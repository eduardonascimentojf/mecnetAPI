package br.com.mecnet.mecnet.modules.employee.repositories;

import br.com.mecnet.mecnet.modules.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface LoginRepository extends JpaRepository<Employee, UUID> {

    Employee findByUserName(String Username);


}
