package br.com.mecnet.mecnet.modules.sale.Entity;


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
@Table(name = "TB_SALE_PRODUCT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class SaleProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "id_productStock", nullable = false)
    private UUID id_productStock;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "amount", nullable = false)
    private Integer amount = 0;

    @Column(name = "fullValue", nullable = false)
    private Float fullValue = 0f;


    @ManyToOne
    @JoinColumn(name = "sale_id")
    private Sale sale_id;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
