package org.sanj.demo.currencyexchange.domain.exceptions;

import org.sanj.demo.currencyexchange.domain.Currency;

public class AccountCurrencyException extends RuntimeException {
  public AccountCurrencyException(final Currency currency) {
    super("Account has no %s currency".formatted(currency));
  }
}
