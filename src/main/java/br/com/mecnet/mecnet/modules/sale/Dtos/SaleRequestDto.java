package br.com.mecnet.mecnet.modules.sale.Dtos;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleRequestDto {

    private String client;
    private String cpfClient;
    private List<SaleProductDto> saleProducts;

}