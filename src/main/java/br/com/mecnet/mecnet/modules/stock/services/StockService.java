package br.com.mecnet.mecnet.modules.stock.services;

import br.com.mecnet.mecnet.modules.stock.Dtos.ProductRequestDto;
import br.com.mecnet.mecnet.modules.stock.Entity.AutoStock;
import br.com.mecnet.mecnet.modules.stock.Entity.Product;
import br.com.mecnet.mecnet.modules.stock.Entity.Stock;
import br.com.mecnet.mecnet.modules.stock.repositories.AutoStockRepository;
import br.com.mecnet.mecnet.modules.stock.repositories.ProductRepository;
//import br.com.mecnet.mecnet.modules.stock.repositories.ProductsQuantity;
import br.com.mecnet.mecnet.modules.stock.repositories.ProductsQuantity;
import br.com.mecnet.mecnet.modules.stock.repositories.StockRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public class StockService implements ProductsQuantity {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private AutoStockRepository autoStockRepository;
    @Autowired
    private ProductRepository productRepository;

    public Product createProduct(Product product) {
        List<Stock> stockExit = stockRepository.findAll();
        Stock stock;

        if (stockExit.isEmpty()) {
            Stock Newstock = new Stock();
            stock = stockRepository.save(Newstock);

        } else {
            stock = stockExit.get(0);
        }

        AutoStock autoStock = new AutoStock();
        autoStock = autoStockRepository.save(autoStock);


        product.setStock_id(stock);
        product.setAutoStock(autoStock);

        Product productModel = productRepository.save(product);

        setProductsQuantity(productModel.getStock());
        setAddProducts(productModel.getId());

        return productModel;
    }

    public ResponseEntity<Object> updateProduct(ProductRequestDto product, UUID id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Produto não encontrado!");
        }
        Product productModel = new Product();
        BeanUtils.copyProperties(product, productModel);

        productModel.setId(productOptional.get().getId());
        productModel.setStock_id(productOptional.get().getStock_id());
        productModel.setAutoStock(productOptional.get().getAutoStock());
        productModel.setCreatedAt(productOptional.get().getCreatedAt());
        if(product.getStock() < productOptional.get().getStock()){
            setProductsQuantity(productOptional.get().getStock() - product.getStock() );
        }else
            setProductsQuantity(productOptional.get().getStock() + product.getStock() );
        return ResponseEntity.status(HttpStatus.CREATED).body(productRepository.save(productModel));

    }

    public ResponseEntity<Object> updateAutoStock(AutoStock autoStock, UUID id) {
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
    @Transactional
    public void setProductsQuantity(Integer stockQuantity) {
        Stock stock = stockRepository.findAll().get(0);

        Stock stockModel = new Stock();
        BeanUtils.copyProperties(stock, stockModel);
        stockModel.setId(stock.getId());
        stockModel.setProductsQuantity(stock.getProductsQuantity() + stockQuantity);
        stockModel.setCreatedAt(stock.getCreatedAt());
        stockRepository.save(stockModel);

    }
    @Transactional
    public void setAddProducts(UUID id) {
        Optional<Product> productOptional = productRepository.findById(id);
        Stock stock = stockRepository.findAll().get(0);

        Stock stockModel = new Stock();
        BeanUtils.copyProperties(stock, stockModel);
        stockModel.setId(stock.getId());
        stockModel.addProduct(productOptional.orElse(null));
        stockModel.setCreatedAt(stock.getCreatedAt());
        stockRepository.save(stockModel);

    }
    @Transactional
    public void setProductId(UUID idAutoStock, UUID id_product) {
        Optional<AutoStock> autoStockOptional = autoStockRepository.findById(idAutoStock);
        Optional<Product> productOptional = productRepository.findById(id_product);

        if(productOptional.isEmpty() || autoStockOptional.isEmpty()){
            return;
        }
        AutoStock autoStockModel = new AutoStock();

        BeanUtils.copyProperties(autoStockOptional.get(), autoStockModel);
        autoStockModel.setId_AutoStock(idAutoStock);
        autoStockModel.setProduct_id(productOptional.get());
        autoStockModel.setCreatedAt(autoStockOptional.get().getCreatedAt());
        autoStockRepository.save(autoStockModel);

    }
}