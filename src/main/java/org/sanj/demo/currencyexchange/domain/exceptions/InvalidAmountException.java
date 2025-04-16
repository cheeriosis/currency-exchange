package org.sanj.demo.currencyexchange.domain.exceptions;

import org.sanj.demo.currencyexchange.domain.Currency;

public class InvalidAmountException extends RuntimeException {
  public InvalidAmountException(final Currency currency) {
    super("Invalid %s balance".formatted(currency));
  }
}
