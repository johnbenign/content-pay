package com.task.threeline.user.event;

import com.task.threeline.user.constant.UserType;
import com.task.threeline.user.dao.UserRepository;
import com.task.threeline.user.dto.UserRequest;
import com.task.threeline.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class StartupEvent {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup(){
        log.info(" --- Server startup, populating db --- ");
        populateDbWithTestData();
    }

    private void populateDbWithTestData() {
        if(!userRepository.existsByEmail("3line.management@3lineng.com")) {
            UserRequest userRequest = create3LineUserRequest();
            userService.register(userRequest);
        }
        if(!userRepository.existsByEmail("clientorg@client.ng")) {
            UserRequest userRequest = createClientOrgUserRequest();
            userService.register(userRequest);
        }
    }

    private UserRequest createClientOrgUserRequest() {
        UserRequest request = new UserRequest();
        request.setEmail("clientorg@client.ng");
        String hashedPassword = BCrypt.hashpw("client", BCrypt.gensalt());
        request.setPassword(hashedPassword);
        request.setUserType(UserType.CLIENT_ORG.toString());
        request.setFirstName("Client");
        request.setLastName("Organization");
        return request;
    }

    private UserRequest create3LineUserRequest() {
        UserRequest request = new UserRequest();
        request.setEmail("3line.management@3lineng.com");
        String hashedPassword = BCrypt.hashpw("3line", BCrypt.gensalt());
        request.setPassword(hashedPassword);
        request.setUserType(UserType.CONTRACT_ORG.toString());
        request.setFirstName("3Line");
        request.setLastName("Card Management");
        return request;
    }
}
