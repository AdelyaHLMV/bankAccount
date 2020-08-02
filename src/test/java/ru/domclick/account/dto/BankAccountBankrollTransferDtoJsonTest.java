package ru.domclick.account.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class BankAccountBankrollTransferDtoJsonTest {

    @Autowired
    private JacksonTester<BankAccountBankrollTransferDto> json;

    @Test
    void testDeserializeBankAccountBankrollDto() throws Exception {
        BankAccountBankrollTransferDto dto = json.read("/json/bank-account-bankroll-transfer.json").getObject();
        assertEquals(BigInteger.valueOf(1), dto.getSourceAccountNumber());
        assertEquals(BigInteger.valueOf(2), dto.getTargetAccountNumber());
        assertEquals(BigDecimal.valueOf(10), dto.getBankroll());
    }
}
