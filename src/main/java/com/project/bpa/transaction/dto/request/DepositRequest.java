package com.project.bpa.transaction.dto.request;

import com.project.bpa.common.enums.CurrencyEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositRequest {
    @NotBlank(message = "Account number is required")
    private String accountNumber;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotNull(message = "Currency is required")
    private CurrencyEnum currency;

    @Size(max = 255, message = "Remark cannot exceed 255 characters")
    private String remark;
}
