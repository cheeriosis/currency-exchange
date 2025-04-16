package org.sanj.demo.currencyexchange.domain.exceptions;

public class AccountCurrencyException extends RuntimeException {
  public AccountCurrencyException() {
    super("Account has no currency");
  }
}
