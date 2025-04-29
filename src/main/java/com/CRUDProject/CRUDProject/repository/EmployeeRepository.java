package com.CRUDProject.CRUDProject.repository;

import com.CRUDProject.CRUDProject.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {
     Boolean existsByEmail(String email);
}
