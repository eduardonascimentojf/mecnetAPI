package br.com.mecnet.mecnet.modules.sale.services;


import br.com.mecnet.mecnet.modules.employee.entity.Employee;
import br.com.mecnet.mecnet.modules.employee.repositories.EmployeeRepository;
import br.com.mecnet.mecnet.modules.sale.Dtos.SaleProductDto;
import br.com.mecnet.mecnet.modules.sale.Dtos.SaleRequestDto;
import br.com.mecnet.mecnet.modules.sale.Entity.Sale;
import br.com.mecnet.mecnet.modules.sale.Entity.SaleProduct;
import br.com.mecnet.mecnet.modules.sale.repositories.SaleProductRepository;
import br.com.mecnet.mecnet.modules.sale.repositories.SaleRepository;
import br.com.mecnet.mecnet.modules.stock.Entity.Product;
import br.com.mecnet.mecnet.modules.stock.repositories.ProductRepository;
import br.com.mecnet.mecnet.modules.stock.services.StockService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class SaleService {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private SaleProductRepository saleProductRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private StockService stockService;

    public ResponseEntity<Object> createSale(SaleRequestDto saleRequestDto, String name) {
        Employee employee = employeeRepository.findByUserName(name);
        Sale sale = new Sale();
        sale.setClient(saleRequestDto.getClient());
        sale.setCpfClient(saleRequestDto.getCpfClient());
        sale.setSeller(name);
        sale.setEmployee_id(employee);

        Sale saleSave = saleRepository.save(sale);
        AddProductsSale(sale.getId(), saleRequestDto.getSaleProducts());

        Employee newEmployee = new Employee();
        BeanUtils.copyProperties(employee, newEmployee);
        newEmployee.setPassWord(employee.getPassword());
        newEmployee.setUserName(employee.getUsername());
        newEmployee.getSales().add(saleSave);
        newEmployee.setCreatedAt(employee.getCreatedAt());

        employeeRepository.save(newEmployee);
        this.checkout(saleSave.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(saleSave);

    }
//    public ResponseEntity<Object> addProductInSale(SaleProductDto data, UUID id) {
//
//        Optional<Sale> saleOptional = saleRepository.findById(id);
//        if(saleOptional.isEmpty()){
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflit: É necessario adicionar as infos do cliente");
//        }
//
//        SaleProduct saleProduct = new SaleProduct();
//        saleProduct.setId_productStock(data.getId_productStock());
//        saleProduct.setAmount(data.getAmount());
//        saleProduct.setPrice(data.getPrice());
//        saleProduct.setFullValue(data.getPrice()* data.getAmount());
//
//        ResponseEntity.status(HttpStatus.CREATED).body(saleProductRepository.save(saleProduct));
//
//        Sale newSale = new Sale();
//        BeanUtils.copyProperties(saleOptional.get(), newSale);
//        newSale.setId(saleOptional.get().getId());
//        newSale.getProductsList().add(saleProduct);
//        newSale.setPrice(saleProduct.getFullValue() + newSale.getPrice());
//
//        ResponseEntity.status(HttpStatus.CREATED).body(saleRepository.save(newSale));
//
//        checkout(newSale.getId());
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(saleProduct);
//    }
//    public ResponseEntity<Object> removeProductInSale(RemoveItemsDto data) {
//
//        Optional<SaleProduct> saleProductOptional = saleProductRepository.findById(data.id());
//
//        if(saleProductOptional.isEmpty()){
//            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflit: Não foi possivel remover da lista");
//        }
//        Optional<Sale> saleOptional = saleRepository.findById(data.saleId());
//
//        Sale newSale = new Sale();
//        BeanUtils.copyProperties(saleOptional.get(), newSale);
//        newSale.setId(saleOptional.get().getId());
//        newSale.setPrice( newSale.getPrice() - saleProductOptional.get().getFullValue());
//        saleProductRepository.deleteById(data.id());
//
//        return  ResponseEntity.status(HttpStatus.CREATED).body(saleRepository.save(newSale));
//    }

    public void checkout(UUID id){
        AtomicInteger quantityProductsAdd  = new AtomicInteger();
        Optional<Sale> saleOptional = saleRepository.findById(id);
        if(saleOptional.isEmpty()){
            ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Venda não encontrada!");
            return;
        }
        List<SaleProduct> saleProducts = new ArrayList<>(saleOptional.get().getProductsList());

        for (SaleProduct saleProduct : saleProducts) {

            UUID id_productStock = saleProduct.getId_productStock();
            Optional<Product> productOptional = productRepository.findById(id_productStock);

            if (productOptional.isEmpty()) {
                ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Produto não encontrado!");
                return;
            }

            var productModel = new Product();
            BeanUtils.copyProperties(productOptional.get(), productModel);
            productModel.setId(productOptional.get().getId());
            productModel.setStock_id(productOptional.get().getStock_id());
            productModel.setAutoStock(productOptional.get().getAutoStock());
            productModel.setCreatedAt(productOptional.get().getCreatedAt());
            productModel.setStock(productOptional.get().getStock() - saleProduct.getAmount());
            quantityProductsAdd.addAndGet(saleProduct.getAmount()*-1);
            productRepository.save(productModel);

        }

        Sale saleModel = new Sale();
        BeanUtils.copyProperties(saleOptional.get(), saleModel);
        saleModel.setId(saleOptional.get().getId());
        saleModel.setIsActive(false);
        saleModel.setCreatedAt(saleOptional.get().getCreatedAt());
        saleRepository.save(saleModel);

        stockService.setProductsQuantity(quantityProductsAdd.get() *-1);
        ResponseEntity.status(HttpStatus.CREATED).body("Compra finalizada!");
    }
    public void cancelSale(UUID id) {
        AtomicInteger quantityProductsAdd  = new AtomicInteger();
        Optional<Sale> saleOptional = saleRepository.findById(id);
        if(saleOptional.isEmpty()){
            ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Venda não encontrada!");
            return;
        }
        ArrayList<SaleProduct> listProductsSale = new ArrayList<>(saleOptional.get().getProductsList());
        for (SaleProduct product : listProductsSale) {
            readjust(product, quantityProductsAdd);
        }
        stockService.setProductsQuantity(quantityProductsAdd.get());
//        saleRepository.deleteById(id);
        ResponseEntity.status(HttpStatus.CREATED).body("Cancelado com sucessso");
    }

    public void readjust(SaleProduct saleproduct,AtomicInteger quantityProductsAdd){
        Optional<Product> productOptional = productRepository.findById(saleproduct.getId_productStock());
        if(productOptional.isEmpty()){
            ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Não existe este produto no estoque!");
            return;
        }

        Product productReset = new Product();
        BeanUtils.copyProperties(productOptional.get(), productReset);
        productReset.setId(productOptional.get().getId());
        productReset.setStock(productOptional.get().getStock() + saleproduct.getAmount());
        productReset.setCreatedAt(productReset.getCreatedAt());
        productRepository.save(productReset);
        quantityProductsAdd.addAndGet(saleproduct.getAmount());
    }

    @Transactional
    public void AddProductsSale(UUID id, List<SaleProductDto> productDtos) {

        Optional<Sale> saleOptional = saleRepository.findById(id);

        for (SaleProductDto product : productDtos) {
            SaleProduct saleProductModel = new SaleProduct();
            BeanUtils.copyProperties(product, saleProductModel);
            saleProductModel.setSale_id(saleOptional.orElse(null));
            saleProductModel.setFullValue(product.getPrice() * product.getAmount());

            SaleProduct saleProduct =  saleProductRepository.save(saleProductModel);


            Sale saleModel = new Sale();
            BeanUtils.copyProperties(saleOptional.get(), saleModel);
            saleModel.setPrice(saleProduct.getFullValue()+saleOptional.get().getPrice());
            saleModel.getProductsList().add(saleProduct);
            saleRepository.save(saleModel);

        }
    }
    @Transactional
    public void setFullValue(UUID id, Float priceTotal) {

        Optional<Sale> saleOptional = saleRepository.findById(id);
        Sale saleModel = new Sale();
        BeanUtils.copyProperties(saleOptional.get(), saleModel);
        saleModel.setPrice(priceTotal);
        saleRepository.save(saleModel);

    }
}
