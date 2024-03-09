package com.cqrs.event.command;

import lombok.Data;

@Data
public class CreateProductCommand {
    private Long productId;
    private String name;

}
