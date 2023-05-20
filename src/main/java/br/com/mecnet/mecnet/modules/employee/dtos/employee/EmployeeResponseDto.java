package br.com.mecnet.mecnet.modules.employee.dtos.employee;

import br.com.mecnet.mecnet.modules.employee.entity.Employee;

import java.time.LocalDateTime;
import java.util.UUID;

public record EmployeeResponseDto(UUID id, String name, String email, String userName, String password, Boolean isAdmin, LocalDateTime createdAt, LocalDateTime updatedAt)  {
    public EmployeeResponseDto(Employee employee) {
        this(employee.getId(), employee.getName(), employee.getEmail(), employee.getUsername(), employee.getPassword(), employee.getIsAdmin() , employee.getCreatedAt(), employee.getUpdatedAt());
    }


}

