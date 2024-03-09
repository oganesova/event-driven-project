package com.cqrs.event.entity;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "write_product")
public class WriteProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
}
