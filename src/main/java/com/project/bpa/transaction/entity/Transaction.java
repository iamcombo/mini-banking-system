package com.project.bpa.transaction.entity;

import com.project.bpa.authentication.user.entity.User;
import com.project.bpa.common.entity.BaseEntity;
import com.project.bpa.common.enums.CurrencyEnum;
import com.project.bpa.transaction.enums.TransactionStatusEnum;
import com.project.bpa.transaction.enums.TransactionTypeEnum;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "transactions")
public class Transaction extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "from_account_number", nullable = false)
    private String fromAccountNumber;

    @Column(name = "to_account_number", nullable = false)
    private String toAccountNumber;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private CurrencyEnum currency;

    @Column
    private String remark;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "proceed_by_user_id", nullable = false)
    private User proceedBy;

    @Column(name = "trx_date", nullable = false)
    private LocalDateTime trxDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "trx_type", nullable = false)
    private TransactionTypeEnum trxType;

    @Enumerated(EnumType.STRING)
    @Column(name = "trx_status", nullable = false)
    private TransactionStatusEnum trxStatus;
}
