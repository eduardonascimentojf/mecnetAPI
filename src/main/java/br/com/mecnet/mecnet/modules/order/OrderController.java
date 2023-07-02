package br.com.mecnet.mecnet.modules.order;
import br.com.mecnet.mecnet.modules.order.Dtos.OrderItemsDeleteDto;
import br.com.mecnet.mecnet.modules.order.Entity.Order;
import br.com.mecnet.mecnet.modules.order.Entity.OrderItems;
import br.com.mecnet.mecnet.modules.order.repositories.OrderItemsRepository;
import br.com.mecnet.mecnet.modules.order.repositories.OrderRepository;
import br.com.mecnet.mecnet.modules.order.services.OrderService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping("/order")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemsRepository orderItemsRepository;
    @GetMapping
    @RolesAllowed({"USER","ADMIN"})
    public Optional<Order> getOrderActive(){
        return orderRepository.findOrderByIsComplete(false);
    }
    @GetMapping("/all")
    @RolesAllowed({"USER","ADMIN"})
    public List<Order> getAllOrder(){
        return orderRepository.findAllByIsComplete(true, Sort.by(Sort.Direction.DESC, "updatedAt"));
    }
    @GetMapping("/priceOrderTotal")
    @RolesAllowed({"USER","ADMIN"})
    public ResponseEntity<Object> getAllPrice(){
        return orderService.getAllPrice();
    }
    @GetMapping("/{id}")
    @RolesAllowed({"USER","ADMIN"})
        public Optional<Order> getOrderById(@PathVariable(value = "id") UUID id){
        return orderRepository.findById(id);
    }

    @GetMapping("/setBuy/{id}")
    @RolesAllowed({"ADMIN"})
    public ResponseEntity<Object> setBuy(@PathVariable(value = "id") UUID id){
        return orderService.setBuy(id);
    }
    @GetMapping("/setReceived/{id}")
    @RolesAllowed({"ADMIN"})
    public ResponseEntity<Object> setReceived(@PathVariable(value = "id") UUID id){
        return orderService.setReceived(id);
    }

    @GetMapping("/orderItems")
    @RolesAllowed({"USER","ADMIN"})
    public List<OrderItems> getAllOrderItems(){
        return orderItemsRepository.findAll().stream().map(OrderItems::new).toList();
    }
    @GetMapping("/orderItems/{id}")
    @RolesAllowed({"USER","ADMIN"})
    public Optional<OrderItems> getOrderItems(@PathVariable(value = "id") UUID id){
        return orderItemsRepository.findById(id);
    }
    @PostMapping("/orderItems")
    @RolesAllowed({"USER","ADMIN"})
    public ResponseEntity<Object> createOrderItems(@RequestBody OrderItems data ){
        return orderService.createOrderItems(data);
    }
    @PutMapping("/orderItems/{id}")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Object> updateOrderItems(@RequestBody OrderItems data, @PathVariable(value = "id") UUID id){
        return orderService.updateOrderItems(data, id);
    }
    @DeleteMapping("/orderItems")
    @RolesAllowed("ADMIN")
    public ResponseEntity<Object> deleteOrderItems(@RequestBody OrderItemsDeleteDto data){
        Optional<OrderItems> orderItemsOptional = orderItemsRepository.findById(data.id());
        Optional<Order> orderOptional = orderRepository.findOrderByIsComplete(false);
        if(orderOptional.isEmpty() || orderItemsOptional.isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro interno");
        }
        Order orderModel = new Order();
        BeanUtils.copyProperties(orderOptional.get(), orderModel);
        orderModel.setId(orderOptional.get().getId());
        orderModel.setCreatedAt(orderOptional.get().getCreatedAt());
        orderModel.setFullValue(orderOptional.get().getFullValue() - orderItemsOptional.get().getFullValue());
        orderModel.getListOrderItems().remove(orderItemsOptional.get());
        orderModel.setListOrderItems(orderModel.getListOrderItems());
        ResponseEntity.status(HttpStatus.CREATED).body(orderRepository.save(orderModel));
        orderItemsRepository.deleteById(data.id());
        return ResponseEntity.status(HttpStatus.OK).body("Removido com sucesso");
    }
}
