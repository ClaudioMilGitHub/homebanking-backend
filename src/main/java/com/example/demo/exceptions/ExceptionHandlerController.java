package com.example.demo.exceptions;

import com.example.demo.error.ErrorFieldDTO;
import com.example.demo.error.ErrorMessageDTO;
import com.example.demo.error.ValidationErrorDTO;
import com.example.demo.exceptions.custom.InsufficientFundsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlerController {

    //Exception when validation fails
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDTO> methodArgumentNotValidExceptionHandler (MethodArgumentNotValidException exception,
                                                                WebRequest request) {
        ValidationErrorDTO validationErrorDTO = new ValidationErrorDTO();
        validationErrorDTO.setTimestamp(LocalDateTime.now());
        validationErrorDTO.setCustomMessage("Validation argument failed");
        validationErrorDTO.setStatusCode(HttpStatus.BAD_REQUEST.value());
        validationErrorDTO.setStatusMessage(HttpStatus.BAD_REQUEST.toString());
        validationErrorDTO.setErrors(exception.getFieldErrors().stream()
                .map(ErrorFieldDTO::new).toList());
        return ResponseEntity.badRequest().body(validationErrorDTO);
    }

    //Entity not found exception
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorMessageDTO> entityNotFoundExceptionHandler (EntityNotFoundException exception,
                                                                              WebRequest request) {
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
        errorMessageDTO.setTimestamp(LocalDateTime.now());
        errorMessageDTO.setPath(request.getDescription(false));
        errorMessageDTO.setMessage(exception.getMessage());
        errorMessageDTO.setErrorType(HttpStatus.BAD_REQUEST.toString());

        return ResponseEntity.badRequest().body(errorMessageDTO);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public ResponseEntity<ErrorMessageDTO> insufficientFundsExceptionHandler (InsufficientFundsException exception,
                                                                              WebRequest request) {

        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
        errorMessageDTO.setTimestamp(LocalDateTime.now());
        errorMessageDTO.setPath(request.getDescription(false));
        errorMessageDTO.setMessage(exception.getMessage());
        errorMessageDTO.setErrorType(HttpStatus.BAD_REQUEST.toString());

        return ResponseEntity.badRequest().body(errorMessageDTO);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorMessageDTO> responseStatusExceptionHandler (ResponseStatusException exception,
                                                                           WebRequest request) {

        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
        errorMessageDTO.setTimestamp(LocalDateTime.now());
        errorMessageDTO.setPath(request.getDescription(false));
        errorMessageDTO.setMessage(exception.getMessage());
        errorMessageDTO.setErrorType(HttpStatus.BAD_REQUEST.toString());

        return ResponseEntity.badRequest().body(errorMessageDTO);
    }

}
