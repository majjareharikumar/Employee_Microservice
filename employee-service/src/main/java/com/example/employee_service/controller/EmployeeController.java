package com.example.employee_service.controller;

import com.example.employee_service.dto.APIResponseDto;
import com.example.employee_service.dto.EmployeeDto;
import com.example.employee_service.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/Employees")
@AllArgsConstructor
public class EmployeeController {


    private EmployeeService employeeService;

    //Create employee
    @PostMapping("/createemp")
    public ResponseEntity<EmployeeDto> saveEmployee(@RequestBody EmployeeDto employeeDto){
        EmployeeDto savedEmployee=employeeService.saveEmployee(employeeDto);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }

    //Get Employee By Id
    @GetMapping("{id}")
    public ResponseEntity<APIResponseDto> getEmployee(@PathVariable("id") Long employeeId){
        APIResponseDto apiResponseDto=employeeService.getEmployeeById(employeeId);
        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }

}
