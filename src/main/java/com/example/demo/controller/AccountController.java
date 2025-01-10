package com.example.demo.controller;

import com.example.demo.dto.account.AccountDTO;
import com.example.demo.dto.account.AccountRequestDTO;
import com.example.demo.error.ErrorMessageDTO;
import com.example.demo.error.ValidationErrorDTO;
import com.example.demo.service.definition.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/user/account/findAll")
    public ResponseEntity<List<AccountDTO>> findAll () throws Exception{
        return ResponseEntity.ok(
                accountService.findAll()
        );
    }


    @GetMapping("/all/account/findById/{id}")
    @Tag(
            name = "Account API",
            description = "Operations on application accounts"
    )
    @Operation(
            summary = "Find account by account_id",
            description = "Get all the information about account with the given id number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Account has been found",
                    content =
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema =
                            @Schema(
                                    implementation = AccountDTO.class,
                                    description = "Account DTO class"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Account with given id not found",
                    content =
                    @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema =
                            @Schema(
                                    implementation = ErrorMessageDTO.class
                            )
                    )
            )
    })
    public ResponseEntity<AccountDTO> findAccountById (@PathVariable long id ) throws Exception {

        return ResponseEntity.ok(
                accountService.findById(id)
        );
    }

    @PostMapping("/all/account/insert")
    @Tag(
            name = "Account API",
            description = "Operations on application accounts"
    )
    @Operation(
            summary = "Insert Account",
            description = "Register a new account in database. The given account must be validated in input by providing "
                    + "account number not null and user not null"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Account has been registered",
                    content =
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema =
                            @Schema(
                                    implementation = AccountDTO.class,
                                    description = "Account DTO class"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error. Account number or User fields not valid",
                    content =
                    @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema =
                            @Schema(
                                    implementation = ValidationErrorDTO.class,
                                    description = "Error message with displayed Error Fields"
                            )
                    )
            )
    })
    public ResponseEntity<Void> insertAccount (@Validated @RequestBody AccountRequestDTO dto) throws Exception{

        accountService.insertAccount(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/user/account/{accountNumber}/withdraw")
    @Tag(
            name = "Account API",
            description = "Operations on application accounts"
    )
    @Operation(
            summary = "make transaction withdrawal",
            description = "The account with the given id number performs a withdrawal of the specified amount. "
                    + "This API also creates a Transaction entity to save transaction informations such as amount and time"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Withdrawal successful",
                    content =
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema =
                            @Schema(
                                    implementation = AccountDTO.class,
                                    description = "Account DTO class"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error. id not valid or amount exceeds current account balance",
                    content =
                    @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema =
                            @Schema(
                                    implementation = ValidationErrorDTO.class,
                                    description = "Error message with displayed Error Fields"
                            )
                    )
            )
    })
    public ResponseEntity<AccountDTO> withdraw (@PathVariable String accountNumber, @RequestParam double amount) throws Exception {

        return ResponseEntity.ok(
                accountService.withdraw(accountNumber, amount)
        );
    }

    @PostMapping("/all/account/{accountNumber}/deposit")
    @Tag(
            name = "Account API",
            description = "Operations on application accounts"
    )
    @Operation(
            summary = "Makes transaction deposit",
            description = "The account with the given id number performs a deposit of the amount specified. "
                    + "This API also creates a Transaction entity to save transaction informations such as amount and time"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Deposit successful",
                    content =
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema =
                            @Schema(
                                    implementation = AccountDTO.class,
                                    description = "Account DTO class"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error. id not valid or amount not valid",
                    content =
                    @Content(
                            mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                            schema =
                            @Schema(
                                    implementation = ValidationErrorDTO.class,
                                    description = "Error message with displayed Error Fields"
                            )
                    )
            )
    })
    public ResponseEntity<AccountDTO> deposit (@PathVariable String accountNumber, @RequestParam double amount) throws Exception {

        return ResponseEntity.ok(
                accountService.deposit(accountNumber, amount)
        );
    }

}