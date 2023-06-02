package br.com.mecnet.mecnet.modules.employee.controllers;


import br.com.mecnet.mecnet.modules.employee.dtos.LoginDt0;


import br.com.mecnet.mecnet.modules.employee.dtos.LoginResponseDt0;
import br.com.mecnet.mecnet.modules.employee.entity.Employee;
import br.com.mecnet.mecnet.modules.employee.repositories.EmployeeRepository;
import br.com.mecnet.mecnet.modules.employee.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;
    @Autowired
    private EmployeeRepository employeeRepository;
    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDt0 loginDt0) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDt0.login(),
                      loginDt0.password());

        var user =(Employee) authenticationManager.authenticate(usernamePasswordAuthenticationToken).getPrincipal();
        LoginResponseDt0 loginResponseDt0 = new LoginResponseDt0(tokenService.generateToken(user),user);
        return ResponseEntity.status(HttpStatus.OK).body(loginResponseDt0);

    }
    @GetMapping("/authenticate")
    public ResponseEntity<Object> authenticate(Authentication authentication) {
        return ResponseEntity.status(HttpStatus.OK).body(employeeRepository.findByUserName(authentication.getName()));
    }

}
