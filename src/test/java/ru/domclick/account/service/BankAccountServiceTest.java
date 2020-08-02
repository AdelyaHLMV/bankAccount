package ru.domclick.account.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.domclick.account.domain.bankaccount.BankAccount;
import ru.domclick.account.helper.TestDataCreatorHelper;
import ru.domclick.account.repository.BankAccountRepository;
import ru.domclick.account.service.account.BankAccountServiceImpl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
public class BankAccountServiceTest {

    @Mock
    private BankAccountRepository bankAccountRepository;

    @InjectMocks
    private BankAccountServiceImpl bankAccountService;

    @Test
    public void testGetById() {
        BankAccount bankAccount = TestDataCreatorHelper.createBankAccount();
        given(this.bankAccountRepository.findById(any()))
                .willReturn(Optional.of(bankAccount));
        BankAccount currentBankAccount = bankAccountService.findById(bankAccount.getAccountNumber());
        assertThat(currentBankAccount.getAccountNumber()).isEqualTo(bankAccount.getAccountNumber());
        assertThat(currentBankAccount.getStartDate()).isEqualTo(bankAccount.getStartDate());
        assertThat(currentBankAccount.getBalance()).isEqualTo(bankAccount.getBalance());

    }

    @Test
    public void testSave() {
        BankAccount bankAccount = TestDataCreatorHelper.createBankAccount();
        given(this.bankAccountRepository.save(any()))
                .willReturn(bankAccount);
        BankAccount savedBankAccount = bankAccountService.saveChanges(bankAccount);
        assertThat(savedBankAccount.getAccountNumber()).isEqualTo(bankAccount.getAccountNumber());
        assertThat(savedBankAccount.getStartDate()).isEqualTo(bankAccount.getStartDate());
        assertThat(savedBankAccount.getBalance()).isEqualTo(bankAccount.getBalance());
    }


    @Test
    public void testGetActiveBankAccount() {
        BankAccount bankAccount = TestDataCreatorHelper.createBankAccount();
        List<BankAccount> bankAccounts = Collections.singletonList(
                bankAccount
        );
        given(this.bankAccountRepository.findAllByCloseDateAfterOrCloseDateIsNull(any()))
                .willReturn(bankAccounts);
        List<BankAccount> activeBankAccounts = bankAccountService.getActiveBankAccounts();
        assertThat(activeBankAccounts.size()).isEqualTo(1);
        BankAccount activeBankAccount = activeBankAccounts.get(0);
        assertThat(activeBankAccount.getAccountNumber()).isEqualTo(bankAccount.getAccountNumber());
        assertThat(activeBankAccount.getStartDate()).isEqualTo(bankAccount.getStartDate());
        assertThat(activeBankAccount.getBalance()).isEqualTo(bankAccount.getBalance());

    }

}
