package br.com.mecnet.mecnet.modules.stock.Entity;

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
@Table(name = "TB_AUTO_STOCK")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutoStock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_AutoStock", nullable = false, unique = true)
    private UUID id_AutoStock;

    @Column(name = "automates", nullable = false)
    private Boolean automates = false;

    @Column(name = "maxPrice", nullable = false)
    private Float maxPrice = 0F;

    @Column(name = "minPrice", nullable = false)
    private Float minPrice = 0F;

    @Column(name = "maxQuantity", nullable = false)
    private Integer maxQuantity = 0;

    @Column(name = "minQuantity", nullable = false)
    private Integer minQuantity = 0;


    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToOne()
    @JoinColumn(name = "product_id")
    private Product product_id;
}
