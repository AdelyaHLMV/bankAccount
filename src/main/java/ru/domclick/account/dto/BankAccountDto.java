package ru.domclick.account.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZonedDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper=false)
@NoArgsConstructor
public class BankAccountDto {

    BigInteger accountNumber;
    Long version;
    ZonedDateTime startDate;
    ZonedDateTime closeDate;
    BigDecimal balance;
}
