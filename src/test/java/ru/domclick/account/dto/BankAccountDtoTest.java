package ru.domclick.account.dto;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import ru.domclick.account.domain.bankaccount.BankAccount;
import ru.domclick.account.helper.TestDataCreatorHelper;
import ru.domclick.account.mapper.BankAccountDtoMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@JsonTest
public class BankAccountDtoTest {

    BankAccountDtoMapper bankAccountDtoMapper = BankAccountDtoMapper.INSTANCE;

    @Test
    public void testToDto() {
        BankAccount bankAccount = TestDataCreatorHelper.createBankAccount();
        BankAccountDto dto = bankAccountDtoMapper.toBankAccountDto(bankAccount);
        assertEquals(bankAccount.getAccountNumber(), dto.getAccountNumber());
        assertEquals(bankAccount.getVersion(), dto.getVersion());
        assertEquals(bankAccount.getStartDate(), dto.getStartDate());
        assertNull(dto.getCloseDate());
        assertEquals(bankAccount.getBalance(), dto.getBalance());
    }

}
