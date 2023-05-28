package br.com.mecnet.mecnet.modules.stock.Dtos;

import br.com.mecnet.mecnet.modules.stock.Entity.AutoStock;
import br.com.mecnet.mecnet.modules.stock.Entity.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;


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

    private ArrayList<String> image;

    private AutoStock autoStock;

    private Stock stock_id;

}