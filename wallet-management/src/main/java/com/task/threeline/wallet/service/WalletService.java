package com.task.threeline.wallet.service;

import com.task.threeline.wallet.dto.GeneralResponse;
import com.task.threeline.wallet.dto.WalletRequest;

public interface WalletService {
    GeneralResponse createWallet(Long userId);

    GeneralResponse getWalletBalance(Long userId);

    GeneralResponse credit(WalletRequest request);

    GeneralResponse debit(WalletRequest request);
}
