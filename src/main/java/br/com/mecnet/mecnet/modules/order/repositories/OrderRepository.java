package br.com.mecnet.mecnet.modules.order.repositories;

import br.com.mecnet.mecnet.modules.order.Entity.Order;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    Optional<Order> findOrderByIsComplete(Boolean is_Complete);
    List<Order> findAllByIsComplete(Boolean is_Complete, Sort sort );
}
