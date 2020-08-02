package ru.domclick.account.business;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.domclick.account.business.account.impl.BankAccountServiceBusinessImpl;
import ru.domclick.account.common.enums.BankAccountExceptionEnum;
import ru.domclick.account.domain.bankaccount.BankAccount;
import ru.domclick.account.dto.BankAccountBankrollDto;
import ru.domclick.account.dto.BankAccountBankrollTransferDto;
import ru.domclick.account.exception.WebServiceException;
import ru.domclick.account.helper.TestDataCreatorHelper;
import ru.domclick.account.service.account.impl.BankAccountService;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class BankAccountBusinessServiceTest {

    @Mock
    private BankAccountService bankAccountService;

    @InjectMocks
    private BankAccountServiceBusinessImpl bankAccountServiceBusiness;

    @Test
    public void testToChargeAnAccount() {
        BankAccount bankAccount = TestDataCreatorHelper.createBankAccount();
        BigDecimal bankAccountInitialBalance = bankAccount.getBalance();
        given(this.bankAccountService.findById(any()))
                .willReturn(bankAccount);
        given(this.bankAccountService.saveChanges(any()))
                .willReturn(bankAccount);
        BankAccountBankrollDto bankAccountBankrollDto = TestDataCreatorHelper.createBankAccountBankrollDto();
        BankAccount chargedBankAccount = bankAccountServiceBusiness.toChargeAnAccount(bankAccountBankrollDto);


        assertThat(chargedBankAccount.getBalance()).isEqualTo(bankAccountInitialBalance.subtract(bankAccountBankrollDto.getBankroll()));

        WebServiceException overdraftException = assertThrows(WebServiceException.class,
                () -> {
                    bankAccountServiceBusiness.toChargeAnAccount(TestDataCreatorHelper.createBankAccountBankrollDto(
                            BigInteger.valueOf(11), BigDecimal.valueOf(1278)));
                });
        assertThat(overdraftException.getCode()).isEqualTo(BankAccountExceptionEnum.OVERDRAFT_EXCEPTION.getCode());

        given(this.bankAccountService.findById(any()))
                .willThrow(new EntityNotFoundException());

        WebServiceException notFoundException = assertThrows(WebServiceException.class,
                () -> {
                    bankAccountServiceBusiness.toChargeAnAccount(TestDataCreatorHelper.createBankAccountBankrollDto(
                            BigInteger.valueOf(123), BigDecimal.valueOf(1278)));

                });
        assertThat(notFoundException.getCode()).isEqualTo(BankAccountExceptionEnum.BANK_ACCOUNT_NOT_FOUND_EXCEPTION.getCode());

    }

    @Test
    public void testToReplenishAccount() {
        BankAccount bankAccount = TestDataCreatorHelper.createBankAccount();
        BigDecimal bankAccountInitialBalance = bankAccount.getBalance();
        given(this.bankAccountService.findById(any()))
                .willReturn(bankAccount);
        given(this.bankAccountService.saveChanges(any()))
                .willReturn(bankAccount);

        BankAccountBankrollDto bankAccountBankrollDto = TestDataCreatorHelper.createBankAccountBankrollDto();
        BankAccount replenishedBankAccount = bankAccountServiceBusiness.toReplenishAccount(bankAccountBankrollDto);

        assertThat(bankAccount.getAccountNumber()).isEqualTo(replenishedBankAccount.getAccountNumber());
        assertThat(bankAccountInitialBalance.add(bankAccountBankrollDto.getBankroll())).isEqualTo(replenishedBankAccount.getBalance());


        given(this.bankAccountService.findById(any()))
                .willThrow(new EntityNotFoundException());
        WebServiceException notFoundException = assertThrows(WebServiceException.class,
                () -> {
                    bankAccountServiceBusiness.toReplenishAccount(TestDataCreatorHelper.createBankAccountBankrollDto(
                            BigInteger.valueOf(123), BigDecimal.valueOf(1278)));
                });
        assertThat(notFoundException.getCode()).isEqualTo(BankAccountExceptionEnum.BANK_ACCOUNT_NOT_FOUND_EXCEPTION.getCode());
    }

    @Test
    public void testTransferBetweenAccounts() {
        BankAccount bankAccountSource = TestDataCreatorHelper.createBankAccount();
        BigDecimal bankAccountSourceBalance = bankAccountSource.getBalance();

        given(this.bankAccountService.findById(BigInteger.ONE))
                .willReturn(bankAccountSource);
        given(this.bankAccountService.saveChanges(bankAccountSource))
                .willReturn(bankAccountSource);

        BankAccount bankAccountTarget = TestDataCreatorHelper.createBankAccount();
        BigDecimal bankAccountTargetBalance = bankAccountSource.getBalance();
        bankAccountTarget.setAccountNumber(BigInteger.valueOf(2));
        given(this.bankAccountService.findById(BigInteger.valueOf(2)))
                .willReturn(bankAccountTarget);
        given(this.bankAccountService.saveChanges(bankAccountTarget))
                .willReturn(bankAccountTarget);

        BankAccountBankrollTransferDto bankAccountBankrollTransferDto = TestDataCreatorHelper.createBankAccountBankrollTransferDto();
        bankAccountServiceBusiness.transferBetweenAccounts(bankAccountBankrollTransferDto);

        assertThat(bankAccountSource.getBalance()
                .add(bankAccountBankrollTransferDto.getBankroll())).isEqualTo(bankAccountSourceBalance);
        assertThat(bankAccountTarget.getBalance().subtract(bankAccountBankrollTransferDto.getBankroll())).isEqualTo(bankAccountTargetBalance);

        WebServiceException sameAccountNumberException = assertThrows(WebServiceException.class,
                () -> {
                    bankAccountServiceBusiness.transferBetweenAccounts(TestDataCreatorHelper.createBankAccountBankrollTransferDto(
                            BigInteger.ONE,
                            BigInteger.ONE,
                            BigDecimal.valueOf(12)));
                });
        assertThat(sameAccountNumberException.getCode()).isEqualTo(BankAccountExceptionEnum.SAME_ACCOUNT_NUMBER_EXCEPTION.getCode());
    }

    @Test
    public void testGetActiveBankAccount() {
        BankAccount bankAccount = TestDataCreatorHelper.createBankAccount();
        List<BankAccount> bankAccounts = Collections.singletonList(bankAccount);
        given(this.bankAccountService.getActiveBankAccounts())
                .willReturn(bankAccounts);
        List<BankAccount> activeBankAccount = bankAccountServiceBusiness.getActiveBankAccount();
        assertThat(activeBankAccount.size()).isEqualTo(1);

    }


}
