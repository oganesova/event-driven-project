package com.cqrs.event.event;

import com.cqrs.event.command.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductEventHandler {
    private final ProductService productService;
    private final ObjectMapper objectMapper;

    public ProductEventHandler(ProductService productService, ObjectMapper objectMapper) {
        this.productService = productService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "product-event", groupId = "my-group")
    public void handleProductCreatedEvent(String kafkaMessage) {
        try {
            ProductCreatedEvent event = objectMapper.readValue(kafkaMessage, ProductCreatedEvent.class);

            log.info("Received ProductCreatedEvent: productId={}, name={}", event.getProductId(), event.getName());

            productService.handleProductCreatedEvent(event);

            log.info("Updated read model for Product ID {}: {}", event.getProductId(), event.getName());
        } catch (JsonProcessingException e) {
            log.error("Error deserializing Kafka message", e);
        } catch (Exception e) {
            log.error("Error handling ProductCreatedEvent", e);
        }
    }
}
