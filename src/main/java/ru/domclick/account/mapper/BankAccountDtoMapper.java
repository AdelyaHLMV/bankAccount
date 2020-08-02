package ru.domclick.account.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import ru.domclick.account.domain.bankaccount.BankAccount;
import ru.domclick.account.dto.BankAccountDto;

@Mapper
public interface BankAccountDtoMapper {

    BankAccountDtoMapper INSTANCE = Mappers.getMapper(BankAccountDtoMapper.class);

    @Mappings({
            @Mapping(target = "accountNumber", source = "accountNumber"),
            @Mapping(target = "startDate", source = "startDate"),
            @Mapping(target = "closeDate", source = "closeDate"),
            @Mapping(target = "balance", source = "balance")
    })

    BankAccountDto toBankAccountDto(BankAccount source);

}
