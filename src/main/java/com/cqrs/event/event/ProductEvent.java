package com.cqrs.event.event;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@AllArgsConstructor
public class ProductEvent{
    private Long productId;
    private String name;

}
