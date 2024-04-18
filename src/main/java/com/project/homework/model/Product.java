package com.project.homework.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private Integer amount;

  private Double price;

  private String media;

  private String description;


}
