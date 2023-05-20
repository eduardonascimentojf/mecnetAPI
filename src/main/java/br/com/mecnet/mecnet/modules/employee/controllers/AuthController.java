package br.com.mecnet.mecnet.modules.employee.controllers;


import br.com.mecnet.mecnet.modules.employee.dtos.LoginDt0;


import br.com.mecnet.mecnet.modules.employee.entity.Employee;
import br.com.mecnet.mecnet.modules.employee.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public String login(@RequestBody LoginDt0 loginDt0) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginDt0.login(),
                      loginDt0.password());

        var user =(Employee) authenticationManager.authenticate(usernamePasswordAuthenticationToken).getPrincipal();
        return tokenService.generateToken(user);

    }


}