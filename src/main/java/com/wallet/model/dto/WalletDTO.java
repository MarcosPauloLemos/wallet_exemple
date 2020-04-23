package com.wallet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletDTO {

    private Long id;

    @NotNull(message = "O nome não pode ser nulo")
    @Length(min = 3, message = "O nome deve conter no mínimo 3 caracteres")
    private String name;

    @NotNull(message = "Insira um valor para a carteira")
    private BigDecimal value;
}
