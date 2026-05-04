package com.paula.click2buy.exceptions;

public class ItemCartNotFoundException extends RuntimeException {
  public ItemCartNotFoundException() {
    super("ItemCart not found");
  }
}
