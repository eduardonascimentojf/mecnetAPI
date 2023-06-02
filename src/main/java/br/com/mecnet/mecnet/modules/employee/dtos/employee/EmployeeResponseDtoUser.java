package br.com.mecnet.mecnet.modules.employee.dtos.employee;

import br.com.mecnet.mecnet.modules.employee.entity.Employee;

import java.util.UUID;

public record EmployeeResponseDtoUser(UUID id, String name, String email, String userName, Boolean isAdmin) {
    public EmployeeResponseDtoUser(Employee employee) {
        this(employee.getId(), employee.getName(), employee.getEmail(), employee.getUsername(), employee.getIsAdmin());
    }
}
