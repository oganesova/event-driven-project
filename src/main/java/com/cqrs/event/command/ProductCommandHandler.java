package com.cqrs.event.command;

import com.cqrs.event.event.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductCommandHandler {

    private final ProductService productService;
    private final ApplicationEventPublisher eventPublisher;

    public ProductCommandHandler(ProductService productService, ApplicationEventPublisher eventPublisher) {
        this.productService = productService;
        this.eventPublisher = eventPublisher;
    }

    public void handleCreateProductCommand(CreateProductCommand command) {
        try {
            productService.createProduct(command.getName());

            ProductCreatedEvent event = new ProductCreatedEvent(command.getProductId(), command.getName());
            eventPublisher.publishEvent(event);
            log.info("Product creation command handled successfully {}", event);

        } catch (Exception e) {
            log.error("Error handling create product command", e);
        }
    }
}

