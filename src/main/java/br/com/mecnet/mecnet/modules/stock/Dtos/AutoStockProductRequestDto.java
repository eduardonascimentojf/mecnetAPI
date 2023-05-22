package br.com.mecnet.mecnet.modules.stock.Dtos;

import br.com.mecnet.mecnet.modules.stock.Entity.AutoStock;
import br.com.mecnet.mecnet.modules.stock.Entity.Stock;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutoStockProductRequestDto {


    private UUID id;
    private Boolean automates;
    private Float maxPrice;
    private Float minPrice;
    private Integer maxQuantity;
    private Integer minQuantity = 0;

}