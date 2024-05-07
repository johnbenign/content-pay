package com.task.threeline.wallet;

import static org.mockito.ArgumentMatchers.*;

import com.google.gson.Gson;
import com.task.threeline.wallet.controller.WalletController;
import com.task.threeline.wallet.dto.GeneralResponse;
import com.task.threeline.wallet.dto.WalletRequest;
import com.task.threeline.wallet.service.WalletService;
import java.lang.Exception;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

public final class TestWalletControllerV {
  private WalletController walletController;

  private WalletService service;

  private Gson gson = new Gson();

  @BeforeEach
  public void setup() throws Exception {
    service = Mockito.mock(WalletService.class);
    walletController = new WalletController(service);
  }

//  @Test
  public void testMethodCreate() throws Exception {
    // 
    GeneralResponse createWalletResult = gson.fromJson("", GeneralResponse.class);
    // GeneralResponse createWalletResult = service.createWallet(1 arguments);
    Mockito.when(service.createWallet(eq(0L))).thenReturn(createWalletResult);
    //  
    ResponseEntity<GeneralResponse> responseEntity = walletController.create(0L);
    ResponseEntity<GeneralResponse> responseEntityExpected = null;
    Assertions.assertEquals(responseEntityExpected, responseEntity);
  }

//  @Test
  public void testMethodDebit() throws Exception {
    //
    GeneralResponse debitResult = gson.fromJson("", GeneralResponse.class);
    // GeneralResponse debitResult = service.debit(1 arguments);
    Mockito.when(service.debit(any(WalletRequest.class))).thenReturn(debitResult);
    //
    WalletRequest request1 = gson.fromJson("{}", WalletRequest.class);
    ResponseEntity<GeneralResponse> responseEntity = walletController.debit(request1);
    ResponseEntity<GeneralResponse> responseEntityExpected = null;
    Assertions.assertEquals(responseEntityExpected, responseEntity);
  }
//  @Test
  public void testMethodCredit() throws Exception {
    //
    GeneralResponse creditResult = gson.fromJson("", GeneralResponse.class);
    // GeneralResponse creditResult = service.credit(1 arguments);
    Mockito.when(service.credit(any(WalletRequest.class))).thenReturn(creditResult);
    //
    WalletRequest request1 = gson.fromJson("{}", WalletRequest.class);
    ResponseEntity<GeneralResponse> responseEntity = walletController.credit(request1);
    ResponseEntity<GeneralResponse> responseEntityExpected = null;
    Assertions.assertEquals(responseEntityExpected, responseEntity);
  }
}
