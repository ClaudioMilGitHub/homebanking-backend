package com.example.demo.error;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ErrorMessageDTO {

    private String path;
    private String message;
    private String errorType;
    private LocalDateTime timestamp;
}
