package com.project.homework.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class History {
    @Id
    String id;

    String username;

    String status;

    Integer quantity;

    Long productId;

    Date purchaseTime;
}
