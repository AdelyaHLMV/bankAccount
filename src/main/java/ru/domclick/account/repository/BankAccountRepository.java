package ru.domclick.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.domclick.account.domain.bankaccount.BankAccount;

import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, BigInteger> {

    List<BankAccount> findAllByCloseDateAfterOrCloseDateIsNull(ZonedDateTime closeDate);
}
