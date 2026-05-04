package com.paula.click2buy.exceptions;

public class AddressNotFoundException extends RuntimeException {
    public AddressNotFoundException() {
        super("Address not found!");
    }
}
