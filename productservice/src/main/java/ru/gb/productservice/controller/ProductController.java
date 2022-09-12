package ru.gb.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gb.productservice.dto.ProductDto;
import ru.gb.productservice.entity.ProductEntity;
import ru.gb.productservice.service.ProductsService;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductsService productsService;

    @GetMapping("/api/v1/product-all")
    public List<ProductDto> getProductAll() throws InterruptedException {
        List<ProductEntity> productEntityList = productsService.findAll();
        List<ProductDto> productDtoList = new ArrayList<>();
        for (int i = 0; i < productEntityList.size(); i++) {
            ProductEntity productEntity = productEntityList.get(i);
            productDtoList.add(i,new ProductDto(productEntity.getId(), productEntity.getTitle(), productEntity.getPrice()));
        }
        return productDtoList;
    }
}
