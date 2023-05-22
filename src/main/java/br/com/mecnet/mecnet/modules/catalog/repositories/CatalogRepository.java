package br.com.mecnet.mecnet.modules.catalog.repositories;

import br.com.mecnet.mecnet.modules.catalog.Entity.CatalogProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CatalogRepository extends JpaRepository<CatalogProduct, UUID> {
    Optional<CatalogProduct> findByName(UUID name);
    Optional<CatalogProduct> findByBrand(UUID brand);
    Optional<CatalogProduct> findByManufacturer(UUID manufacturer);


    boolean existsByName(String name);
    boolean existsByBrand(String brand);
    boolean existsByManufacturer(String manufacturer);


}
