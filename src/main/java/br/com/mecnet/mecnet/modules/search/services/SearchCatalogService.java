package br.com.mecnet.mecnet.modules.search.services;

import br.com.mecnet.mecnet.modules.catalog.Entity.CatalogProduct;
import br.com.mecnet.mecnet.modules.search.repositories.SearchCatalogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchCatalogService {
    SearchCatalogRepository searchCatalogRepository;

    public SearchCatalogService(SearchCatalogRepository searchCatalogRepository) {
        this.searchCatalogRepository = searchCatalogRepository;
    }

    public Page<CatalogProduct> listProductsCatalog(Pageable pageable) {
        return searchCatalogRepository.findAll(pageable);
    }
}
