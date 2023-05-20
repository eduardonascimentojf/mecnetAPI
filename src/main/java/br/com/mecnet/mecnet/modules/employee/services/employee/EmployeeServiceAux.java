package br.com.mecnet.mecnet.modules.employee.services.employee;


import br.com.mecnet.mecnet.modules.employee.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;


import javax.transaction.Transactional;


public class EmployeeServiceAux {
    @Autowired
    EmployeeRepository employeeRepository;
    public EmployeeServiceAux(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }
    @Transactional
    public boolean existsByEmail(String email) {
        return employeeRepository.existsByEmail(email);
    }

    public boolean existsByUserName(String username) {
        return employeeRepository.existsByUserName(username);
    }

}
