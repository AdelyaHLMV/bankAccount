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
@EqualsAndHashCode(callSuper=false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "BankAccount: Bankroll")
public class BankAccountBankrollDto {

    @NotNull(message = "Field 'accountNumber' must be not null")
    @ApiModelProperty(value = "Account number", name = "accountNumber", example = "1234567", required = true)
    BigInteger accountNumber;

    @NotNull(message = "Field 'bankroll' must be not null")
    @DecimalMin(value = "0.01", message = "Bankroll must be more than {value} or equals")
    @Digits(integer = 19, fraction = 2,   message = "Bankroll may have max fraction part = {fraction} and" +
            " max integer part = {integer} digits")
    @ApiModelProperty(value = "Bankroll", name = "bankroll", example = "1234567", required = true)
    BigDecimal bankroll;
}
