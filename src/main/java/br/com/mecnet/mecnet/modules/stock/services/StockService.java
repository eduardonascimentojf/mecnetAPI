package br.com.mecnet.mecnet.modules.stock.services;

import br.com.mecnet.mecnet.modules.stock.Dtos.ProductRequestDto;
import br.com.mecnet.mecnet.modules.stock.Entity.AutoStock;
import br.com.mecnet.mecnet.modules.stock.Entity.Product;
import br.com.mecnet.mecnet.modules.stock.Entity.Stock;
import br.com.mecnet.mecnet.modules.stock.repositories.AutoStockRepository;
import br.com.mecnet.mecnet.modules.stock.repositories.ProductRepository;
import br.com.mecnet.mecnet.modules.stock.repositories.StockRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private AutoStockRepository autoStockRepository;
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<Object> createProduct(Product product) {
        List<Stock> stockExit = stockRepository.findAll();
        Stock stock;
        if(stockExit.isEmpty()){
            Stock Newstock = new Stock();
            stock = stockRepository.save(Newstock);

        }else{
            stock = stockExit.get(0);
        }

        AutoStock autoStock = new AutoStock();
        autoStock = autoStockRepository.save(autoStock);

        product.setStock_id(stock);
        product.setAutoStock(autoStock);

        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(product));

    }

    public ResponseEntity<Object> updateProduct(ProductRequestDto product, UUID id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Produto não encontrado!");
        }
        var productModel = new Product();
        BeanUtils.copyProperties(product, productModel);
        productModel.setId(productOptional.get().getId());

        productModel.setStock_id(productOptional.get().getStock_id());
        productModel.setAutoStock(productOptional.get().getAutoStock());
        productModel.setCreatedAt(productOptional.get().getCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));

    }
    public ResponseEntity<Object> updateAutoStock(AutoStock autoStock,UUID id) {
        Optional<AutoStock> autoStockOptional = autoStockRepository.findById(id);
        if (autoStockOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Produto não encontrado!");
        }

        var autoStockModel = new AutoStock();
        BeanUtils.copyProperties(autoStock, autoStockModel);
        autoStockModel.setId_AutoStock(autoStockOptional.get().getId_AutoStock());

        autoStockModel.setProduct_id(autoStockOptional.get().getProduct_id());
        autoStockModel.setCreatedAt(autoStockOptional.get().getCreatedAt());

        return ResponseEntity.status(HttpStatus.CREATED).body(autoStockRepository.save(autoStockModel));

    }

}
