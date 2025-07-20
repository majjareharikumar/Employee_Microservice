package com.example.employee_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EmployeeException extends RuntimeException{

    private String firstName;
    private String lastName;
    private String email;

    public EmployeeException(String firstName,String lastName,Long id){
        super(String.format("%s not found by id %s:- %s",firstName,lastName,id));
        this.firstName=firstName;
        this.lastName=lastName;
        this.email=email;

    }
}
