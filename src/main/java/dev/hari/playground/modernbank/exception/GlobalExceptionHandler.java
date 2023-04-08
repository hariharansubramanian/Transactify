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
     * Handles {@link InvalidAccountException}, {@link ExceededMaxRequestedTransactionsException} and {@link PaymentRequestValidationException} exceptions
     * and returns {@link ErrorDetail} with a {@link HttpStatus#BAD_REQUEST} status code
     *
     * @param e The exception
     * @return {@link ErrorDetail} with {@link HttpStatus#BAD_REQUEST}
     */
    @ExceptionHandler({InvalidAccountException.class, ExceededMaxRequestedTransactionsException.class, PaymentRequestValidationException.class})
    @ResponseBody
    public ErrorDetail handleCustomBadRequestExceptions(Exception e) {
        return new ErrorDetail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    /**
     * Handles {@link InsufficientFundsException} and {@link PaymentNotAllowedException} exceptions
     *
     * @param e The exception
     * @return {@link ErrorDetail} with {@link HttpStatus#METHOD_NOT_ALLOWED} status code
     */
    @ExceptionHandler({InsufficientFundsException.class, PaymentNotAllowedException.class})
    @ResponseBody
    public ErrorDetail handleCustomPaymentExceptions(Exception e) {
        return new ErrorDetail(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
    }


    /**
     * Handles {@link ExchangeRatesFetchException} and returns {@link ErrorDetail} with a {@link HttpStatus#SERVICE_UNAVAILABLE} status code
     *
     * @param e The exception
     * @return {@link ErrorDetail} with {@link HttpStatus#SERVICE_UNAVAILABLE}
     */
    @ExceptionHandler(ExchangeRatesFetchException.class)
    @ResponseBody
    public ErrorDetail handleExchangeRatesFetchException(ExchangeRatesFetchException e) {
        return new ErrorDetail(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
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
