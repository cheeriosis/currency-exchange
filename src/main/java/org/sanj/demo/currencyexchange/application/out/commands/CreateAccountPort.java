package org.sanj.demo.currencyexchange.application.out.commands;

import org.sanj.demo.currencyexchange.domain.Account;
import org.sanj.demo.currencyexchange.domain.Currency;
import org.sanj.demo.currencyexchange.domain.Owner;

import java.math.BigDecimal;
import java.util.Map;

public interface CreateAccountPort {
  Account execute(Command command);

  record Command(String number, Owner owner, Map<Currency, BigDecimal> balances) {
  }
}
