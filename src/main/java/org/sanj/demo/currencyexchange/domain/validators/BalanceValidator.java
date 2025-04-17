package org.sanj.demo.currencyexchange.domain.validators;

import lombok.experimental.UtilityClass;
import org.sanj.demo.currencyexchange.domain.Currency;
import org.sanj.demo.currencyexchange.domain.CurrencyAmount;
import org.sanj.demo.currencyexchange.domain.exceptions.AccountCurrencyException;
import org.sanj.demo.currencyexchange.domain.exceptions.InvalidAmountException;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Objects;

@UtilityClass
public class BalanceValidator {
  public static void validate(final Map<Currency, BigDecimal> balances, final CurrencyAmount from, final CurrencyAmount to) {
    Objects.requireNonNull(balances);
    CurrencyAmountValidator.validate(from);
    CurrencyAmountValidator.validate(to);

    if (balances.isEmpty() || Objects.isNull(balances.get(from.getCurrency()))) {
      throw new AccountCurrencyException(from.getCurrency());
    }

    final var baseCurrency = from.getCurrency();
    if (balances.get(baseCurrency).compareTo(from.getAmount()) < 0) {
      throw new InvalidAmountException(baseCurrency);
    }
  }
}
