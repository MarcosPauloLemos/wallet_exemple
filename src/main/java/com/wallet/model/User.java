package com.wallet.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "US01_USER")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "US01_ID")
    private Long id;

    @Column(name = "US01_NAME", nullable = false)
    private String name;

    @Column(name = "US01_PASSWORD", nullable = false)
    private String password;

    @Column(name = "US01_EMAIL", nullable = false)
    private String email;

    public User(Long id) {
        this.id = id;
    }
}
