package ru.domclick.account.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;
import ru.domclick.account.configuration.JpaConfiguration;
import ru.domclick.account.domain.bankaccount.BankAccount;
import ru.domclick.account.helper.TestDataCreatorHelper;

import java.time.ZonedDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ContextConfiguration(
        classes = {JpaConfiguration.class},
        loader = AnnotationConfigContextLoader.class)
@Transactional
@TestPropertySource(locations = "classpath:/application-test.properties")
public class BankAccountRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BankAccountRepository bankAccountRepository;

    @Test
    public void findByIdTest() {
        BankAccount bankAccount = TestDataCreatorHelper.createBankAccount();
        bankAccount.setAccountNumber(null);
        entityManager.persist(bankAccount);
        entityManager.flush();
        BankAccount founded = bankAccountRepository.findById(bankAccount.getAccountNumber()).get();
        assertThat(bankAccount.getAccountNumber().equals(founded.getAccountNumber()));
        assertThat(bankAccount.getVersion().equals(founded.getVersion()));
        assertThat(bankAccount.getStartDate().equals(founded.getStartDate()));
        assertThat(bankAccount.getBalance().equals(founded.getBalance()));
    }

    @Test
    public void findActiveBankAccountsTest() {
        ZonedDateTime now = ZonedDateTime.now();
        List<BankAccount> bankAccountList = bankAccountRepository.findAllByCloseDateAfterOrCloseDateIsNull(now);
        bankAccountList.forEach(bankAccount -> {
            assertThat(bankAccount.getCloseDate() == null || bankAccount.getCloseDate().compareTo(now) > 0);
        });
    }

}