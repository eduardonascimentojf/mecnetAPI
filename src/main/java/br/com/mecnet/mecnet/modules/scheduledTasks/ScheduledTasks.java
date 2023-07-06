package br.com.mecnet.mecnet.modules.scheduledTasks;

import br.com.mecnet.mecnet.modules.catalog.Entity.CatalogProduct;
import br.com.mecnet.mecnet.modules.catalog.repositories.CatalogRepository;
import br.com.mecnet.mecnet.modules.order.Entity.OrderItems;
import br.com.mecnet.mecnet.modules.order.services.OrderService;
import br.com.mecnet.mecnet.modules.stock.Entity.Product;
import br.com.mecnet.mecnet.modules.stock.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;


@Service
@Async
public class ScheduledTasks {
    @Autowired
    ProductRepository productRepository;
    @Autowired
    CatalogRepository catalogRepository;
    @Autowired
    OrderService orderService;

    @Scheduled(cron = "0 0/10 * * * *")
    public void execute()  {

        List<Product> products = productRepository.findAll();
        if (products.isEmpty()) return;
        for (Product product : products) {

            if (product.getAutoStock().getAutomates()) {

                UUID idCatalog = product.getIdCatalog();
                Optional<CatalogProduct> catalogProduct = catalogRepository.findById(idCatalog);
                if (catalogProduct.isEmpty()) {
                    return;
                }

                if (catalogProduct.get().getPrice() <= product.getAutoStock().getMaxPrice() &&
                        catalogProduct.get().getPrice() >= product.getAutoStock().getMinPrice() &&
                        product.getStock() <= product.getAutoStock().getMaxQuantity() &&
                        product.getStock() >= product.getAutoStock().getMinQuantity()
                ) {
                    OrderItems newOrderItems = new OrderItems();
                    newOrderItems.setProductCatalogId(product.getIdCatalog());
                    newOrderItems.setAmount(1);
                    newOrderItems.setPrice(product.getPrice());
                    newOrderItems.setFullValue(product.getPrice());
                    newOrderItems.setDescription(product.getDescription());
                    ResponseEntity<Object> object= orderService.createOrderItems(newOrderItems);
                    System.out.println(Objects.requireNonNull(object.getBody()).toString());
                }

            }
        }
    }
}