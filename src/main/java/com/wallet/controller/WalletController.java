package com.wallet.controller;

import com.wallet.model.Wallet;
import com.wallet.model.dto.WalletDTO;
import com.wallet.response.Response;
import com.wallet.service.IWalletService;
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
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private IWalletService walletService;

    @PostMapping
    public ResponseEntity create(@Valid  @RequestBody  WalletDTO walletDTO, BindingResult result){
        Response<WalletDTO> response = new Response<>();
        if(result.hasErrors()){
            result.getAllErrors().forEach(e -> response.getErrors().add(e.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        Wallet wallet = walletService.save(convertDtoToEntity(walletDTO));
        response.setData(convertEntityToDTO(wallet));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    private Wallet convertDtoToEntity(WalletDTO walletDTO){
        return Wallet.builder()
                .id(walletDTO.getId())
                .name(walletDTO.getName())
                .value(walletDTO.getValue())
                .build();
    }

    private WalletDTO convertEntityToDTO(Wallet wallet){
        return WalletDTO.builder()
                .id(wallet.getId())
                .name(wallet.getName())
                .value(wallet.getValue())
                .build();
    }
}
