package org.sanj.demo.currencyexchange.application.in.queries;

import org.sanj.demo.currencyexchange.domain.Account;

public interface GetAccountUseCase {
  Account execute(String number);
}
