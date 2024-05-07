package com.task.threeline.content.dto;

import lombok.Data;

@Data
public class BuyContentRequest {
    private Long buyerId;
    private Long contentId;
}
