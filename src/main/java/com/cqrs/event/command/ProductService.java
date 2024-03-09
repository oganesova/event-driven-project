package com.cqrs.event.command;

import com.cqrs.event.entity.ReadProduct;
import com.cqrs.event.entity.WriteProduct;
import com.cqrs.event.event.ProductCreatedEvent;
import com.cqrs.event.repository.read.ProductReadRepository;
import com.cqrs.event.repository.write.ProductWriteRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Slf4j
public class ProductService {

    private final ProductWriteRepository productWriteRepository;
    private final ProductReadRepository productReadRepository;
    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductService(
            ProductWriteRepository productWriteRepository,
            ProductReadRepository productReadRepository,
            KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.productWriteRepository = productWriteRepository;
        this.productReadRepository = productReadRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Transactional
    public void createProduct(String name) {
        validateProductData(name);

        WriteProduct product = new WriteProduct();
        product.setName(name);
        productWriteRepository.save(product);

        log.trace("Product created successfully with name: {}", name);

        notifyProductCreation(name);
    }

    @Transactional
    public void handleProductCreatedEvent(ProductCreatedEvent event) {
        validateProductData(event.getName());

        ReadProduct readProduct = new ReadProduct();
        readProduct.setName(event.getName());
        productReadRepository.save(readProduct);

        log.trace("Updated read model for Product ID {}: {}", event.getProductId(), event.getName());
    }

    private void validateProductData(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Product name cannot be null or empty");
        }
    }

    private void notifyProductCreation(String name) {
        ProductCreatedEvent event = new ProductCreatedEvent(generateProductId(), name);
        kafkaTemplate.send("product-event", event);
        log.trace("Product creation command handled successfully, and event sent to Kafka: {}", event);
    }

    private Long generateProductId() {
        UUID uuid = UUID.randomUUID();
        return uuid.getMostSignificantBits() & Long.MAX_VALUE;
    }
}