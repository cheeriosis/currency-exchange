package org.sanj.demo.currencyexchange.application.out.commands;

import org.sanj.demo.currencyexchange.domain.Account;
import org.sanj.demo.currencyexchange.domain.Currency;

import java.math.BigDecimal;
import java.util.Map;

public interface UpdateAccountBalancesPort {
  Account execute(Command command);

  record Command(String number, Map<Currency, BigDecimal> balances) {
  }
}
