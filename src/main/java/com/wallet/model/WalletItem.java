package com.wallet.model;

import com.wallet.model.enums.TypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WA01C_WALLET_ITEM")
public class WalletItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WA01C_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WA01C_ID_WALLET", referencedColumnName = "WA01_ID")
    private Wallet wallet;

    @NotNull
    @Column(name = "WA01C_DATE")
    private Date date;

    @NotNull
    @Column(name = "WA01C_TYPE")
    @Enumerated(EnumType.STRING)
    private TypeEnum type;

    @NotNull
    @Column(name = "WA01C_DESCRIPTION")
    private String description;

    @NotNull
    @Column(name = "WA01C_VALUE")
    private BigDecimal value;
}
