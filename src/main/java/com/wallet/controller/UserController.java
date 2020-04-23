package com.wallet.controller;

import com.wallet.model.User;
import com.wallet.model.dto.UserDTO;
import com.wallet.response.Response;
import com.wallet.service.UserService;
import com.wallet.util.Bcrypt;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping
    public ResponseEntity <Response<UserDTO>> create(@Valid @RequestBody UserDTO userDTO, BindingResult result){
        Response<UserDTO> responserUser = new Response<>();
        if(result.hasErrors()){
            result.getAllErrors().forEach(e -> responserUser.getErrors().add(e.getDefaultMessage()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responserUser);
        }

        User user = service.save(convertDtoToEntity(userDTO));
        responserUser.setData(convertEntityToDto(user));
        return ResponseEntity.status(HttpStatus.CREATED).body(responserUser);
    }

    private User convertDtoToEntity(UserDTO userDTO){
        return User.builder()
                .name(userDTO.getName())
                .email(userDTO.getEmail())
                .password(Bcrypt.getHash(userDTO.getPassword()))
                .build();
    }

    private UserDTO convertEntityToDto(User user){
        return UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}
