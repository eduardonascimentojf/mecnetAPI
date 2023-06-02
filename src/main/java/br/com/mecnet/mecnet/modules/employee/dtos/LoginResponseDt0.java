package br.com.mecnet.mecnet.modules.employee.dtos;

import br.com.mecnet.mecnet.modules.employee.dtos.employee.EmployeeResponseDtoUser;
import br.com.mecnet.mecnet.modules.employee.entity.Employee;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginResponseDt0 {
    private String token;
    private EmployeeResponseDtoUser employee;

    public LoginResponseDt0(String token, Employee data) {
        this.token = token;
        this.employee = new EmployeeResponseDtoUser(data.getId(), data.getName(), data.getEmail(), data.getUsername(), data.getIsAdmin());;
    }
}
