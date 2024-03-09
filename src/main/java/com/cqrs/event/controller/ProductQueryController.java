package com.cqrs.event.controller;

import com.cqrs.event.exception.ProductNotFoundException;
import com.cqrs.event.query.ProductQueryService;
import com.cqrs.event.query.ProductView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/queries/products")
@Slf4j
public class ProductQueryController {

    private final ProductQueryService productQueryService;

    public ProductQueryController(ProductQueryService productQueryService) {
        this.productQueryService = productQueryService;
    }
    @GetMapping("/list")
    public List<ProductView> getAllProducts() {
        return productQueryService.getAllProducts();
    }
    @GetMapping("/{productId}")
    public ResponseEntity<ProductView> getProductById(@PathVariable Long productId) {
        log.info("Received request to retrieve product with ID: {}", productId);

        try {
            ProductView productView = productQueryService.getProductById(productId);
            log.info("Product retrieved successfully: {}", productView);
            return ResponseEntity.ok(productView);
        } catch (ProductNotFoundException e) {
            log.error("Product not found with ID: {}", productId);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            log.error("Error retrieving product with ID: {}", productId, e);
            return ResponseEntity.status(500).build();
        }
    }}
