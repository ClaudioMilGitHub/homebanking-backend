package com.example.demo.exceptions.custom;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class InsufficientFundsException extends Exception{

    private String message;

    public InsufficientFundsException (String message) {
        this.message = message;
    }
}