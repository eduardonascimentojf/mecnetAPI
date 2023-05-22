package br.com.mecnet.mecnet.modules.sale.repositories;

import br.com.mecnet.mecnet.modules.sale.Entity.SaleProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.UUID;

@Repository
public interface SaleProductRepository extends JpaRepository<SaleProduct, UUID> {

}
