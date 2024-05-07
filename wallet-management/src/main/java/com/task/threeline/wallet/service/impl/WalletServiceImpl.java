package com.task.threeline.wallet.service.impl;

import com.task.threeline.wallet.dao.WalletRepository;
import com.task.threeline.wallet.dto.GeneralResponse;
import com.task.threeline.wallet.dto.WalletRequest;
import com.task.threeline.wallet.exception.InsufficientFundsException;
import com.task.threeline.wallet.exception.NotFoundException;
import com.task.threeline.wallet.model.Wallet;
import com.task.threeline.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class WalletServiceImpl implements WalletService {

    private final WalletRepository repository;

    @Override
    public GeneralResponse createWallet(Long userId){
        Wallet wallet = new Wallet();
        wallet.setAmount(BigDecimal.ZERO);
        wallet.setUserId(userId);
        repository.save(wallet);
        return GeneralResponse.builder()
                .message("Wallet created successfully")
                .status(HttpStatus.CREATED.value())
                .data("Wallet created successfully")
                .build();
    }

    @Override
    public GeneralResponse getWalletBalance(Long userId){
        Wallet wallet = repository.findByUserId(userId).orElseThrow(() -> new NotFoundException("User not found with given id"));
        return GeneralResponse.builder()
                .data(wallet.getAmount())
                .status(HttpStatus.OK.value())
                .message("Successful")
                .build();
    }

    @Override
    public GeneralResponse credit(WalletRequest request){
        Wallet wallet = repository.findByUserId(request.getUserId()).orElseThrow(() -> new NotFoundException("User not found with given id"));
        wallet.setAmount(wallet.getAmount().add(request.getAmount()));
        repository.save(wallet);
        return GeneralResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Successful")
                .message("Wallet credited successfully")
                .build();
    }

    @Override
    public GeneralResponse debit(WalletRequest request){
        Wallet wallet = repository.findByUserId(request.getUserId()).orElseThrow(() -> new NotFoundException("User not found with given id"));
        if(wallet.getAmount().doubleValue() < request.getAmount().doubleValue())
            throw new InsufficientFundsException();
        wallet.setAmount(wallet.getAmount().subtract(request.getAmount()));
        repository.save(wallet);
        return GeneralResponse.builder()
                .status(HttpStatus.OK.value())
                .data("Successful")
                .message("Wallet debited successfully")
                .build();
    }
}
