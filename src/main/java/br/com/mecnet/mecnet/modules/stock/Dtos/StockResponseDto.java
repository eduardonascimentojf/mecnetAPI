package br.com.mecnet.mecnet.modules.stock.Dtos;

import br.com.mecnet.mecnet.modules.stock.Entity.Stock;
import java.util.UUID;

public record StockResponseDto(UUID id, Integer capacity, Integer productsQuantity)  {
    public StockResponseDto(Stock stock) {
        this(stock.getId(), stock.getCapacity(), stock.getProductsQuantity());
    }

}

