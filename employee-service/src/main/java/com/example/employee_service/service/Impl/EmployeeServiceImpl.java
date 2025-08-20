package com.example.employee_service.service.Impl;

import com.example.employee_service.dto.DepartmentDto;
import com.example.employee_service.dto.EmployeeDto;
import com.example.employee_service.entity.Employee;
import com.example.employee_service.exception.EmployeeException;
import com.example.employee_service.repository.EmployeeRepo;
import com.example.employee_service.service.APIClient;
import com.example.employee_service.service.EmployeeService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.employee_service.dto.APIResponseDto;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

private static final Logger LOGGER= LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private EmployeeRepo employeeRepo;

    private WebClient webClient;
    private APIClient apiClient;

    @Override
    public EmployeeDto saveEmployee(EmployeeDto employeeDto) {

        Employee employee=new Employee(
                employeeDto.getId(),
                employeeDto.getFirstName(),
                employeeDto.getLastName(),
                employeeDto.getEmail(),
                employeeDto.getDepartmentCode()
        );
        Employee savedEmployee=employeeRepo.save(employee);
        EmployeeDto savedEmployeeDto=new EmployeeDto(
                savedEmployee.getId(),
                savedEmployee.getFirstName(),
                savedEmployee.getLastName(),
                savedEmployee.getEmail(),
                savedEmployee.getDepartmentCode()
        );

        return savedEmployeeDto;
    }

    //@CircuitBreaker(name = "${spring.application.name}", fallbackMethod = "getdefaultdepartment")
    @Retry(name = "${spring.application.name}", fallbackMethod = "getdefaultdepartment")
    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {
        LOGGER.info("Inside getEmployeeById method");


        Employee employee=employeeRepo.findById(employeeId).orElseThrow(
                ()-> new EmployeeException("fistName","lastName",employeeId)
        );

//        ResponseEntity <DepartmentDto> responseEntity=restTemplate.getForEntity("http://localhost:8080/api/departments/"+employee.getDepartmentCode(),DepartmentDto.class);
//
//        DepartmentDto departmentDto=responseEntity.getBody();

        ResponseEntity<DepartmentDto> responseEntity = webClient.get()
                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
                .retrieve()
                .toEntity(DepartmentDto.class)
                .block();
//        ResponseEntity<DepartmentDto> responseEntity = apiClient.getDepartment(employee.getDepartmentCode());

        DepartmentDto departmentDto = responseEntity.getBody();

        EmployeeDto employeeDto=new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartmentCode()
        );
        APIResponseDto apiResponseDto=new APIResponseDto();
        apiResponseDto.setEmployeeDto(employeeDto);
        apiResponseDto.setDepartmentDto(departmentDto);

        return apiResponseDto;
    }
    public APIResponseDto getdefaultdepartment(Long employeeId , Exception exception){
        LOGGER.info("Inside getdefaultdepartment method");
        Employee employee=employeeRepo.findById(employeeId).orElseThrow(
                ()-> new EmployeeException("fistName","lastName",employeeId)
        );


        DepartmentDto departmentDto=new DepartmentDto();
        departmentDto.setId(0L);
        departmentDto.setDepartmentName("R&D DEPT");
        departmentDto.setDepartmentDescription("R&D DEPT");
        departmentDto.setDepartmentCode("R&D");

        EmployeeDto employeeDto=new EmployeeDto(
                employee.getId(),
                employee.getFirstName(),
                employee.getLastName(),
                employee.getEmail(),
                employee.getDepartmentCode()
        );
        APIResponseDto apiResponseDto=new APIResponseDto();
        apiResponseDto.setEmployeeDto(employeeDto);
        apiResponseDto.setDepartmentDto(departmentDto);

        return apiResponseDto;
    }



}
