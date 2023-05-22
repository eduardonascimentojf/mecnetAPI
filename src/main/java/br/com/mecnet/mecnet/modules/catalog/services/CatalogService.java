package br.com.mecnet.mecnet.modules.catalog.services;

import br.com.mecnet.mecnet.modules.catalog.Dtos.CatalogProductDt0;
import br.com.mecnet.mecnet.modules.catalog.Entity.CatalogProduct;
import br.com.mecnet.mecnet.modules.catalog.repositories.CatalogRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;
@Service
public class CatalogService {

    @Autowired
    private CatalogRepository catalogRepository;

    @Transactional
    public boolean existsByName(String name) {
        return catalogRepository.existsByName(name);
    }
    public boolean existsByBrand(String brand) {
        return catalogRepository.existsByBrand(brand);
    }
    public boolean existsByManufacturer(String manufacturer) {
        return catalogRepository.existsByManufacturer(manufacturer);
    }
    public ResponseEntity<Object> updateProductInCatalog(CatalogProductDt0 productCatalog, UUID id) {
        Optional<CatalogProduct> productCatalogOptional = catalogRepository.findById(id);

        if (productCatalogOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Produto não encontrado!");
        }
        var productCatalogModel = new CatalogProduct();
        BeanUtils.copyProperties(productCatalog, productCatalogModel);
        productCatalogModel.setId(productCatalogOptional.get().getId());

        productCatalogModel.setId(productCatalogOptional.get().getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(catalogRepository.save(productCatalogModel));

    }
}
