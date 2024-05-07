package com.task.threeline.user.dto;

import com.task.threeline.user.constant.MessageConstant;
import com.task.threeline.user.constant.UserType;
import com.task.threeline.user.util.ValueOfEnum;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class UserRequest {
    @NotEmpty(message = MessageConstant.FIRST_NAME_REQUIRED)
    private String firstName;

    @NotEmpty(message = MessageConstant.LAST_NAME_REQUIRED)
    private String lastName;

    @NotEmpty(message = MessageConstant.USER_EMAIL_REQUIRED)
    @Email(message = MessageConstant.INVALID_EMAIL)
    private String email;

    @NotEmpty(message = MessageConstant.PASSWORD_REQUIRED)
    private String password;

    @NotNull(message = MessageConstant.USER_TYPE_REQUIRED)
    @ValueOfEnum(enumClass = UserType.class, message = MessageConstant.INVALID_USER_TYPE)
    private String userType;
}
