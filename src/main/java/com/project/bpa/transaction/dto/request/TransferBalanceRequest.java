package com.project.bpa.transaction.dto.request;

import com.project.bpa.common.enums.CurrencyEnum;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransferBalanceRequest {
    @NotBlank(message = "Source account number is required")
    @Size(min = 10, max = 20, message = "Account number must be between 10 and 20 characters")
    private String fromAccountNumber;

    @NotBlank(message = "Destination account number is required")
    @Size(min = 10, max = 20, message = "Account number must be between 10 and 20 characters")
    private String toAccountNumber;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;

    @NotBlank(message = "Currency is required")
    private CurrencyEnum currency;

    @Size(max = 255, message = "Remark cannot exceed 255 characters")
    private String remark;

    @NotNull(message = "Request date is required")
    private LocalDateTime requestDate;
}
