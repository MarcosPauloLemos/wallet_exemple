package com.wallet.service;

import com.wallet.model.User;
import com.wallet.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{

    @Autowired
    private UserRepository repository;

    @Override
    public Optional<User> findByEmail(String email) {
        return repository.findByEmailEquals(email);
    }

    @Override
    public User save(User user) {
        return repository.save(user);
    }
}
