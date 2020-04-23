package com.wallet.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {

    private Long id;

    @Email(message = "Email inválido!")
    private String email;

    @Length(min = 3, max = 50, message = "O nome deve conter entre 3 e 50 caracteres!")
    private String name;

    @NotNull
    @Length(min = 6, message = "A Senha deve conter no minimo 6 caracteres!")
    private String password;
}
