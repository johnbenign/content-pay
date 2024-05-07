package com.task.threeline.user.service.impl;

import com.task.threeline.user.dao.UserRepository;
import com.task.threeline.user.dto.GeneralResponse;
import com.task.threeline.user.dto.UserRequest;
import com.task.threeline.user.exception.BadCredentialException;
import com.task.threeline.user.exception.BadRequestException;
import com.task.threeline.user.exception.NotFoundException;
import com.task.threeline.user.mapper.UserMapper;
import com.task.threeline.user.model.User;
import com.task.threeline.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final WebClient webClient;

    @Override
    public GeneralResponse register(UserRequest request) {
        if(userRepository.existsByEmail(request.getEmail()))
            throw new BadRequestException("User already exists");
        User user = UserMapper.mapToEntity(request);
        String hashedPassword = BCrypt.hashpw(request.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);
        user = userRepository.save(user);
        GeneralResponse createWalletResponse = createWallet(user.getId());
        if(createWalletResponse.getStatus() != HttpStatus.CREATED.value())
            return createWalletResponse;
        return GeneralResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message("User created successfully")
                .data(UserMapper.mapToDto(user))
                .build();
    }

    private GeneralResponse createWallet(Long userId) {
        return webClient.post()
                .uri("http://132.145.58.252:6062/wallet-api/create", uriBuilder -> uriBuilder.path("/" + userId).build())
                .retrieve()
                .bodyToMono(GeneralResponse.class)
                .block();
    }

    @Override
    public GeneralResponse authenticate(String email, String password) throws NotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User details not found"));
        if (!BCrypt.checkpw(password, user.getPassword()))
            throw new BadCredentialException("Invalid password");
        return GeneralResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Authentication successful")
                .data(UserMapper.mapToDto(user))
                .build();    }

    @Override
    public GeneralResponse findOne(Long userId){
        User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with given id"));
        return GeneralResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successful")
                .data(UserMapper.mapToDto(user))
                .build();
    }

    @Override
    public GeneralResponse findByEmail(String email){
        User user = userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found with given email"));
        return GeneralResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successful")
                .data(UserMapper.mapToDto(user))
                .build();
    }
}