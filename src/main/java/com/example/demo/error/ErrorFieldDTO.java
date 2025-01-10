package com.example.demo.error;

import lombok.Data;
import org.springframework.validation.FieldError;

@Data
public class ErrorFieldDTO {

    private String field;
    private String errorMessage;

    public ErrorFieldDTO (FieldError fieldError) {
        this.field = fieldError.getField();
        this.errorMessage = fieldError.getDefaultMessage();
    }
}
