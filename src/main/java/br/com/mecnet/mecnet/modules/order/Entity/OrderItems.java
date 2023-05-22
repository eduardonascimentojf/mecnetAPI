package br.com.mecnet.mecnet.modules.order.Entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "TB_ORDER_ITEMS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "productCatalogId", nullable = false)
    private UUID productCatalogId;

    @Column(name = "isComplete", nullable = false)
    private Boolean isComplete = false;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "amount", nullable = false)
    private Integer amount;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "fullValue", nullable = false)
    private Float fullValue = 0F;;

    @OneToOne
    @JoinColumn(name = "order_id")
    private Order order_id;


    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public OrderItems(OrderItems orderItems) {
        this.id = orderItems.id;
        this.productCatalogId = orderItems.productCatalogId;
        this.isComplete = orderItems.isComplete;
        this.description = orderItems.description;
        this.amount = orderItems.amount;
        this.price = orderItems.price;
        this.fullValue = orderItems.fullValue;
        this.order_id = orderItems.order_id;
        this.createdAt = orderItems.createdAt;
        this.updatedAt = orderItems.updatedAt;
    }
}
