package com.wallet.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WalletItemDTO {
    private Long id;
    @NotNull(message = "Insira o id da carteira")
    private Long walletId;
    @NotNull(message = "Informe uma data")
    private Date date;
    @NotNull(message = "Informe um tipo")
    private String type;
    @NotNull(message = "Informe uma descrição")
    @Length(min = 5, message = "A descrição deve ter no mínimo 5 caracteres")
    private String description;
    @NotNull(message = "Informe um valor")
    private BigDecimal value;
}
