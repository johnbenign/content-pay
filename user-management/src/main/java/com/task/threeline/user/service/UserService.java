package com.task.threeline.user.service;

import com.task.threeline.user.dto.GeneralResponse;
import com.task.threeline.user.dto.UserRequest;
import com.task.threeline.user.exception.NotFoundException;

public interface UserService {
    GeneralResponse register(UserRequest request);
    GeneralResponse authenticate(String email, String password) throws NotFoundException;

    GeneralResponse findOne(Long userId);

    GeneralResponse findByEmail(String email);
}
