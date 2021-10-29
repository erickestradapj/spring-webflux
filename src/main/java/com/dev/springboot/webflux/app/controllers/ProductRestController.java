package com.dev.springboot.webflux.app.controllers;

import com.dev.springboot.webflux.app.models.documents.Product;
import com.dev.springboot.webflux.app.models.repository.IProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/products")
public class ProductRestController {

    @Autowired
    private IProductRepository productRepository;

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    @GetMapping()
    public Flux<Product> index() {

        Flux<Product> productFlux = productRepository.findAll()
                .map(product -> {
                    product.setName(product.getName().toUpperCase());
                    return product;
                }).doOnNext(product -> log.info(product.getName()));

        return productFlux;
    }

    @GetMapping("/{id}")
    public Mono<Product> show(@PathVariable String id) {
//        Mono<Product> productFlux = productRepository.findById(id);
//        return productFlux;

        Flux<Product> productFlux = productRepository.findAll();

        Mono<Product> productMono = productFlux.filter(product -> product.getId().equals(id))
                .next()
                .doOnNext(product -> log.info(product.getName()));

        return productMono;
    }
}
