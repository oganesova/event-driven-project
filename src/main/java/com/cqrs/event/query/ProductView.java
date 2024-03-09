package com.cqrs.event.query;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductView {
    private Long productId;
    private String name;
}
