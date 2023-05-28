package br.com.mecnet.mecnet.modules.stock.services;


import br.com.mecnet.mecnet.modules.stock.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ProductService {

    @Autowired
    ProductRepository ProductRepository;
    public ProductService(ProductRepository ProductRepository) {
        this.ProductRepository = ProductRepository;
    }
    @Transactional
    public boolean existsByName(String name) {
        return ProductRepository.existsByName(name);
    }
    public boolean existsByBrand(String brand) {
            return ProductRepository.existsByBrand(brand);
    }
    public boolean existsByManufacturer(String manufacturer) {
        return ProductRepository.existsByManufacturer(manufacturer);
    }
}
