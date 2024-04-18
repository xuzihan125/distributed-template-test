package com.project.homework.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class History {
    @Id
    String id;

    String username;

    String status;

    Integer quantity;

    Long productId;
}
