package br.com.mecnet.mecnet.modules.employee.repositories;

import br.com.mecnet.mecnet.modules.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    Employee findByUserName(String Username);
    Employee findByEmail(String email);

    boolean existsByUserName(String Username);
    boolean existsByEmail(String email);


}
