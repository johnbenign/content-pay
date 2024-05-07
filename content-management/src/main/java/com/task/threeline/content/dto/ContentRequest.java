package com.task.threeline.content.dto;

import com.task.threeline.content.constant.MessageConstant;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ContentRequest {
    @NotEmpty(message = MessageConstant.NAME_REQUIRED)
    private String name;

    private String description;

    @NotNull(message = MessageConstant.COST_REQUIRED)
    private BigDecimal cost;

    @NotNull(message = MessageConstant.CONTENT_OWNER_REQUIRED)
    private Long contentOwnerId;
}
