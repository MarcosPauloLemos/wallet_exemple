package com.wallet.service;

import com.wallet.model.UserWallet;
import com.wallet.repository.UserWalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWalletService implements IUserWalletService {

    @Autowired
    private UserWalletRepository repository;

    @Override
    public UserWallet save(UserWallet userWallet) {
        return repository.save(userWallet);
    }
}
