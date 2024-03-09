package com.cqrs.event.event;

import lombok.Getter;

@Getter
public class ProductCreatedEvent extends ProductEvent {
    public ProductCreatedEvent(Long productId, String name) {
        super(productId, name);
    }

}
