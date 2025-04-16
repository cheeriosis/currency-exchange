package org.sanj.demo.currencyexchange.application.out.queries;

import org.sanj.demo.currencyexchange.domain.Account;

public interface GetAccountPort {
  Account execute(String number);
}
