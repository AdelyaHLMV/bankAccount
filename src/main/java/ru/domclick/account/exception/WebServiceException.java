package ru.domclick.account.exception;

import lombok.*;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;

@Data
@EqualsAndHashCode(callSuper=false)
public class WebServiceException extends RuntimeException {

    private static final long serialVersionUID = 1267870138197926142L;

    private Integer code;
    private HttpStatus status;

    @Builder
    public WebServiceException(String message, Integer code, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.status = ObjectUtils.isEmpty(status) ?  HttpStatus.INTERNAL_SERVER_ERROR : status;
    }

}
