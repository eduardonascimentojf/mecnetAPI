package br.com.mecnet.mecnet.modules.sale;


import br.com.mecnet.mecnet.modules.sale.Dtos.SaleRequestDto;
import br.com.mecnet.mecnet.modules.sale.Entity.Sale;
import br.com.mecnet.mecnet.modules.sale.repositories.SaleRepository;
import br.com.mecnet.mecnet.modules.sale.services.SaleService;
import br.com.mecnet.mecnet.modules.stock.Dtos.RemoveItemsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Sort;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/sale")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class SaleController {

    @Autowired
    private SaleRepository saleRepository;
    @Autowired
    private SaleService saleService;

    @GetMapping("/{id}")
    @RolesAllowed({"USER","ADMIN"})
    public Optional<Sale> getSale(@PathVariable(value = "id") UUID id){
        return saleRepository.findById(id);
    }

    @GetMapping("/priceSaleTotal")
    @RolesAllowed({"USER","ADMIN"})
    public ResponseEntity<Object> getAllPrice(){
        return saleService.getAllPrice();
    }

    @GetMapping("/salesEmploy")
    @RolesAllowed({"USER","ADMIN"})
    public List<Sale> getSalesEmploy(Authentication authentication){
        return saleRepository.findAllBySeller(authentication.getName());
    }
    @GetMapping("/all")
    @RolesAllowed("ADMIN")
    public List<Sale> getAllSale(){
        //TODO: Retornar so os comprados
        return saleRepository.findAll();
    }

    @PostMapping()
    @RolesAllowed({"USER","ADMIN"})
    public ResponseEntity<Object> createSale(Authentication authentication, @RequestBody SaleRequestDto data){
        return saleService.createSale(data, authentication.getName());
    }

//    @PostMapping("/addItems/{id}")
//    @RolesAllowed({"USER","ADMIN"})
//    public ResponseEntity<Object> addProductInSale(@RequestBody SaleProductDto data,
//                                                   @PathVariable(value = "id") UUID id){
//        return saleService.addProductInSale(data, id);
//    }

//    @GetMapping("/checkout/{id}")
//    @RolesAllowed({"USER","ADMIN"})
//    public ResponseEntity<Object> checkout(@PathVariable(value = "id") UUID id){
//        return saleService.checkout(id);
//    }

    @DeleteMapping("/removeItems")
    @RolesAllowed({"USER","ADMIN"})
    public ResponseEntity<Object> removeProductInSale(@RequestBody RemoveItemsDto data){
        return saleService.removeProductInSale(data);
    }

    @GetMapping("/cancelSale/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<String> cancelSale(@PathVariable(value = "id") UUID id){
        saleService.cancelSale(id);
        return ResponseEntity.status(HttpStatus.OK).body("Compra Cancelada!");
    }
}