package br.com.mecnet.mecnet.modules.stock.Dtos;

import br.com.mecnet.mecnet.modules.stock.Entity.Product;
import br.com.mecnet.mecnet.modules.stock.Entity.Stock;
import java.util.List;
import java.util.UUID;

public record StockResponseDto(UUID id, Integer capacity)  {
    public StockResponseDto(Stock stock) {
        this(stock.getId(), stock.getCapacity());
    }

}

