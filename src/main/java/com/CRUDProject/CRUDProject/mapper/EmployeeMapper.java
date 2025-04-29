package com.CRUDProject.CRUDProject.mapper;

import com.CRUDProject.CRUDProject.dto.EmployeeDto;
import com.CRUDProject.CRUDProject.dto.RegisterEmployeeDto;
import com.CRUDProject.CRUDProject.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDto toDto(Employee emp);
    Employee toEntity(EmployeeDto dto);
    Employee toRegisterEntity(RegisterEmployeeDto dto);
    void update(RegisterEmployeeDto dto, @MappingTarget Employee emp);
}
