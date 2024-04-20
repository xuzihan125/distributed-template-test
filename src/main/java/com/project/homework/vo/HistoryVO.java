package com.project.homework.vo;

import lombok.Data;

import java.util.Date;

@Data
public class HistoryVO {

    String name;

    String status;

    Integer quantity;

    Double price;

    Date date;
}
