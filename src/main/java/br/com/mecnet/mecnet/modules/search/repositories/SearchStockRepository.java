package br.com.mecnet.mecnet.modules.search.repositories;

import br.com.mecnet.mecnet.modules.stock.Entity.Product;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface SearchStockRepository extends PagingAndSortingRepository<Product, UUID>, JpaSpecificationExecutor<Product> {

}
