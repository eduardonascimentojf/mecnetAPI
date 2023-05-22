package br.com.mecnet.mecnet.modules.sale.Dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
public class SaleProductDto {

   private UUID id_productStock;
   private Float price;
   private Integer amount;

}
