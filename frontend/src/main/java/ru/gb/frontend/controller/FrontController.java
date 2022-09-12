package ru.gb.frontend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.gb.frontend.FrontendApplication;
import ru.gb.frontend.dto.ProductDto;

import java.util.List;

@RestController
public class FrontController {
    @Autowired
    private RestTemplate restTemplate;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(FrontendApplication.class, args);
    }

    @GetMapping("/api/v1/product/product-all")
    public String getProductAll() {
        ResponseEntity<ProductDto> productDtoResponseEntity = restTemplate.getForEntity("http://productsrvice/api/v1/product-all",ProductDto.class);
        return "Все продукты из БД: " + productDtoResponseEntity;
    }
}
