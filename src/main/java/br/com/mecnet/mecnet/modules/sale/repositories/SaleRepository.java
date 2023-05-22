package br.com.mecnet.mecnet.modules.sale.repositories;

import br.com.mecnet.mecnet.modules.sale.Entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {

    List<Sale> findAllBySeller(String seller);

}
