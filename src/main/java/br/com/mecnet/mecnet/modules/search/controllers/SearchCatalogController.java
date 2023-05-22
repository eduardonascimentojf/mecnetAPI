package br.com.mecnet.mecnet.modules.search.controllers;

import br.com.mecnet.mecnet.modules.catalog.Entity.CatalogProduct;
import br.com.mecnet.mecnet.modules.search.Dtos.SearchProductCatalogDto;
import br.com.mecnet.mecnet.modules.search.repositories.SearchCatalogRepository;
import br.com.mecnet.mecnet.modules.search.services.SearchCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/searchCatalog")
public class SearchCatalogController {
    @Autowired
    SearchCatalogService searchCatalogService;
    @Autowired
    SearchCatalogRepository searchCatalogRepository;
    @GetMapping
    public List<CatalogProduct> listProductSearch(Pageable pageable) {
        return searchCatalogService.listProductsCatalog(pageable).getContent();
    }


    @GetMapping("/queryDynamic")
    public List<CatalogProduct> listProductSearchDynamic(SearchProductCatalogDto searchProductCatalogDto, Pageable pageable) {
        return searchCatalogRepository.findAll(searchProductCatalogDto.toSpac(), pageable).getContent();
    }
    @GetMapping("/queryDynamicLike")
    public List<CatalogProduct> listProductSearchDynamicLike (SearchProductCatalogDto searchProductCatalogDto, Pageable pageable) {
        return searchCatalogRepository.findAll(searchProductCatalogDto.toSpacLike(), pageable).getContent();
    }

}
