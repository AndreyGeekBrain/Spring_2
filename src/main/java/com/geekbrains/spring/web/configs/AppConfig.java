package com.geekbrains.spring.web.configs;

import org.springframework.context.annotation.*;

@Configuration
@EnableAspectJAutoProxy
@PropertySource("secrets.properties")
@ComponentScan("com.geekbrains.spring.web")
public class AppConfig {
}
