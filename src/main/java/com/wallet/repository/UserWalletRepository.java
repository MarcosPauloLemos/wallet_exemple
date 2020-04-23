package com.wallet.repository;

import com.wallet.model.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWalletRepository extends JpaRepository<UserWallet, Long> {

}
