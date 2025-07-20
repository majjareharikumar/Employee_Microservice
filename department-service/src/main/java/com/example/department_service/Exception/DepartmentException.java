package com.example.department_service.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DepartmentException extends RuntimeException{

    private String departmentName;
    private String departmentDescription;
    private String departmentCode;

    public DepartmentException(String departmentName,String departmentDescription,String departmentCode){
        super(String.format("%s not found by code %S :- %s",departmentName,departmentDescription,departmentCode));
        this.departmentName=departmentName;
        this.departmentDescription=departmentDescription;
        this.departmentCode=departmentCode;

    }
}
