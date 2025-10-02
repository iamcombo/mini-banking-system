package com.project.bpa.account.dto.response;

import com.project.bpa.account.enums.AccountTypeEnum;
import com.project.bpa.common.enums.CurrencyEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private Long id;
    private String accountHolderName;
    private String accountHolderEmail;
    private String accountHolderPhone;
    private String nationalId;
    private String accountNumber;
    private BigDecimal balance;
    private CurrencyEnum currency;
    private AccountTypeEnum accountType;
}
