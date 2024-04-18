package com.project.homework.vo;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProductVO {

  private String name;

  private Integer amount;

  private Double price;

  private MultipartFile media;

  private String description;


}
