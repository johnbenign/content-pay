package com.task.threeline.user.mapper;

import com.task.threeline.user.constant.UserType;
import com.task.threeline.user.dto.UserDto;
import com.task.threeline.user.dto.UserRequest;
import com.task.threeline.user.model.User;

public class UserMapper {
    private UserMapper(){}

    public static User mapToEntity(UserRequest dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setUserType(UserType.valueOf(dto.getUserType()));
        return user;
    }

    public static UserDto mapToDto(User entity) {
        UserDto dto = new UserDto();
        dto.setFirstName(entity.getFirstName());
        dto.setLastName(entity.getLastName());
        dto.setEmail(entity.getEmail());
        dto.setUserType(entity.getUserType());
        dto.setId(entity.getId());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setCreatedAt(entity.getCreatedAt());
        return dto;
    }
}
