package br.com.mecnet.mecnet.modules.catalog;


import br.com.mecnet.mecnet.modules.catalog.Dtos.CatalogProductDeleteDt0;
import br.com.mecnet.mecnet.modules.catalog.Dtos.CatalogProductDt0;
import br.com.mecnet.mecnet.modules.catalog.Entity.CatalogProduct;
import br.com.mecnet.mecnet.modules.catalog.repositories.CatalogRepository;
import br.com.mecnet.mecnet.modules.catalog.services.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/catalog")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class CatalogProductController {

    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private CatalogService catalogService;


    @GetMapping()
    @RolesAllowed({"USER","ADMIN"})
    public List<CatalogProduct> getAllProductsOfCatalog(){
        return catalogRepository.findAll().stream().map(CatalogProduct::new).toList();
    }
    @GetMapping("/{id}")
    @RolesAllowed({"USER","ADMIN"})
    public Optional<CatalogProduct> getProductOfCatalog(@PathVariable(value = "id") UUID id){
        return catalogRepository.findById(id);
    }

    @PostMapping()
    @RolesAllowed("ADMIN")
    public ResponseEntity<Object> createProductInCatalog(@RequestBody @Valid CatalogProduct data){
        if(catalogRepository.existsByName(data.getName()) &&
                catalogRepository.existsByBrand(data.getBrand()) &&
                catalogRepository.existsByManufacturer(data.getManufacturer())){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Produto já cadastrado!");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(catalogRepository.save(data));
    }
    @PutMapping("/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Object> updateProductInCatalog(@RequestBody CatalogProductDt0 data, @PathVariable(value = "id") UUID id){
        return catalogService.updateProductInCatalog(data, id);
    }
    @DeleteMapping()
    @RolesAllowed("ADMIN")
    public ResponseEntity<String> deleteProduct(@RequestBody CatalogProductDeleteDt0 deleteDt0){
        catalogRepository.deleteById(deleteDt0.id());
        return ResponseEntity.status(HttpStatus.OK).body("Excluído com sucesso");
    }

}