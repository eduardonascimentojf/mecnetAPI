package br.com.mecnet.mecnet.modules.order.Dtos;

import br.com.mecnet.mecnet.modules.stock.Entity.AutoStock;
import br.com.mecnet.mecnet.modules.stock.Entity.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDto {


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