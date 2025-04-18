package org.sanj.demo.currencyexchange.domain.events;

import org.sanj.demo.currencyexchange.domain.CurrencyAmount;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

public record MoneyConvertedEvent(String accountNumber, CurrencyAmount from, CurrencyAmount to, BigDecimal rate, OffsetDateTime timestamp)
    implements DomainEvent {
  public MoneyConvertedEvent(final String accountNumber, final CurrencyAmount from, final CurrencyAmount to, final BigDecimal rate) {
    this(accountNumber, from, to, rate, OffsetDateTime.now());
  }
}
