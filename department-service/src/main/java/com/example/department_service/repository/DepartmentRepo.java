package com.example.department_service.repository;

import com.example.department_service.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepo extends JpaRepository<Department,Long> {
    Department findByDepartmentCode(String departmentCode);
}
