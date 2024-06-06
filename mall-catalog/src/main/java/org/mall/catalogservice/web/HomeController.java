package org.mall.catalogservice.web;

import org.mall.catalogservice.config.MallProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private final MallProperties mallProperties;

    public HomeController(MallProperties mallProperties) {
        this.mallProperties = mallProperties;
    }

    @GetMapping("/")
    public String getGreeting() {
        return mallProperties.getGreeting();
    }
}