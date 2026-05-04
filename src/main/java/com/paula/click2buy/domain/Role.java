package com.paula.click2buy.domain;

public enum Role {
    USER(0),
    ADMIN(1);

    private int value;

    Role(int value) {
        this.value = value;
    }
}
