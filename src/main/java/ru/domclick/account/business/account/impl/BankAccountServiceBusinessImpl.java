package ru.domclick.account.business.account.impl;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.domclick.account.business.account.BankAccountServiceBusiness;
import ru.domclick.account.common.enums.BankAccountExceptionEnum;
import ru.domclick.account.domain.bankaccount.BankAccount;
import ru.domclick.account.dto.BankAccountBankrollDto;
import ru.domclick.account.dto.BankAccountBankrollTransferDto;
import ru.domclick.account.exception.WebServiceException;
import ru.domclick.account.service.account.impl.BankAccountService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static ru.domclick.account.common.enums.BankAccountExceptionEnum.*;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class BankAccountServiceBusinessImpl implements BankAccountServiceBusiness {

    BankAccountService accountService;

    @Transactional
    public BankAccount toChargeAnAccount(
            BankAccountBankrollDto bankAccountBankrollDto
    ) {
        try {
            BankAccount bankAccount = accountService.findById(bankAccountBankrollDto.getAccountNumber());
            if (bankAccount.getBalance()
                    .subtract(bankAccountBankrollDto.getBankroll())
                    .signum() >= 0) {
                bankAccount.setBalance(bankAccount.getBalance().subtract(bankAccountBankrollDto.getBankroll()));
                return accountService.saveChanges(bankAccount);
            } else {
                throw OVERDRAFT_EXCEPTION.exception(bankAccountBankrollDto.getAccountNumber());
            }
        } catch (WebServiceException ex) {
            throw ex;
        } catch (EntityNotFoundException ex) {
            throw BANK_ACCOUNT_NOT_FOUND_EXCEPTION.exception(bankAccountBankrollDto.getAccountNumber(), ex);
        } catch (Exception ex) {
            throw DEFAULT_EXCEPTION.exception(ex);
        }
    }

    @Transactional
    public BankAccount toReplenishAccount(
            BankAccountBankrollDto bankAccountBankrollDto
    ) {
        try {
            BankAccount bankAccount = accountService.findById(bankAccountBankrollDto.getAccountNumber());
            bankAccount.setBalance(bankAccount.getBalance().add(bankAccountBankrollDto.getBankroll()));
            return accountService.saveChanges(bankAccount);
        } catch (EntityNotFoundException ex) {
            throw BANK_ACCOUNT_NOT_FOUND_EXCEPTION.exception(bankAccountBankrollDto.getAccountNumber(), ex);
        } catch (Exception ex) {
            throw DEFAULT_EXCEPTION.exception(ex);
        }
    }

    @Transactional
    public void transferBetweenAccounts(
            BankAccountBankrollTransferDto bankAccountBankrollTransferDto
    ) {
        if (bankAccountBankrollTransferDto.getSourceAccountNumber().equals(bankAccountBankrollTransferDto.getTargetAccountNumber())) {
            throw SAME_ACCOUNT_NUMBER_EXCEPTION.exception();
        }

        toChargeAnAccount(BankAccountBankrollDto
                .builder()
                .accountNumber(bankAccountBankrollTransferDto.getSourceAccountNumber())
                .bankroll(bankAccountBankrollTransferDto.getBankroll())
                .build());
        toReplenishAccount(BankAccountBankrollDto
                .builder()
                .accountNumber(bankAccountBankrollTransferDto.getTargetAccountNumber())
                .bankroll(bankAccountBankrollTransferDto.getBankroll())
                .build()
        );

    }

    @Override
    @Transactional
    public List<BankAccount> getActiveBankAccount() {
        return accountService.getActiveBankAccounts();
    }


}
