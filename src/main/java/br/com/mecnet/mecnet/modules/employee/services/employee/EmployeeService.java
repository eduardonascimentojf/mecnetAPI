package br.com.mecnet.mecnet.modules.employee.services.employee;

import br.com.mecnet.mecnet.modules.employee.dtos.employee.EmployeeRequestDto;
import br.com.mecnet.mecnet.modules.employee.entity.Employee;
import br.com.mecnet.mecnet.modules.employee.repositories.EmployeeRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;
@Service
public class EmployeeService {
    @Autowired
    EmployeeRepository employeeRepository;

    private BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
    public ResponseEntity<Object> createUser(Employee employee) {


        if(employeeRepository.existsByUserName(employee.getUsername()) || employeeRepository.existsByEmail(employee.getEmail())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Usuário já cadastrado!");
        }
        String auxRole = "ROLE_USER";
        if(employee.getIsAdmin()) auxRole = "ROLE_ADMIN";
        employee.setRole(auxRole);
        employee.setPassWord(passwordEncoder().encode(employee.getPassword()));
        return ResponseEntity.status(HttpStatus.CREATED).body(employeeRepository.save(employee));

    }
    public ResponseEntity<Object> updateUser(EmployeeRequestDto employee, UUID id) {
        Optional<Employee> employeeOptional = employeeRepository.findById(id);

        if(employeeOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Usuário não encontrado!");

        }
        var employeeModel = new Employee();
        BeanUtils.copyProperties(employee, employeeModel);
        employeeModel.setId(id);
        String auxRole = "ROLE_USER";
        if(employee.getIsAdmin()) auxRole = "ROLE_ADMIN";
        employeeModel.setRole(auxRole);
        if(employee.getPassWord().length() > 5){
            employeeModel.setPassWord(passwordEncoder().encode(employee.getPassWord()));
        }else{
            employeeModel.setPassWord(employeeOptional.get().getPassword());
        }
        employeeModel.setCreatedAt(employeeOptional.get().getCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(employeeRepository.save(employeeModel));

    }








}
