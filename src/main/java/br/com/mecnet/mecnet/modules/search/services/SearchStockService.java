package br.com.mecnet.mecnet.modules.search.services;

import br.com.mecnet.mecnet.modules.search.repositories.SearchStockRepository;
import br.com.mecnet.mecnet.modules.stock.Entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchStockService {
    SearchStockRepository searchStockRepository;

    public SearchStockService(SearchStockRepository searchStockRepository) {
        this.searchStockRepository = searchStockRepository;
    }

    public Page<Product> listProducts(Pageable pageable) {
        return searchStockRepository.findAll(pageable);
    }
}
