package br.com.mecnet.mecnet.modules.employee.entity;

import br.com.mecnet.mecnet.modules.sale.Entity.Sale;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "TB_EMPLOYEE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Employee implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @NotBlank(message = "Nome não informado")
    @Column(name = "name", nullable = false)
    private String name;

    @Email(message = "E-mail inválido")
    @NotBlank(message = "E-mail não informado")
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "Username não informado")
    @Column(name = "userName", nullable = false)
    private String userName;
    @NotBlank(message = "Password não informado")
    @Column(name = "password", nullable = false)
    private String passWord;


    @Column(name = "isAdmin", nullable = false)
    private Boolean isAdmin;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "role", nullable = false)
    private String role;

    @OneToMany(mappedBy = "employee_id")
    private List<Sale> sales =  new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));
    }


    @Override
    public String getPassword() {
        return passWord;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
