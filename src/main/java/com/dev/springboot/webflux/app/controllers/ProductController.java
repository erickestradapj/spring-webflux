package com.dev.springboot.webflux.app.controllers;

import com.dev.springboot.webflux.app.models.documents.Product;
import com.dev.springboot.webflux.app.models.repository.IProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Controller
public class ProductController {

    @Autowired
    private IProductRepository productRepository;

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @GetMapping({"/list", ""})
    public String list(Model model) {
        Flux<Product> productFlux = productRepository.findAll().map(product -> {
            product.setName(product.getName().toUpperCase());
            return product;
        });

        productFlux.subscribe(product -> log.info(product.getName()));

        model.addAttribute("products", productFlux);
        model.addAttribute("title", "Spring with WebFlux");

        return "list";
    }

    @GetMapping("/list-datadriver")
    public String listDataDriver(Model model) {
        Flux<Product> productFlux = productRepository.findAll().map(product -> {
            product.setName(product.getName().toUpperCase());
            return product;
        }).delayElements(Duration.ofSeconds(1));

        productFlux.subscribe(product -> log.info(product.getName()));

        model.addAttribute("products", new ReactiveDataDriverContextVariable(productFlux, 1));
        model.addAttribute("title", "Spring with WebFlux");
        return "list";
    }

    @GetMapping("/list-full")
    public String listFull(Model model) {
        Flux<Product> productFlux = productRepository.findAll().map(product -> {
            product.setName(product.getName().toUpperCase());
            return product;
        }).repeat(5000);

        model.addAttribute("products", productFlux);
        model.addAttribute("title", "Spring with WebFlux");
        return "list";
    }

    @GetMapping("/list-chunked")
    public String listChunked(Model model) {
        Flux<Product> productFlux = productRepository.findAll().map(product -> {
            product.setName(product.getName().toUpperCase());
            return product;
        }).repeat(5000);

        model.addAttribute("products", productFlux);
        model.addAttribute("title", "Spring with WebFlux");
        return "list-chunked";
    }
}
