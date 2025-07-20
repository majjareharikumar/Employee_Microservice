package com.example.department_service.service.Impl;

import com.example.department_service.DTO.DepartmentDto;
import com.example.department_service.Exception.DepartmentException;
import com.example.department_service.entity.Department;
import com.example.department_service.repository.DepartmentRepo;
import com.example.department_service.service.DepartmentService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {


    private DepartmentRepo departmentRepo;


    @Override
    public DepartmentDto saveDepartment(DepartmentDto departmentDto) {
        //convert DTO to JPA
        Department department=new Department(
                departmentDto.getId(),
                departmentDto.getDepartmentName(),
                departmentDto.getDepartmentDescription(),
                departmentDto.getDepartmentCode()
        );

        Department savedDepartment=departmentRepo.save(department);

        DepartmentDto savedDepartmentDto=new DepartmentDto(
                savedDepartment.getId(),
                savedDepartment.getDepartmentName(),
                savedDepartment.getDepartmentDescription(), savedDepartment.getDepartmentCode()
        );

        return savedDepartmentDto;
    }

    @Override
    public DepartmentDto getDepartmentByCode(String departmentCode) {
        Department department=departmentRepo.findByDepartmentCode(departmentCode);
        if (department == null) {
            throw new DepartmentException("departmentName","departmentDescription","departmentCode");
        }
        DepartmentDto departmentDto=new DepartmentDto(
                department.getId(),
                department.getDepartmentName(),
                department.getDepartmentDescription(),
                department.getDepartmentCode()
        );
        return departmentDto;
    }
}
