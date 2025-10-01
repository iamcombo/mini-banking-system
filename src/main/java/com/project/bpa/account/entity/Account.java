package com.project.bpa.account.entity;

import com.project.bpa.account.enums.AccountTypeEnum;
import com.project.bpa.authentication.user.entity.User;
import com.project.bpa.common.entity.BaseEntity;
import com.project.bpa.common.enums.CurrencyEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "account_holder_name")
    private String accountHolderName;

    @Column(name = "account_holder_email")
    private String accountHolderEmail;

    @Column(name = "account_holder_phone")
    private String accountHolderPhone;

    @Column(name = "national_id")
    private String nationalId;

    @Column(name = "account_number")
    private String accountNumber;

    @Column(name = "balance", nullable = false)
    private BigDecimal balance = BigDecimal.ZERO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CurrencyEnum currency;

    @Enumerated(EnumType.STRING)
    @Column(name = "account_type")
    private AccountTypeEnum accountType;

    // User (Many to One)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
