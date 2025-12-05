package com.paula.click2buy.exceptions;

public class StockQuantityNotFoundException extends RuntimeException {
    public StockQuantityNotFoundException(String message) {
        super(message);
    }
}
