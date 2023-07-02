package br.com.mecnet.mecnet.modules.order.Entity;

import br.com.mecnet.mecnet.modules.stock.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;



@Entity
@Table(name = "TB_ORDER")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "isComplete", nullable = false)
    private Boolean isComplete = false;


    @Column(name = "received", nullable = false)
    private Boolean received = false;

    @Column(name = "fullValue", nullable = false)
    private Float fullValue = 0F;

//    @OneToMany(mappedBy = "order_id")
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "order_id", cascade = CascadeType.ALL)
    private List<OrderItems> listOrderItems = new ArrayList<>();

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;



}

