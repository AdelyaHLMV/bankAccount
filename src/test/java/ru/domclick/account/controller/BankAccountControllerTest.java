package ru.domclick.account.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.domclick.account.business.account.BankAccountServiceBusiness;
import ru.domclick.account.controller.bankaccount.BankAccountController;
import ru.domclick.account.domain.bankaccount.BankAccount;
import ru.domclick.account.helper.TestDataCreatorHelper;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BankAccountController.class)
public class BankAccountControllerTest {

    @MockBean
    private BankAccountServiceBusiness bankAccountServiceBusiness;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void replenishAnAccountTest() throws Exception {
        BankAccount bankAccount = TestDataCreatorHelper.createBankAccount();
        given(bankAccountServiceBusiness.toReplenishAccount(any())).willReturn(bankAccount);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/account/replenish")
                .content(asJsonString(TestDataCreatorHelper.createBankAccountBankrollDto()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.version").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").exists());
    }

    @Test
    public void chargeAnAccountTest() throws Exception {
        BankAccount bankAccount = TestDataCreatorHelper.createBankAccount();
        given(bankAccountServiceBusiness.toChargeAnAccount(any())).willReturn(bankAccount);
        mockMvc.perform(MockMvcRequestBuilders
                .post("/account/charge")
                .content(asJsonString(TestDataCreatorHelper.createBankAccountBankrollDto()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.accountNumber").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.version").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.startDate").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.balance").exists());
    }

    @Test
    public void transferBetweenAccountsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .post("/account/transfer")
                .content(asJsonString(TestDataCreatorHelper.createBankAccountBankrollTransferDto()))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void getActiveBankAccountsTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                .get("/account")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
