package br.com.mecnet.mecnet.modules.stock.repositories;


import br.com.mecnet.mecnet.modules.stock.Entity.AutoStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AutoStockRepository extends JpaRepository<AutoStock, UUID> {

}
