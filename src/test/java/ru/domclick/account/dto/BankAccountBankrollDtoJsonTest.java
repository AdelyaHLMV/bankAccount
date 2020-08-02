package ru.domclick.account.dto;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@JsonTest
public class BankAccountBankrollDtoJsonTest {

    @Autowired
    private JacksonTester<BankAccountBankrollDto> json;

    @Test
    void testDeserializeBankAccountBankrollDto() throws Exception {
        BankAccountBankrollDto dto = json.read("/json/bank-account-bankroll.json").getObject();
        assertEquals(BigInteger.valueOf(1L), dto.getAccountNumber());
        assertEquals(BigDecimal.valueOf(10L), dto.getBankroll());
    }
}
