package com.wallet.controller;

import com.wallet.model.User;
import com.wallet.model.UserWallet;
import com.wallet.model.Wallet;
import com.wallet.model.dto.UserWalletDTO;
import com.wallet.response.Response;
import com.wallet.service.UserWalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/user-wallet")
public class UserWalletController {

    @Autowired
    private UserWalletService userWalletService;

    @PostMapping
    public ResponseEntity create(@Valid @RequestBody UserWalletDTO userWalletDTO, BindingResult result){
        Response<UserWalletDTO> response = new Response<>();
        if(result.hasErrors()){
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        UserWallet userWallet = userWalletService.save(convertDtoToEntity(userWalletDTO));
        response.setData(convertEntityToDto(userWallet));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private UserWalletDTO convertEntityToDto(UserWallet userWallet) {
        return UserWalletDTO.builder()
                .id(userWallet.getId())
                .userId(userWallet.getUser().getId())
                .walletId(userWallet.getWallet().getId())
                .build();
    }

    private UserWallet convertDtoToEntity(UserWalletDTO userWalletDTO) {
        return UserWallet.builder()
                .id(userWalletDTO.getId())
                .user(new User(userWalletDTO.getUserId()))
                .wallet(new Wallet(userWalletDTO.getWalletId()))
                .build();
    }
}
