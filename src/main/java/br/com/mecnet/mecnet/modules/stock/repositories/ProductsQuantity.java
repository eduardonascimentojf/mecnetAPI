package br.com.mecnet.mecnet.modules.stock.repositories;

import org.springframework.stereotype.Service;

@Service
public interface ProductsQuantity {
    void setProductsQuantity(Integer stockQuantity);
}
