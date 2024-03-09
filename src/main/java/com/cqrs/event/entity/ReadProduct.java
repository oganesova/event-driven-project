package com.cqrs.event.entity;

import javax.persistence.*;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
@Table(name = "read_product")
public class ReadProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String name;
}