package ru.domclick.account.helper;

import ru.domclick.account.domain.bankaccount.BankAccount;
import ru.domclick.account.dto.BankAccountBankrollDto;
import ru.domclick.account.dto.BankAccountBankrollTransferDto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.ZonedDateTime;

public class TestDataCreatorHelper {

    public static BankAccount createBankAccount() {
        BigInteger accountNumber = BigInteger.valueOf(11);
        Long version = 0L;
        ZonedDateTime startDate = ZonedDateTime.now();
        BigDecimal balance = BigDecimal.valueOf(123.4);

        return BankAccount
                .builder()
                .accountNumber(accountNumber)
                .version(version)
                .startDate(startDate)
                .balance(balance)
                .build();
    }

    public static BankAccountBankrollDto createBankAccountBankrollDto(
            BigInteger accountNumber,
            BigDecimal bankroll
    ) {
        return BankAccountBankrollDto
                .builder()
                .accountNumber(accountNumber)
                .bankroll(bankroll)
                .build();
    }

    public static BankAccountBankrollDto createBankAccountBankrollDto() {
        return BankAccountBankrollDto
                .builder()
                .accountNumber(BigInteger.ONE)
                .bankroll(BigDecimal.valueOf(12))
                .build();
    }

    public static BankAccountBankrollTransferDto createBankAccountBankrollTransferDto() {
        return BankAccountBankrollTransferDto
                .builder()
                .sourceAccountNumber(BigInteger.ONE)
                .targetAccountNumber(BigInteger.valueOf(2))
                .bankroll(BigDecimal.valueOf(12))
                .build();

    }

    public static BankAccountBankrollTransferDto createBankAccountBankrollTransferDto(
            BigInteger sourceAccountNumber,
            BigInteger targetAccountNumber,
            BigDecimal bankroll
    ) {
        return BankAccountBankrollTransferDto
                .builder()
                .sourceAccountNumber(sourceAccountNumber)
                .targetAccountNumber(targetAccountNumber)
                .bankroll(bankroll)
                .build();

    }
}
