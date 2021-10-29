package com.dev.springboot.webflux.app;

import com.dev.springboot.webflux.app.models.documents.Product;
import com.dev.springboot.webflux.app.models.repository.IProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.util.Date;

@SpringBootApplication
public class SpringBootWebfluxApplication implements CommandLineRunner {

    @Autowired
    private IProductRepository productRepository;

    @Autowired
    private ReactiveMongoTemplate mongoTemplate;

    private Logger log = LoggerFactory.getLogger(SpringBootWebfluxApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootWebfluxApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        mongoTemplate.dropCollection("products");

        Flux.just(new Product("TV Panasonic Pantalla LCD", 456.89),
                        new Product("Sony Camara HD Digital", 177.89),
                        new Product("Apple iPod", 46.89),
                        new Product("Sony Notebook", 846.89),
                        new Product("Hewlett Packard Multifuncional", 200.89),
                        new Product("Bianchi Bicicleta", 70.89),
                        new Product("HP Notebook Omen 17", 2500.89),
                        new Product("Mica Cómoda 5 Cajones", 150.89),
                        new Product("TV Sony Bravia OLED 4K Ultra HD", 2255.89)
                )
                .flatMap(product -> {
                    product.setCreateAt(new Date());
                    return productRepository.save(product);
                })
                .subscribe(product -> log.info("Insert: " + product.getId() + " " + product.getName()));
    }
}
