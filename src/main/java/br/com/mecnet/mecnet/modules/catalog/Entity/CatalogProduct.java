package br.com.mecnet.mecnet.modules.catalog.Entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "TB_PRODUCT_CATALOG")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class CatalogProduct {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

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
    private String image;

    public CatalogProduct(CatalogProduct catalogProduct) {
        this.id = catalogProduct.id;
        this.name = catalogProduct.name;
        this.description = catalogProduct.description;
        this.price = catalogProduct.price;
        this.brand = catalogProduct.brand;
        this.manufacturer = catalogProduct.manufacturer;
        this.stock = catalogProduct.stock;
        this.image = catalogProduct.image;
    }
}
