package com.example.employee_service.service.Impl;

import com.example.employee_service.dto.DepartmentDto;
import com.example.employee_service.dto.EmployeeDto;
import com.example.employee_service.entity.Employee;
import com.example.employee_service.exception.EmployeeException;
import com.example.employee_service.repository.EmployeeRepo;
import com.example.employee_service.service.APIClient;
import com.example.employee_service.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.example.employee_service.dto.APIResponseDto;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {


    private EmployeeRepo employeeRepo;

    //private WebClient webClient;
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

    @Override
    public APIResponseDto getEmployeeById(Long employeeId) {

        Employee employee=employeeRepo.findById(employeeId).orElseThrow(
                ()-> new EmployeeException("fistName","lastName",employeeId)
        );

//        ResponseEntity <DepartmentDto> responseEntity=restTemplate.getForEntity("http://localhost:8080/api/departments/"+employee.getDepartmentCode(),DepartmentDto.class);
//
//        DepartmentDto departmentDto=responseEntity.getBody();

//        ResponseEntity<DepartmentDto> responseEntity = webClient.get()
//                .uri("http://localhost:8080/api/departments/" + employee.getDepartmentCode())
//                .retrieve()
//                .toEntity(DepartmentDto.class)
//                .block();
        ResponseEntity<DepartmentDto> responseEntity = apiClient.getDepartment(employee.getDepartmentCode());

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
}
