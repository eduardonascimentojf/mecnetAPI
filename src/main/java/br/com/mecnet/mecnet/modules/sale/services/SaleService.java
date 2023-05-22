package br.com.mecnet.mecnet.modules.sale.services;


import br.com.mecnet.mecnet.modules.sale.Dtos.SaleProductDto;
import br.com.mecnet.mecnet.modules.sale.Dtos.SaleRequestDto;
import br.com.mecnet.mecnet.modules.sale.Entity.Sale;
import br.com.mecnet.mecnet.modules.sale.Entity.SaleProduct;
import br.com.mecnet.mecnet.modules.sale.repositories.SaleProductRepository;
import br.com.mecnet.mecnet.modules.sale.repositories.SaleRepository;
import br.com.mecnet.mecnet.modules.stock.Dtos.RemoveItemsDto;
import br.com.mecnet.mecnet.modules.stock.Entity.Product;
import br.com.mecnet.mecnet.modules.stock.repositories.ProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private SaleProductRepository saleProductRepository;
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<Object> createSale(SaleRequestDto SaleRequestDto, String name) {
        Sale sale = new Sale();
        sale.setClient(SaleRequestDto.getClient());
        sale.setCpfClient(SaleRequestDto.getCpfClient());
        sale.setSeller(name);
        return ResponseEntity.status(HttpStatus.CREATED).body(saleRepository.save(sale));

    }
    public ResponseEntity<Object> addProductInSale(SaleProductDto data, UUID id) {

        Optional<Sale> saleOptional = saleRepository.findById(id);
        if(saleOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflit: É necessario adicionar as infos do cliente");
        }

        SaleProduct saleProduct = new SaleProduct();
        saleProduct.setId_productStock(data.getId_productStock());
        saleProduct.setAmount(data.getAmount());
        saleProduct.setPrice(data.getPrice());
        saleProduct.setFullValue(data.getPrice()* data.getAmount());

        ResponseEntity.status(HttpStatus.CREATED).body(saleProductRepository.save(saleProduct));

        Sale newSale = new Sale();
        BeanUtils.copyProperties(saleOptional.get(), newSale);
        newSale.setId(saleOptional.get().getId());
        newSale.getProductsList().add(saleProduct);
        newSale.setPrice(saleProduct.getFullValue() + newSale.getPrice());


        ResponseEntity.status(HttpStatus.CREATED).body(saleRepository.save(newSale));
        return ResponseEntity.status(HttpStatus.CREATED).body(saleProduct);
    }
    public ResponseEntity<Object> removeProductInSale(RemoveItemsDto data) {

        Optional<SaleProduct> saleProductOptional = saleProductRepository.findById(data.id());

        if(saleProductOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflit: Não foi possivel remover da lista");
        }
        Optional<Sale> saleOptional = saleRepository.findById(data.saleId());

        Sale newSale = new Sale();
        BeanUtils.copyProperties(saleOptional.get(), newSale);
        newSale.setId(saleOptional.get().getId());
        newSale.setPrice( newSale.getPrice() - saleProductOptional.get().getFullValue());
        saleProductRepository.deleteById(data.id());
        return  ResponseEntity.status(HttpStatus.CREATED).body(saleRepository.save(newSale));
    }

    public ResponseEntity<Object> checkout(UUID id){
        Optional<Sale> saleOptional = saleRepository.findById(id);
        if(saleOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Venda não encontrada!");
        }
        List<SaleProduct> saleProducts = new ArrayList<>(saleOptional.get().getProductsList());

        for (SaleProduct saleProduct : saleProducts) {

            UUID id_productStock = saleProduct.getId_productStock();
            Optional<Product> productOptional = productRepository.findById(id_productStock);

            if (productOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Produto não encontrado!");
            }

            var productModel = new Product();
            BeanUtils.copyProperties(productOptional.get(), productModel);
            productModel.setId(productOptional.get().getId());
            productModel.setStock_id(productOptional.get().getStock_id());
            productModel.setAutoStock(productOptional.get().getAutoStock());
            productModel.setCreatedAt(productOptional.get().getCreatedAt());
            productModel.setStock(productOptional.get().getStock() - saleProduct.getAmount());
            productRepository.save(productModel);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body("Compra finalizada!");
    }
    public void cancelSale(UUID id) {
        Optional<Sale> saleOptional = saleRepository.findById(id);
        if(saleOptional.isEmpty()){
            ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Venda não encontrada!");
            return;
        }
        ArrayList<SaleProduct> listProductsSale = new ArrayList<>(saleOptional.get().getProductsList());
        for (SaleProduct product : listProductsSale) {
            readjust(product);
        }
        saleRepository.deleteById(id);
        ResponseEntity.status(HttpStatus.CREATED).body("Cancelado com sucessso");
    }

    public void readjust(SaleProduct saleproduct){
        Optional<Product> productOptional = productRepository.findById(saleproduct.getId_productStock());
        if(productOptional.isEmpty()){
            ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Não existe este produto no estoque!");
            return;
        }

        Product productReset = new Product();
        BeanUtils.copyProperties(productOptional.get(), productReset);
        productReset.setId(productOptional.get().getId());
        productReset.setStock(productOptional.get().getStock()+saleproduct.getAmount());
        productReset.setCreatedAt(productReset.getCreatedAt());
        productRepository.save(productReset);

    }

}
