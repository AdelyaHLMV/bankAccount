package ru.domclick.account.business.account;

import ru.domclick.account.domain.bankaccount.BankAccount;
import ru.domclick.account.dto.BankAccountBankrollDto;
import ru.domclick.account.dto.BankAccountBankrollTransferDto;

import java.util.List;

public interface BankAccountServiceBusiness {

    BankAccount toChargeAnAccount(
            BankAccountBankrollDto bankAccountBankrollDto
    );

    BankAccount toReplenishAccount(
            BankAccountBankrollDto bankAccountBankrollDto
    );

    void transferBetweenAccounts(
            BankAccountBankrollTransferDto bankAccountBankrollTransferDto
    );

    List<BankAccount> getActiveBankAccount();

}
