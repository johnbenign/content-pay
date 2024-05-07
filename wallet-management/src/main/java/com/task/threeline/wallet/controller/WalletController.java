package com.task.threeline.wallet.controller;

import com.task.threeline.wallet.constant.ApiRoute;
import com.task.threeline.wallet.dto.GeneralResponse;
import com.task.threeline.wallet.dto.WalletRequest;
import com.task.threeline.wallet.service.WalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping(ApiRoute.WALLET_API)
public class WalletController {

    private final WalletService service;

    @PostMapping(ApiRoute.CREATE + "/{user-id}")
    public ResponseEntity<GeneralResponse> create(@PathVariable("user-id") Long userId) {
        GeneralResponse response = service.createWallet(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(ApiRoute.DEBIT)
    public ResponseEntity<GeneralResponse> debit(@RequestBody @Valid WalletRequest request) {
        GeneralResponse response = service.debit(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PostMapping(ApiRoute.CREDIT)
    public ResponseEntity<GeneralResponse> credit(@RequestBody @Valid WalletRequest request) {
        GeneralResponse response = service.credit(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(value = ApiRoute.GET_BALANCE + "/{user-id}")
    public ResponseEntity<GeneralResponse> getWalletBalance(@PathVariable("user-id") Long userId){
        GeneralResponse response = service.getWalletBalance(userId);
        return ResponseEntity.ok(response);
    }
}
