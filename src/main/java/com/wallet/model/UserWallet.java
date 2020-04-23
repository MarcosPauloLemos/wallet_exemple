package com.wallet.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "WM01B_USER_WALLET")
public class UserWallet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "WM01B_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WM01B_ID_USER", referencedColumnName = "US01_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "WM01B_ID_WALLET", referencedColumnName = "WA01_ID")
    private Wallet wallet;
}
