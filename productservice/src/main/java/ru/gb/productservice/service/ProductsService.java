package ru.gb.productservice.service;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.gb.productservice.entity.ProductEntity;
import ru.gb.productservice.repository.ProductsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductsService {
    private final ProductsRepository productsRepository;

    public List<ProductEntity> findAll() {
        return productsRepository.findAll();
    }

}
