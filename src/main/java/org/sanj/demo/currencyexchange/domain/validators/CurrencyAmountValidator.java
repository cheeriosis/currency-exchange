package org.sanj.demo.currencyexchange.domain.validators;

import lombok.experimental.UtilityClass;
import org.sanj.demo.currencyexchange.domain.Currency;
import org.sanj.demo.currencyexchange.domain.CurrencyAmount;

import java.math.BigDecimal;
import java.util.Objects;

@UtilityClass
public class CurrencyAmountValidator {
  public static void validate(final Currency currency, final BigDecimal amount) {
    Objects.requireNonNull(currency);
    Objects.requireNonNull(amount);

    if (BigDecimal.ZERO.compareTo(amount) >= 0) {
      throw new IllegalArgumentException("Amount must be greater than zero");
    }
  }

  public static void validate(final CurrencyAmount currencyAmount) {
    Objects.requireNonNull(currencyAmount);
    Objects.requireNonNull(currencyAmount.getAmount());
    Objects.requireNonNull(currencyAmount.getCurrency());
  }

  public static void validateInitialBalance(final BigDecimal amount) {
    Objects.requireNonNull(amount);

    if (BigDecimal.ZERO.compareTo(amount) >= 0) {
      throw new IllegalArgumentException("Initial balance must be greater than zero");
    }
  }

  public static void validateConversion(final Currency currency, final BigDecimal rate) {
    Objects.requireNonNull(currency);
    Objects.requireNonNull(rate);

    if (BigDecimal.ZERO.compareTo(rate) >= 0) {
      throw new IllegalArgumentException("Conversion rate must be greater than zero");
    }
  }
}
