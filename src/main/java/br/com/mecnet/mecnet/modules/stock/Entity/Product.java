package br.com.mecnet.mecnet.modules.stock.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;


@Entity
@Table(name = "TB_STOCK_PRODUCT")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Product {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "idCatalog")
    private UUID idCatalog = null;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "price", nullable = false)
    private Float price;

    @Column(name = "brand", nullable = false)
    private String brand;

    @Column(name = "manufacturer", nullable = false)
    private String manufacturer;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "image", nullable = false)
    private ArrayList<String> image;

    @OneToOne
    @JoinColumn(name = "autoStock_id")
    private AutoStock autoStock;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "stock_id")
    private Stock stock_id;

    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Product(Product product){
      this.id = product.id;
      this.idCatalog = product.idCatalog;
      this.name = product.name;
      this.description = product.description;
      this.price = product.price;
      this.brand = product.brand;
      this.manufacturer = product.manufacturer;
      this.stock = product.stock;
      this.image = product.image;
      this.autoStock = product.autoStock;
      this.stock_id = product.stock_id;
      this.createdAt = product.createdAt;
      this.updatedAt = product.updatedAt;

    }
}
