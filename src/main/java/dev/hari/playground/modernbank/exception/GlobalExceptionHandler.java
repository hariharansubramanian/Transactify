package dev.hari.playground.modernbank.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Catches application exceptions and returns an {@link ErrorDetail} containing an appropriate HTTP status code and message
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Handles {@link InvalidAccountException}, {@link ExceededMaxRequestedTransactionsException},  {@link InsufficientFundsException} and {@link PaymentRequestValidationException} exceptions
     * and returns {@link ErrorDetail} with a {@link HttpStatus#BAD_REQUEST} status code
     *
     * @param e The exception
     * @return {@link ErrorDetail} with {@link HttpStatus#BAD_REQUEST}
     */
    @ExceptionHandler({InvalidAccountException.class, ExceededMaxRequestedTransactionsException.class, InsufficientFundsException.class, PaymentRequestValidationException.class})
    @ResponseBody
    public ErrorDetail handleCustomBadRequestExceptions(Exception e) {
        return new ErrorDetail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    /**
     * Handles all uncaught exceptions and returns {@link ErrorDetail} with a {@link HttpStatus#NOT_IMPLEMENTED}
     *
     * @param e The exception
     * @return {@link ErrorDetail} with {@link HttpStatus#INTERNAL_SERVER_ERROR}
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ErrorDetail handleGenericException(Exception e) {
        return new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }
}
