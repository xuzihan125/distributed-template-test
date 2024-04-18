package com.project.homework.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Product {

  @Id
  private String name;

  private Integer num;
}
