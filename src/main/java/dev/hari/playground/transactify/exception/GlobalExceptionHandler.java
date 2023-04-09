package dev.hari.playground.transactify.exception;

import dev.hari.playground.transactify.dto.ErrorDetail;
import dev.hari.playground.transactify.exception.classes.*;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<ErrorDetail> handleCustomBadRequestExceptions(Exception e) {
        ErrorDetail errorDetail = new ErrorDetail(HttpStatus.BAD_REQUEST.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetail);
    }

    /**
     * Handles {@link InsufficientFundsException} exception
     *
     * @param e The exception
     * @return {@link ErrorDetail} with {@link HttpStatus#METHOD_NOT_ALLOWED} status code
     */
    @ExceptionHandler({InsufficientFundsException.class})
    public ResponseEntity<ErrorDetail> handleCustomPaymentExceptions(Exception e) {
        var errorDetail = new ErrorDetail(HttpStatus.METHOD_NOT_ALLOWED.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorDetail);
    }

    /**
     * Handles {@link ExchangeRatesFetchException} and returns {@link ErrorDetail} with a {@link HttpStatus#SERVICE_UNAVAILABLE} status code
     *
     * @param e The exception
     * @return {@link ErrorDetail} with {@link HttpStatus#SERVICE_UNAVAILABLE}
     */
    @ExceptionHandler(ExchangeRatesFetchException.class)
    @ResponseBody
    public ResponseEntity<ErrorDetail> handleExchangeRatesFetchException(ExchangeRatesFetchException e) {
        var errorDetail = new ErrorDetail(HttpStatus.SERVICE_UNAVAILABLE.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(errorDetail);
    }

    /**
     * Handles {@link NotImplementedException} and returns {@link ErrorDetail} with a {@link HttpStatus#NOT_IMPLEMENTED} status code
     *
     * @param e The exception
     * @return {@link ErrorDetail} with {@link HttpStatus#NOT_IMPLEMENTED}
     */
    @ExceptionHandler(NotImplementedException.class)
    @ResponseBody
    public ResponseEntity<ErrorDetail> handleNotImplementedException(NotImplementedException e) {
        var errorDetail = new ErrorDetail(HttpStatus.NOT_IMPLEMENTED.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(errorDetail);
    }

    /**
     * Handles all uncaught exceptions and returns {@link ErrorDetail} with a {@link HttpStatus#NOT_IMPLEMENTED}
     *
     * @param e The exception
     * @return {@link ErrorDetail} with {@link HttpStatus#INTERNAL_SERVER_ERROR}
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorDetail> handleGenericException(Exception e) {
        var errorDetail = new ErrorDetail(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetail);
    }
}
