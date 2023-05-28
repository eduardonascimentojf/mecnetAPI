package br.com.mecnet.mecnet.modules.employee.controllers;


import br.com.mecnet.mecnet.modules.employee.dtos.employee.EmployeeDeleteDto;
import br.com.mecnet.mecnet.modules.employee.dtos.employee.EmployeeRequestDto;
import br.com.mecnet.mecnet.modules.employee.dtos.employee.EmployeeResponseDto;
import br.com.mecnet.mecnet.modules.employee.entity.Employee;

import br.com.mecnet.mecnet.modules.employee.repositories.EmployeeRepository;
import br.com.mecnet.mecnet.modules.employee.services.employee.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    @GetMapping
    public List<EmployeeResponseDto> getAllEmployee(){

        return employeeRepository.findAll().stream().map(EmployeeResponseDto::new).toList();
    }
    @GetMapping("/{id}")
    public Optional<Employee> getEmployee(@PathVariable(value = "id") UUID id){
        return employeeRepository.findById(id);
    }

    @PostMapping
    public ResponseEntity<Object> createEmployee(@RequestBody @Valid Employee data){
        return employeeService.createUser(data);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Object> updateEmployee(@RequestBody EmployeeRequestDto data, @PathVariable(value = "id") UUID id){
        return employeeService.updateUser(data, id);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteEmployee(@RequestBody EmployeeDeleteDto deleteDto){
        employeeRepository.deleteById(deleteDto.id());
        return ResponseEntity.status(HttpStatus.OK).body("Excluído com sucesso");
    }
}
