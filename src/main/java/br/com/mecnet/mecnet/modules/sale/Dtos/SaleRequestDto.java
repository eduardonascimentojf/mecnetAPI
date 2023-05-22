package br.com.mecnet.mecnet.modules.sale.Dtos;


import br.com.mecnet.mecnet.modules.sale.Entity.SaleProduct;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SaleRequestDto {
    private String seller;
    private UUID employee_id;
    private String price;
    private String client;
    private String cpfClient;

}