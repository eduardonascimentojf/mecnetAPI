package br.com.mecnet.mecnet.modules.order.services;


import br.com.mecnet.mecnet.modules.catalog.Entity.CatalogProduct;
import br.com.mecnet.mecnet.modules.catalog.repositories.CatalogRepository;
import br.com.mecnet.mecnet.modules.order.Entity.Order;
import br.com.mecnet.mecnet.modules.order.Entity.OrderItems;
import br.com.mecnet.mecnet.modules.order.repositories.OrderItemsRepository;
import br.com.mecnet.mecnet.modules.order.repositories.OrderRepository;
import br.com.mecnet.mecnet.modules.sale.Entity.Sale;
import br.com.mecnet.mecnet.modules.stock.Dtos.ProductRequestDto;
import br.com.mecnet.mecnet.modules.stock.Entity.AutoStock;
import br.com.mecnet.mecnet.modules.stock.Entity.Product;
import br.com.mecnet.mecnet.modules.stock.Entity.Stock;
import br.com.mecnet.mecnet.modules.stock.repositories.AutoStockRepository;
import br.com.mecnet.mecnet.modules.stock.repositories.ProductRepository;
import br.com.mecnet.mecnet.modules.stock.repositories.StockRepository;
import br.com.mecnet.mecnet.modules.stock.services.StockService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private CatalogRepository catalogRepository;
    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @Autowired
    private StockService stockService;
    @Autowired
    private AutoStockRepository autoStockRepository;
    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductRepository productRepository;

    public ResponseEntity<Object> setBuy(UUID id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Pedido não encontrado!");
        }
        var orderModel = new Order();
        BeanUtils.copyProperties(orderOptional.get(), orderModel);
        orderModel.setId(orderOptional.get().getId());
        orderModel.setListOrderItems(orderOptional.get().getListOrderItems());
        orderModel.setCreatedAt(orderOptional.get().getCreatedAt());
        orderModel.setFullValue(orderOptional.get().getFullValue());
        orderModel.setIsComplete(true);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderRepository.save(orderModel));

    }
    public ResponseEntity<Object> setReceived(UUID id) {
        AtomicInteger quantityProductsAdd  = new AtomicInteger();
        Optional<Order> orderOptional = orderRepository.findById(id);
        if(orderOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Pedido não encontrado!");
        }
        if(!orderOptional.get().getIsComplete()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: O pedido não foi comprado!");
        }
        if(orderOptional.get().getReceived()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: O ja foi entregue foi comprado!");
        }

        List<OrderItems> orderList = orderOptional.get().getListOrderItems();

        orderList.forEach(orderItems -> {
            Optional<OrderItems> order_itemsOptional = orderItemsRepository.findById(orderItems.getId());
            OrderItems orderItemsModel = new OrderItems();
            BeanUtils.copyProperties(order_itemsOptional.get(), orderItemsModel);
            orderItemsModel.setCreatedAt(order_itemsOptional.get().getCreatedAt());
            orderItemsModel.setId(order_itemsOptional.get().getId());
            orderItemsModel.setIsComplete(true);
            quantityProductsAdd.addAndGet(orderItems.getAmount());
            orderItemsRepository.save(orderItemsModel);
            setReceived(order_itemsOptional.get());

        });

        var orderModel = new Order();
        BeanUtils.copyProperties(orderOptional.get(), orderModel);
        orderModel.setCreatedAt(orderOptional.get().getCreatedAt());
        orderModel.setId(orderOptional.get().getId());
        orderModel.setReceived(true);
        orderModel.setIsComplete(true);

        // TODO: Marcar o Orderitems como is Complete

        stockService.setProductsQuantity(quantityProductsAdd.get());
        return ResponseEntity.status(HttpStatus.CREATED).body(orderRepository.save(orderModel));

    }
    public void updateFullValue(Float value) {
        Optional<Order> orderOptional = orderRepository.findOrderByIsComplete(false);
        if(orderOptional.isEmpty()){
            ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Pedido não encontrado!");
            return;
        }

        var orderModel = new Order();
        BeanUtils.copyProperties(orderOptional.get(), orderModel);
        orderModel.setId(orderOptional.get().getId());
        orderModel.setCreatedAt(orderOptional.get().getCreatedAt());
        orderModel.setFullValue(orderOptional.get().getFullValue()+value);
        ResponseEntity.status(HttpStatus.CREATED).body(orderRepository.save(orderModel));

    }

    public void setReceived(OrderItems data) {
        Optional<CatalogProduct> productCatalogOptional = catalogRepository.findById(data.getProductCatalogId());
        if(productCatalogOptional.isEmpty()){
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad Request: Produto não está no catalogo!");
            return;
        }

        Optional<Product> productStockOptional = productRepository.findByIdCatalog(data.getProductCatalogId());

        if(productStockOptional.isEmpty()){

            AutoStock autoStock = new AutoStock();
            autoStock = autoStockRepository.save(autoStock);
            Stock stock = stockRepository.findAll().get(0);

            Product productModel = new Product();
            productModel.setStock_id(stock);
            productModel.setAutoStock(autoStock);
            productModel.setStock(data.getAmount());
            productModel.setName(productCatalogOptional.get().getName());
            productModel.setPrice((data.getPrice()*1.30F));
            productModel.setDescription(productCatalogOptional.get().getDescription());
            productModel.setBrand(productCatalogOptional.get().getBrand());
            productModel.setManufacturer(productCatalogOptional.get().getManufacturer());
            productModel.setImage(productCatalogOptional.get().getImage());
            productModel.setIdCatalog(data.getProductCatalogId());
            Product newProduct = productRepository.save(productModel);

            Stock stockModel = new Stock();
            BeanUtils.copyProperties(stock, stockModel);
            stockModel.setId(stock.getId());
            stockModel.setCreatedAt(stock.getCreatedAt());
            stockModel.getProducts().add(newProduct);
            ResponseEntity.status(HttpStatus.CREATED).body(stockRepository.save(stockModel));

            ResponseEntity.status(HttpStatus.CREATED).body("Adicionado com sucesso");
            return;
        }
        Product productModel = new Product();
        BeanUtils.copyProperties(productStockOptional.get(), productModel);

        productModel.setId(productStockOptional.get().getId());
        productModel.setStock(data.getAmount() + productStockOptional.get().getStock());
        productModel.setPrice((data.getPrice()*1.30F));
        productModel.setCreatedAt(productStockOptional.get().getCreatedAt());
        productRepository.save(productModel);

        ResponseEntity.status(HttpStatus.CREATED).body("Atualizado com sucesso");
    }
    public ResponseEntity<Object> createOrderItems(OrderItems data) {
        var orderOptional = orderRepository.findOrderByIsComplete(false);
        if(orderOptional.isEmpty()){
            Order newOrder = new Order();
            orderRepository.save(newOrder);
            orderOptional = orderRepository.findOrderByIsComplete(false);
        }
        List<OrderItems> items = new ArrayList<>(orderOptional.get().getListOrderItems());
        for (OrderItems item : items) {
            if (item.getProductCatalogId().equals(data.getProductCatalogId())) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Item ja nos pedido");
            }
        }

        Float valueFull = data.getPrice() * data.getAmount();
        data.setFullValue(valueFull);
        data.setOrder_id(orderOptional.get());
        OrderItems orderItems = orderItemsRepository.save(data);

        orderOptional.get().setFullValue(orderOptional.get().getFullValue() + valueFull);
        orderOptional.get().getListOrderItems().add(orderItems);
        Order orderModel = new Order();
        BeanUtils.copyProperties(orderOptional.get(), orderModel);
        orderModel.setId(orderOptional.get().getId());
        orderModel.setCreatedAt(orderOptional.get().getCreatedAt());
        orderModel.setFullValue(orderOptional.get().getFullValue());
        ResponseEntity.status(HttpStatus.CREATED).body(orderRepository.save(orderModel));


        return ResponseEntity.status(HttpStatus.CREATED).body(orderItems);
    }
    public ResponseEntity<Object> updateOrderItems(OrderItems orderItem, UUID id) {
        Optional<OrderItems> orderItemsOptional = orderItemsRepository.findById(id);

        if (orderItemsOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Pedido Item não encontrado!");
        }
        Optional<Order> orderOptional = orderRepository.findOrderByIsComplete(false );
        if(orderOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Produto já comprado!");
        }

        if(orderItemsOptional.get().getIsComplete()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Produto já comprado!");
        }
        List<OrderItems> listOrderItems = orderOptional.get().getListOrderItems();

        var inActive = false;
        for (OrderItems listOrderItem : listOrderItems) {
            if (listOrderItem.getId() == id) {
                inActive = true;
                break;
            }
        }
        if(!inActive){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Produto já comprado!");
        }

        var orderItemsModel = new OrderItems();
        BeanUtils.copyProperties(orderItemsOptional.get(), orderItemsModel);
        orderItemsModel.setId(orderItemsOptional.get().getId());
        orderItemsModel.setAmount(orderItem.getAmount());
        orderItemsModel.setId(orderItemsOptional.get().getId());
        orderItemsModel.setOrder_id(orderItemsOptional.get().getOrder_id());
        orderItemsModel.setCreatedAt(orderItemsOptional.get().getCreatedAt());
        orderItemsModel.setFullValue(orderItemsOptional.get().getFullValue());

        Float newValueFull = orderItemsOptional.get().getPrice() * orderItem.getAmount();
        Float oldValueFull = orderItemsModel.getFullValue();
        Float auxValueFull = newValueFull - oldValueFull;

        orderItemsModel.setFullValue(newValueFull);
        updateFullValue(auxValueFull);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderItemsRepository.save(orderItemsModel));
    }
    public ResponseEntity<Object> getAllPrice(){
        float priceTotal = 0;
        List<Order> orderPriceOptional = orderRepository.findAll();
        if(orderPriceOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Conflict: Não existe pedido no sistema !");

        }
        for (Order order : orderPriceOptional) {
            if(order.getIsComplete())
                priceTotal+= order.getFullValue();
        }
        return ResponseEntity.status(HttpStatus.OK).body(priceTotal);

    }
}
