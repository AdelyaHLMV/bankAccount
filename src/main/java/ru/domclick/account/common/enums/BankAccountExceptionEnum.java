package ru.domclick.account.common.enums;

import lombok.Getter;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.lang.Nullable;
import ru.domclick.account.exception.WebServiceException;

@Getter
public enum BankAccountExceptionEnum {

    BANK_ACCOUNT_NOT_FOUND_EXCEPTION(10100, "Error! Bank account with id = {} not found."),
    CONCURRENT_MODIFICATION(10101, "Error! Concurrent modification for bank account."),
    OVERDRAFT_EXCEPTION(10102, "Error! Overdraft for bank account with id = {}."),
    SAME_ACCOUNT_NUMBER_EXCEPTION(10103, "Error! Source account number equals target account number."),
    DEFAULT_EXCEPTION(11100, "Internal error! Please, contact the administrator.");

    private Integer code;
    private String message;

    BankAccountExceptionEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public WebServiceException exception(@Nullable final Object... args) {
        final FormattingTuple tuple = MessageFormatter.arrayFormat(message, args);
        throw WebServiceException
                .builder()
                .message(tuple.getMessage())
                .code(code)
                .cause(tuple.getThrowable())
                .build();
    }
}
