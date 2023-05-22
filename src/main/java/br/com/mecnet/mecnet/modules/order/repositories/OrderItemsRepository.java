package br.com.mecnet.mecnet.modules.order.repositories;


import br.com.mecnet.mecnet.modules.order.Entity.OrderItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderItemsRepository extends JpaRepository<OrderItems, UUID> {
//    Optional<OrderItems> findByIsComplete(Boolean isComplete);
}
