package com.example.demo.error;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ValidationErrorDTO {

    private LocalDateTime timestamp;
    private int statusCode;
    private String statusMessage;
    private String customMessage;
    private List<ErrorFieldDTO> errors;
}
