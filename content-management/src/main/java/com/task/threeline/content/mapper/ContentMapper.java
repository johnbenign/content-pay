package com.task.threeline.content.mapper;

import com.task.threeline.content.dto.ContentDto;
import com.task.threeline.content.dto.ContentRequest;
import com.task.threeline.content.model.Content;

public class ContentMapper {
    private ContentMapper(){}

    public static Content mapToEntity(ContentRequest request) {
        Content content = new Content();
        content.setCost(request.getCost());
        content.setName(request.getName());
        content.setContentOwner(request.getContentOwnerId());
        content.setDescription(request.getDescription());
        return content;
    }

    public static ContentDto mapToDto(Content entity) {
        if(entity == null) return null;
        ContentDto dto = new ContentDto();
        dto.setCost(entity.getCost());
        dto.setDescription(entity.getDescription());
        dto.setName(entity.getName());
        dto.setId(entity.getId());
        dto.setContentOwnerId(entity.getContentOwner());
        return dto;
    }
}
