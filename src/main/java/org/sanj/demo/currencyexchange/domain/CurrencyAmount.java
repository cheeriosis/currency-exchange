package org.sanj.demo.currencyexchange.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.sanj.demo.currencyexchange.domain.validators.CurrencyAmountValidator;

import java.math.BigDecimal;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Builder
public class CurrencyAmount {
  Currency currency;
  BigDecimal amount;

  public static CurrencyAmount create(final Currency currency, final BigDecimal amount) {
    CurrencyAmountValidator.validate(currency, amount);
    return CurrencyAmount.builder().currency(currency).amount(amount).build();
  }

  public CurrencyAmount convert(final Currency to, final BigDecimal rate) {
    CurrencyAmountValidator.validateConversion(to, rate);
    return CurrencyAmount.create(to, amount.multiply(rate));
  }
}
