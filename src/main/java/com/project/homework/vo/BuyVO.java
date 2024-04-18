package com.project.homework.vo;

import lombok.Data;

import java.util.Map;

@Data
public class BuyVO {
    Map<Long, Integer> cart;
    String username;
}
