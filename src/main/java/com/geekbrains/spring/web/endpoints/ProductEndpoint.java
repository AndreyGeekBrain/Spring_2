package com.geekbrains.spring.web.endpoints;

import com.geekbrains.spring.web.entities.Product;
import com.geekbrains.spring.web.services.ProductsService;
import com.geekbrains.spring.web.soap.soap.GetAllProductResponse;
import com.geekbrains.spring.web.soap.soap.GetAllUsersRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import java.util.function.Consumer;

@Endpoint
@RequiredArgsConstructor
public class ProductEndpoint {

    private static final String NAMESPACE_URI = "http://www.mvg.com/spring/ws/products";
    private final ProductsService productsService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getAllProductRequest")
    @ResponsePayload
    public GetAllProductResponse getAllUsers(@RequestPayload GetAllUsersRequest request) {
        GetAllProductResponse response = new GetAllProductResponse();
        productsService.productAll().forEach(new Consumer<Product>() {
            @Override
            public void accept(Product product) {
                com.geekbrains.spring.web.soap.soap.Product p = new com.geekbrains.spring.web.soap.soap.Product();
                p.setId(product.getId());
                p.setTitle(product.getTitle());
                p.setPrice(product.getPrice());
                response.getProducts().add(p);
            }
        });
        return response;
    }
}
