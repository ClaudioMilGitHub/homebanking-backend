package com.example.demo.controller;

import com.example.demo.dto.user.BaseUserRequestDTO;
import com.example.demo.dto.user.UserDTO;
import com.example.demo.error.ErrorMessageDTO;
import com.example.demo.error.ValidationErrorDTO;
import com.example.demo.service.definition.UserService;
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
public class UserController {

    private final UserService userService;

    @GetMapping("/all/user/findById/{id}")
    @Tag(
            name = "User API",
            description = "Operations on Application Users"
    )
    @Operation(
            summary = "Find user by user_id",
            description = "Get all information about user with the given id number"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User has been found",
                    content =
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema =
                            @Schema(
                                    implementation = UserDTO.class,
                                    description = "User DTO"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "User with given id not found",
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
    public ResponseEntity<UserDTO> findById (@Validated @PathVariable long id) throws Exception{
        return ResponseEntity.ok(
                userService.findById(id)
        );
    }

    @PostMapping("/all/user/registerBaseUser")
    @Tag(
            name = "User API",
            description = "Operations on Application Users"
    )
    @Operation(
            summary = "Insert Base User",
            description = "Register a new user with base role"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "User successfully registered",
                    content =
                    @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema =
                            @Schema(
                                    implementation = BaseUserRequestDTO.class,
                                    description = "User DTO"
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "Validation error",
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
    public ResponseEntity<Void> insertBaseUser (@Validated @RequestBody BaseUserRequestDTO userDTO) throws Exception {

        userService.insertBaseUser(userDTO);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/all/user/findAll")
    public ResponseEntity<List<UserDTO>> findAll () throws Exception {
        return ResponseEntity.ok(
                userService.findAll()
        );
    }
}
