package dev.hari.playground.modernbank.controller;

import dev.hari.playground.modernbank.dto.processPayment.PaymentRequest;
import dev.hari.playground.modernbank.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
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
    @Operation(summary = "Make a payment", description = "Transfers funds from one account to another", tags = {"Payment"})
    @ApiResponse(responseCode = "200", description = "Payment process successful")
    @ApiResponse(responseCode = "400", description = "Invalid account")
    @ApiResponse(responseCode = "400", description = "Insufficient funds")
    public ResponseEntity<String> processPayment(@RequestBody PaymentRequest request) {
        // TODO: Add validation for request
        paymentService.processPayment(request);

        return new ResponseEntity<>("Payment processed successfully", HttpStatus.OK);
    }
}
