package br.com.mecnet.mecnet.modules.employee.services;

import br.com.mecnet.mecnet.modules.employee.entity.Employee;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class TokenService {
    @Value("${token_secret}")
    private String tokenSecret;
    public String generateToken(Employee employee) {
        return JWT.create()
                .withIssuer("Employee")
                .withSubject(employee.getUsername())
                .withClaim("id", String.valueOf(employee.getId()))
                .withExpiresAt(Date.from(LocalDateTime.now()
                        .plusDays(7)
                        .toInstant(ZoneOffset.of("-03:00")))
                ).sign(Algorithm.HMAC256(tokenSecret));
    }


    public String getSubject(String token) {
        return JWT.require(Algorithm.HMAC256(tokenSecret))
                .withIssuer("Employee")
                .build().verify(token).getSubject();
    }
}