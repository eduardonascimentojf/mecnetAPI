package br.com.mecnet.mecnet.modules.search.repositories;

import br.com.mecnet.mecnet.modules.catalog.Entity.CatalogProduct;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SearchCatalogRepository extends PagingAndSortingRepository<CatalogProduct, UUID>, JpaSpecificationExecutor<CatalogProduct> {

}
