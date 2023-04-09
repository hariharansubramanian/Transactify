package dev.hari.playground.transactify.controller;

import dev.hari.playground.transactify.dto.ErrorDetail;
import dev.hari.playground.transactify.dto.processPayment.PaymentRequest;
import dev.hari.playground.transactify.exception.classes.ExchangeRatesFetchException;
import dev.hari.playground.transactify.exception.classes.InsufficientFundsException;
import dev.hari.playground.transactify.exception.classes.InvalidAccountException;
import dev.hari.playground.transactify.exception.classes.PaymentRequestValidationException;
import dev.hari.playground.transactify.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@Tag(name = "Payment", description = "APIs to handle payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping(value = "process", produces = "application/json")
    @Operation(summary = "Process a payment", description = "Transfers funds from source account into destination account", tags = {"Payment"})
    @ApiResponse(responseCode = "200",
            description = "Payment process successful",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE), useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400",
            description = "Payment request validation failed",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetail.class)))
    @ApiResponse(responseCode = "400",
            description = "Invalid account",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetail.class)))
    @ApiResponse(responseCode = "400",
            description = "Insufficient funds",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetail.class)))
    @ApiResponse(responseCode = "503",
            description = "Exchange rates fetch failure",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetail.class)))

    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest request) throws PaymentRequestValidationException, InvalidAccountException, InsufficientFundsException, ExchangeRatesFetchException {
        // Process payment
        paymentService.processPayment(request);

        // return success
        return new ResponseEntity<>("Payment processed successfully", HttpStatus.OK);
    }
}
