package org.sanj.demo.currencyexchange.adapters.out.queries;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.sanj.demo.currencyexchange.application.out.queries.GetAccountPort;
import org.sanj.demo.currencyexchange.domain.Account;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Component
class GetAccountAdapter implements GetAccountPort {
  @Override
  public Account execute(final String number) {
    throw new NotImplementedException();
  }
}
