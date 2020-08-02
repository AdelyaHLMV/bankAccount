package ru.domclick.account.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.domclick.account.exception.WebServiceException;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class WebServiceErrorAttributes extends DefaultErrorAttributes {
    private static final String DEFAULT_MESSAGE = "Internal Server Error";

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
        UUID uuid = UUID.randomUUID();

        Map<String, Object> errorAttributes = new LinkedHashMap<>();

        Throwable throwable = getError(webRequest);
        setUuid(errorAttributes, uuid);
        setErrorAttributes(errorAttributes, webRequest, includeStackTrace);
        if (throwable instanceof WebServiceException) {
            WebServiceException exc = ((WebServiceException) throwable);
            setHttpStatus(errorAttributes, webRequest, exc.getStatus());
            setErrorCode(errorAttributes, exc.getCode());
            setMessage(errorAttributes, exc.getMessage());
        } else if (throwable instanceof ConstraintViolationException) {
            setHttpStatus(errorAttributes, webRequest, HttpStatus.BAD_REQUEST);
        } else if (throwable instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException exc = (MethodArgumentTypeMismatchException) throwable;
            String message = String.format("Parameter '%s' = '%s' type mismatch", exc.getName(), exc.getValue());
            setHttpStatus(errorAttributes, webRequest, HttpStatus.BAD_REQUEST);
            setMessage(errorAttributes, message);
        } else if(throwable instanceof MethodArgumentNotValidException){
            MethodArgumentNotValidException exc = (MethodArgumentNotValidException) throwable;
            Map<String,String> errorDetails = new HashMap<>();
            exc.getBindingResult().getAllErrors().forEach((error) -> {
                String fieldName = ((FieldError) error).getField();
                String errorMessage = error.getDefaultMessage();
                errorDetails.put(fieldName, errorMessage);
            });
            setMessage(errorAttributes, errorDetails.toString());
        }

        logging(throwable, uuid);

        return errorAttributes;
    }

    private void logging(Throwable throwable, UUID uuid) {
        String message = String.format("[UUID = %s]", uuid);
        log.error(message, throwable);
    }

    private void setMessage(Map<String, Object> errorAttributes, String message) {
        errorAttributes.put("message", StringUtils.isEmpty(message) ? DEFAULT_MESSAGE : message);
    }

    private void setErrorAttributes(Map<String, Object> errorAttributes,
                                    WebRequest webRequest,
                                    boolean includeStackTrace) {
        errorAttributes.putAll(super.getErrorAttributes(webRequest, includeStackTrace));
    }

    private void setUuid(Map<String, Object> errorAttributes, UUID uuid) {
        errorAttributes.put("uuid", uuid.toString());
    }

    private void setErrorCode(Map<String, Object> errorAttributes, Integer code) {
        if (!ObjectUtils.isEmpty(code))
            errorAttributes.put("code", code);
    }

    private void setHttpStatus(Map<String, Object> errorAttributes,
                               RequestAttributes requestAttributes,
                               HttpStatus status) {
        setAttribute(requestAttributes, "javax.servlet.error.status_code", status.value());
        errorAttributes.put("status", status.value());
        errorAttributes.put("error", status.getReasonPhrase());
    }

    private void setAttribute(RequestAttributes requestAttributes, String name, Object value) {
        requestAttributes.setAttribute(name, value, RequestAttributes.SCOPE_REQUEST);
    }

}
