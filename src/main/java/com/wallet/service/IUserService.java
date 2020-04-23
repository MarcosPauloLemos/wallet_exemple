package com.wallet.service;

import com.wallet.model.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByEmail(String email);

    User save(User user);
}
