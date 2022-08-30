package com.geekbrains.spring.web;

import com.geekbrains.spring.web.configs.AppConfig;
import com.geekbrains.spring.web.services.CartService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
@EnableCaching
public class SpringWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebApplication.class, args);

//=============================================================
//?????????????????????????????????????????????????????????????

//		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//
//		CartService cartService = context.getBean(CartService.class);
//		cartService.clear();
//		cartService.getCurrentCart();
//		cartService.addProductByIdToCart();

	}
}
