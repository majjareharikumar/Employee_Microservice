package com.example.department_service.service;

import com.example.department_service.DTO.DepartmentDto;
import org.springframework.stereotype.Service;


public interface DepartmentService {

    DepartmentDto saveDepartment(DepartmentDto departmentDto);
    DepartmentDto getDepartmentByCode(String departmentCode);


}
