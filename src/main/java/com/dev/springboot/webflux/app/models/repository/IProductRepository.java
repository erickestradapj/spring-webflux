package com.dev.springboot.webflux.app.models.repository;

import com.dev.springboot.webflux.app.models.documents.Product;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IProductRepository extends ReactiveCrudRepository<Product, String> {


}
