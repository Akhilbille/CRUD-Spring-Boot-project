package com.CRUDProject.CRUDProject.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RegisterEmployeeDto {
    private String firstName;
    private String lastName;
    private String email;
}
