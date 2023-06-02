package br.com.mecnet.mecnet.modules.employee.dtos.employee;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequestDto  {

    private String name;
    private String email;
    private String userName;
    private String passWord;
    private Boolean isAdmin;
    public EmployeeRequestDto(String name, String email, String userName, Boolean isAdmin) {
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.isAdmin = isAdmin;
    }


}