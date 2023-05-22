package br.com.mecnet.mecnet.modules.stock.repositories;


import br.com.mecnet.mecnet.modules.stock.Dtos.StockResponseDto;
import br.com.mecnet.mecnet.modules.stock.Entity.Product;
import br.com.mecnet.mecnet.modules.stock.Entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockRepository extends JpaRepository<Stock, UUID> {

}
