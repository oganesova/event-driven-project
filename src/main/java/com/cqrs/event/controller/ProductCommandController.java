package com.cqrs.event.controller;

import com.cqrs.event.command.CreateProductCommand;
import com.cqrs.event.command.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/commands/products")
@Slf4j
public class ProductCommandController {

    private final ProductService productService;

    public ProductCommandController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/")
    public ResponseEntity<String> createProduct(@RequestBody CreateProductCommand command) {
        log.info("Received create product command: {}", command);

        try {
            productService.createProduct(command.getName());

            log.info("Product creation command handled successfully");
            return new ResponseEntity<>("Product creation command received", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error handling product creation command", e);
            return new ResponseEntity<>("Error handling product creation command", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}