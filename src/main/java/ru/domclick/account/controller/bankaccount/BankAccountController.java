package ru.domclick.account.controller.bankaccount;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.domclick.account.business.account.BankAccountServiceBusiness;
import ru.domclick.account.dto.BankAccountBankrollDto;
import ru.domclick.account.dto.BankAccountBankrollTransferDto;
import ru.domclick.account.dto.BankAccountDto;
import ru.domclick.account.mapper.BankAccountDtoMapper;

import java.util.List;
import java.util.stream.Collectors;

import static ru.domclick.account.common.enums.BankAccountExceptionEnum.CONCURRENT_MODIFICATION;

@RestController
@RequestMapping(path = "/account")
@Api(value = "/account", tags = {"ACCOUNT"})
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BankAccountController {

    BankAccountServiceBusiness bankAccountServiceBusiness;
    BankAccountDtoMapper bankAccountDtoMapper = BankAccountDtoMapper.INSTANCE;

    @ApiOperation(value = "Method allows replenish an account")
    @PostMapping(path = "/replenish")
    public BankAccountDto replenishAnAccount(
            @RequestBody @Validated BankAccountBankrollDto bankAccountBankrollDto
    ) {
        try {
            return bankAccountDtoMapper.toBankAccountDto(
                    bankAccountServiceBusiness.toReplenishAccount(bankAccountBankrollDto)
            );
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw CONCURRENT_MODIFICATION.exception(ex);
        }
    }

    @ApiOperation(value = "Method allows charge an account")
    @PostMapping(path = "/charge")
    public BankAccountDto chargeAnAccount(
            @RequestBody @Validated BankAccountBankrollDto bankAccountBankrollDto
    ) {
        try {
            return bankAccountDtoMapper.toBankAccountDto(
                    bankAccountServiceBusiness.toChargeAnAccount(bankAccountBankrollDto)
            );
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw CONCURRENT_MODIFICATION.exception(ex);
        }
    }

    @ApiOperation(value = "Method allows transfer bankroll from sourceAccount to target account")
    @PostMapping(path = "/transfer")
    public void transferBetweenAccounts(
            @RequestBody @Validated BankAccountBankrollTransferDto bankAccountBankrollTransferDto
    ) {
        try {
            bankAccountServiceBusiness.transferBetweenAccounts(bankAccountBankrollTransferDto);
        } catch (ObjectOptimisticLockingFailureException ex) {
            throw CONCURRENT_MODIFICATION.exception(ex);
        }
    }

    @ApiOperation(value = "Method show active bank accounts")
    @GetMapping
    public List<BankAccountDto> getActiveBankAccount() {
        return bankAccountServiceBusiness.getActiveBankAccount()
                .stream()
                .map(bankAccountDtoMapper::toBankAccountDto)
                .collect(Collectors.toList());
    }
}
