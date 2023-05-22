package br.com.mecnet.mecnet.modules.search.controllers;

import br.com.mecnet.mecnet.modules.search.Dtos.SearchProductDto;
import br.com.mecnet.mecnet.modules.search.repositories.SearchStockRepository;
import br.com.mecnet.mecnet.modules.search.services.SearchStockService;
import br.com.mecnet.mecnet.modules.stock.Entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/searchProduct")
public class SearchStockController {
    @Autowired
    SearchStockService searchStockService;
    @Autowired
    SearchStockRepository searchStockRepository;
    @GetMapping
    public List<Product> listProductSearch(Pageable pageable) {
        return searchStockService.listProducts(pageable).getContent();
    }


    @GetMapping("/queryDynamic")
    public List<Product> listProductSearchDynamic(SearchProductDto searchProductDto, Pageable pageable) {
        return searchStockRepository.findAll(searchProductDto.toSpac(), pageable).getContent();
    }
    @GetMapping("/queryDynamicLike")
    public List<Product> listProductSearchDynamicLike (SearchProductDto searchProductDto, Pageable pageable) {
        return searchStockRepository.findAll(searchProductDto.toSpacLike(), pageable).getContent();
    }

}
