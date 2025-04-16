package org.sanj.demo.currencyexchange.application.out.queries;

import org.sanj.demo.currencyexchange.domain.Currency;

import java.math.BigDecimal;

public interface GetCurrencyPairRatePort {
  BigDecimal execute(Currency from, Currency to);
}
