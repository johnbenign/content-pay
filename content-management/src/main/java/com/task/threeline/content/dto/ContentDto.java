package com.task.threeline.content.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ContentDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal cost;
    private Long contentOwnerId;
}
