package com.geekbrains.spring.web.cart;

import com.geekbrains.spring.web.cart.dto.Cart;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FullServerRunCartTest {

    @Autowired
    private TestRestTemplate restTemplate;
    @Test
    public void fullRestTest() {
        Cart cart = restTemplate.getForObject("/api/v1/carts", Cart.class);
        assertThat(cart).isNotNull();
    }
}
