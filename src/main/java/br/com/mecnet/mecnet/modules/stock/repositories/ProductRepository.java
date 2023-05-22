package br.com.mecnet.mecnet.modules.stock.repositories;


import br.com.mecnet.mecnet.modules.stock.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Product findByName(String Name);

    Product findByBrand(String Brand);
    Product findByManufacturer(String Manufacturer);
    Optional<Product> findByIdCatalog(UUID IdCatalog);
    boolean existsByName(String Name);
    boolean existsByBrand(String Brand);
    boolean existsByManufacturer(String Manufacturer);
}
