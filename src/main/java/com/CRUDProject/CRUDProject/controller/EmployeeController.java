package com.CRUDProject.CRUDProject.controller;

import com.CRUDProject.CRUDProject.dto.EmployeeDto;
import com.CRUDProject.CRUDProject.dto.RegisterEmployeeDto;
import com.CRUDProject.CRUDProject.mapper.EmployeeMapper;
import com.CRUDProject.CRUDProject.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private EmployeeRepository employeeRepository;
    private EmployeeMapper employeeMapper;

    //Getting all employees
    @GetMapping
    public Iterable<EmployeeDto> getAllEmployees(){
        return employeeRepository.findAll().
                stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    //Getting a specific employee
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDto> getEmployee(
            @PathVariable(name = "id") Long id
    ){
        var emp = employeeRepository.findById(id).orElse(null);
        if(emp == null)
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(employeeMapper.toDto(emp));


    }


    //Creating an employee
    @PostMapping
    public  ResponseEntity<?> registerEmployee(
            @RequestBody RegisterEmployeeDto request,
            UriComponentsBuilder uriComponentsBuilder
    ) {
        if (employeeRepository.existsByEmail(request.getEmail()))
            return ResponseEntity.badRequest().body(
                    Map.of("Email", "Email already exists")
            );
        var emp = employeeRepository.save(employeeMapper.toRegisterEntity(request));
        var uri = uriComponentsBuilder.path("/employees/{id}").buildAndExpand(emp.getId()).toUri();

        return ResponseEntity.created(uri).body(employeeMapper.toDto(emp));
    }

    //Updating an employee
    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(
            @PathVariable(name = "id") Long id,
            @RequestBody RegisterEmployeeDto request,
            UriComponentsBuilder uriComponentsBuilder
    ){
        var emp =  employeeRepository.findById(id).orElse(null);
        if(emp == null)
            return ResponseEntity.notFound().build();
        if(!request.getEmail().equals(emp.getEmail()))
            if(employeeRepository.existsByEmail(request.getEmail()))
                return ResponseEntity.badRequest().body(
                        Map.of("Email","Email already exists")
                );
        employeeMapper.update(request,emp);
        employeeRepository.save(emp);
        return ResponseEntity.ok().body(employeeMapper.toDto(emp));

    }

    //Deleting an employee
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(
            @PathVariable(name = "id") Long id
    ){
        if(!employeeRepository.existsById(id))
            return ResponseEntity.notFound().build();
        employeeRepository.deleteById(id);
        return ResponseEntity.noContent().build();


    }



}
