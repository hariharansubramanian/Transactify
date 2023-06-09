package dev.hari.playground.transactify.controller;

import dev.hari.playground.transactify.dto.getBalance.GetAccountBalanceResult;
import dev.hari.playground.transactify.dto.getDetails.GetAccountDetailsResult;
import dev.hari.playground.transactify.dto.getStatement.GetStatementResult;
import dev.hari.playground.transactify.dto.ErrorDetail;
import dev.hari.playground.transactify.exception.classes.ExceededMaxRequestedTransactionsException;
import dev.hari.playground.transactify.exception.classes.InvalidAccountException;
import dev.hari.playground.transactify.service.AccountService;
import dev.hari.playground.transactify.service.impl.bank.BankAccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Max;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Account", description = "APIs to get account information")
public class AccountController {

    private final AccountService accountService;

    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping(value = "{accountId}/balance", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get account balance", description = "Gets account balance of the account id specified in request body", tags = {"Account"})
    @ApiResponse(responseCode = "200",
            description = "Returns the specified account's balance",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = GetAccountBalanceResult.class)), useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400",
            description = "Invalid Account ID supplied",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = ErrorDetail.class)))

    public GetAccountBalanceResult getBalance(@Parameter(description = "Account id to get balance for", required = true)
                                              @PathVariable long accountId) throws InvalidAccountException {

        return accountService.getBalance(accountId);

    }

    @GetMapping(value = "{accountId}/statement", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get account statement", description = "Gets the list of recent transactions for the account specified", tags = {"Account"})
    @ApiResponse(responseCode = "200",
            description = "Returns the list of recent transactions made to this account. The number of transactions listed are specified by the request param transactionCount",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetStatementResult.class)), useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400",
            description = "Invalid Account ID supplied",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetail.class)))
    @ApiResponse(responseCode = "400",
            description = "Too many transactions requested",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetail.class)))

    public GetStatementResult getStatement(@Parameter(description = "Account id to get statement for", required = true)
                                           @PathVariable long accountId,
                                           @Parameter(description = "The number of transactions to list in the statement. Defaults to the last 20 transactions. Must be greater than 0 and less than 50 ", required = false)
                                           @Max(value = BankAccountService.MAX_REQUESTED_TRANSACTIONS_LIMIT)
                                           @RequestParam(defaultValue = "20") int transactionCount) throws ExceededMaxRequestedTransactionsException, InvalidAccountException {

        return accountService.getStatement(accountId, transactionCount);
    }

    @GetMapping(value = "{accountId}/details", produces = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Get account details", description = "Gets the details of the account specified", tags = {"Account"})
    @ApiResponse(responseCode = "200",
            description = "Returns the details of the account specified",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetAccountDetailsResult.class)), useReturnTypeSchema = true)
    @ApiResponse(responseCode = "400",
            description = "Invalid Account ID supplied",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorDetail.class)))

    public GetAccountDetailsResult getAccountDetails(@Parameter(description = "Account id to get details for", required = true)
                                                     @PathVariable long accountId) throws InvalidAccountException {

        return accountService.getDetails(accountId);
    }
}
