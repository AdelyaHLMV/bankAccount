package ru.domclick.account.service.account;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.domclick.account.domain.bankaccount.BankAccount;
import ru.domclick.account.repository.BankAccountRepository;
import ru.domclick.account.service.account.impl.BankAccountService;

import javax.persistence.EntityNotFoundException;
import java.math.BigInteger;
import java.time.ZonedDateTime;
import java.util.List;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
@Slf4j
public class BankAccountServiceImpl implements BankAccountService {

    BankAccountRepository bankAccountRepository;

    @Override
    public BankAccount findById(BigInteger id) {
        return bankAccountRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public BankAccount saveChanges(BankAccount bankAccount) {
        return bankAccountRepository.save(bankAccount);
    }

    @Override
    public List<BankAccount> getActiveBankAccounts() {
        return bankAccountRepository.findAllByCloseDateAfterOrCloseDateIsNull(ZonedDateTime.now());
    }

}
