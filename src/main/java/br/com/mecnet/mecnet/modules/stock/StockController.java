package br.com.mecnet.mecnet.modules.stock;


import br.com.mecnet.mecnet.modules.stock.Dtos.ProductDeleteDto;
import br.com.mecnet.mecnet.modules.stock.Dtos.ProductRequestDto;
import br.com.mecnet.mecnet.modules.stock.Entity.AutoStock;
import br.com.mecnet.mecnet.modules.stock.Entity.Product;
import br.com.mecnet.mecnet.modules.stock.Entity.Stock;
import br.com.mecnet.mecnet.modules.stock.repositories.ProductRepository;
import br.com.mecnet.mecnet.modules.stock.repositories.StockRepository;
import br.com.mecnet.mecnet.modules.stock.services.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/stock")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class StockController {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StockService stockService;
    @Autowired
    private StockRepository stockRepository;

    @GetMapping
    @RolesAllowed({"USER","ADMIN"})
    public Stock get_Stock(){
        return stockRepository.findAll().get(0);
    }

    @GetMapping("/products")
    @RolesAllowed({"USER","ADMIN"})
    public List<Product> getAllProducts(){
        return productRepository.findAll().stream().map(Product::new).toList();
    }
    @GetMapping("/products/{id}")
    @RolesAllowed({"USER","ADMIN"})
    public Optional<Product> getProduct(@PathVariable(value = "id") UUID id){
        return productRepository.findById(id);
    }

    @PostMapping("/products")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Object> createProduct(@RequestBody @Valid Product data){

        return stockService.createProduct(data);
    }
    @PutMapping("/products/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Object> updateProduct(@RequestBody ProductRequestDto data, @PathVariable(value = "id") UUID id){
        return stockService.updateProduct(data, id);
    }
    @DeleteMapping("/products")
    @RolesAllowed("ADMIN")
    public void deleteProduct(@RequestBody ProductDeleteDto deleteDto){
        productRepository.deleteById(deleteDto.id());
    }
    @PutMapping("/products/autoStock/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Object> updateAutoStock(@RequestBody AutoStock data, @PathVariable(value = "id") UUID id){
        return stockService.updateAutoStock(data, id);
    }
}