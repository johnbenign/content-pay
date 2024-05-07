package com.task.threeline.user;

import static org.mockito.ArgumentMatchers.*;

import com.google.gson.Gson;
import com.task.threeline.user.controller.UserController;
import com.task.threeline.user.dto.GeneralResponse;
import com.task.threeline.user.dto.UserRequest;
import com.task.threeline.user.service.UserService;
import java.lang.Exception;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

public final class TestUserControllerV {
  private UserController userController;

  private UserService userService;

  private Gson gson = new Gson();

  @BeforeEach
  public void setup() throws Exception {
    userService = Mockito.mock(UserService.class);
    userController = new UserController(userService);
  }

//  @Test
  public void testMethodRegister() throws Exception {
    // 
    GeneralResponse registerResult = gson.fromJson("", GeneralResponse.class);
    // GeneralResponse registerResult = userService.register(1 arguments);
    Mockito.when(userService.register(any(UserRequest.class))).thenReturn(registerResult);
    // 
    UserRequest user = gson.fromJson("{}", UserRequest.class);
    ResponseEntity<GeneralResponse> responseEntity = userController.register(user);
    ResponseEntity<GeneralResponse> responseEntityExpected = null;
    Assertions.assertEquals(responseEntityExpected, responseEntity);
  }
}
