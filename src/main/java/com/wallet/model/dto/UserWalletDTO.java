package com.wallet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWalletDTO {
    private Long id;
    @NotNull(message = "Informe o ID do Usuário")
    private Long userId;
    @NotNull(message = "Informe o ID da Carteira")
    private Long walletId;
}
