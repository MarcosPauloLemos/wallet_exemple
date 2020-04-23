package com.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WA01_WALLET")
public class Wallet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WA01_ID")
    private Long id;

    @NotNull
    @Column(name = "WA01_NAME")
    private String name;

    @NotNull
    @Column(name = "WA01_VALUE")
    private BigDecimal value;

    public Wallet(Long id) {
        this.id = id;
    }
}
