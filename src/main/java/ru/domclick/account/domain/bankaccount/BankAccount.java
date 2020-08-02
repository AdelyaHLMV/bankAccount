package ru.domclick.account.domain.bankaccount;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZonedDateTime;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper=false)
@Entity(name = "BankAccount")
@Table(name = "BANK_ACCOUNT")
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class BankAccount {

    @Id
    @Column(name = "ACCOUNT_NUMBER")
    @SequenceGenerator(name = "bank_account_id_sequence", sequenceName = "bank_account_id_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "bank_account_id_sequence")
    BigInteger accountNumber;

    @Version
    @Column(name = "VERSION")
    Long version;

    @Column(name = "START_DATE")
    ZonedDateTime startDate;

    @Column(name = "CLOSE_DATE")
    ZonedDateTime closeDate;

    @Column(name = "BALANCE", scale = 2)
    BigDecimal balance;

}
