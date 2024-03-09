package com.cqrs.event.query;

import com.cqrs.event.entity.ReadProduct;
import com.cqrs.event.event.ProductCreatedEvent;
import com.cqrs.event.exception.ProductNotFoundException;
import com.cqrs.event.repository.read.ProductReadRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductQueryService {

    private final ProductReadRepository productReadRepository;

    public ProductQueryService(ProductReadRepository productReadRepository) {
        this.productReadRepository = productReadRepository;
    }

    public void handleProductCreatedEvent(ProductCreatedEvent event) {
        try {
            log.info("Received ProductCreatedEvent: {}", event);

            ReadProduct readProduct = new ReadProduct();
            readProduct.setName(event.getName());
            productReadRepository.save(readProduct);

            log.info("Updated read model for Product name {}:", event.getName());
        } catch (Exception e) {
            log.error("Error handling ProductCreatedEvent", e);
        }
    }

    public ProductView getProductById(Long productId) {
        log.info("Fetching product with ID: {}", productId);
        ReadProduct product = productReadRepository.findById(productId)
                .orElseThrow(() -> {
                    log.error("Product not found with ID: {}", productId);
                    return new ProductNotFoundException("Product not found with ID: " + productId);
                });
        ProductView productView = mapToProductView(product);

        log.info("Product fetched successfully: {}", productView);
        return productView;
    }

    public List<ProductView> getAllProducts() {
        log.info("Fetching all products");

        List<ReadProduct> productList = productReadRepository.findAll();

        List<ProductView> productViews = productList.stream()
                .map(this::mapToProductView)
                .collect(Collectors.toList());

        log.info("All products fetched successfully");
        return productViews;
    }

    private ProductView mapToProductView(ReadProduct readProduct) {
        return new ProductView(readProduct.getProductId(), readProduct.getName());
    }
}