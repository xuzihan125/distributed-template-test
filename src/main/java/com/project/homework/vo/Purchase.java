package com.project.homework.vo;

import lombok.Data;

import java.util.List;

@Data
public class Purchase {
    String id;
    Double cost;
    String status;
    List<HistoryVO> detail;
}
