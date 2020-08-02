package ru.domclick.account.service.account.impl;

import ru.domclick.account.domain.bankaccount.BankAccount;

import java.math.BigInteger;
import java.util.List;

public interface BankAccountService {

    BankAccount findById(BigInteger id);

    BankAccount saveChanges(BankAccount bankAccount);

    List<BankAccount> getActiveBankAccounts();
}
