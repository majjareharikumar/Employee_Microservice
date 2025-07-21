package com.example.employee_service.dto;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class DepartmentDto {
    private Long id;
    private String departmentName;
    private String departmentDescription;
    private String departmentCode;
}
