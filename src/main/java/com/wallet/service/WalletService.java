package com.wallet.service;

import com.wallet.model.Wallet;
import com.wallet.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WalletService implements IWalletService {

    @Autowired
    private WalletRepository repository;

    @Override
    public Wallet save(Wallet wallet){
        return  repository.save(wallet);
    }
}
