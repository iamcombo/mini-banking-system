package com.project.bpa.account.dto.request;

import com.project.bpa.account.enums.AccountTypeEnum;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateAccountRequest {
    @NotBlank(message = "Account holder name is required")
    @Size(min = 2, max = 100, message = "Account holder name must be between 2 and 100 characters")
    private String accountHolderName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100, message = "Email must not exceed 100 characters")
    private String accountHolderEmail;
    
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "Phone number must be 10-15 digits")
    private String accountHolderPhone;
    
    @NotNull(message = "Account type is required")
    private AccountTypeEnum accountType;
    
    @NotBlank(message = "National ID is required")
    @Size(min = 5, max = 20, message = "National ID must be between 5 and 20 characters")
    private String nationalId;
}
