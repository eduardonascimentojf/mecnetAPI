package br.com.mecnet.mecnet.modules.order.repositories;

import br.com.mecnet.mecnet.modules.order.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findOrderByIsComplete(Boolean is_Complete);

}
