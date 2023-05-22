package br.com.mecnet.mecnet.modules.stock.Dtos;

import br.com.mecnet.mecnet.modules.stock.Entity.AutoStock;
import br.com.mecnet.mecnet.modules.stock.Entity.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto  {


    private String name;

    private String description;

    private Float price;

    private String brand;

    private String manufacturer;

    private Integer stock;

    private String image;

    private AutoStock autoStock;

    private Stock stock_id;

}