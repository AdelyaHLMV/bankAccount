package ru.domclick.account.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.math.BigInteger;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "BankAccount: Transfer between bank accounts")
public class BankAccountBankrollTransferDto {

    @NotNull(message = "Field 'sourceAccountNumber' must be not null")
    @ApiModelProperty(value = "Source account number", name = "sourceAccountNumber", example = "1234567", required = true)
    BigInteger sourceAccountNumber;

    @NotNull(message = "Field 'targetAccountNumber' must be not null")
    @ApiModelProperty(value = "Target account number", name = "targetAccountNumber", example = "1234567", required = true)
    BigInteger targetAccountNumber;

    @NotNull(message = "Field 'bankroll' must be not null")
    @DecimalMin(value = "0.01", message = "Bankroll must be more than {value} or equals")
    @Digits(integer = 19, fraction = 2, message = "Bankroll may have max fraction part = {fraction} and" +
            " max integer part = {integer} digits")
    @ApiModelProperty(value = "Bankroll", name = "bankroll", example = "123.56", required = true)
    BigDecimal bankroll;
}
