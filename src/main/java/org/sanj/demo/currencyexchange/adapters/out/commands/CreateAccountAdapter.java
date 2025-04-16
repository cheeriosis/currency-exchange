package org.sanj.demo.currencyexchange.adapters.out.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.NotImplementedException;
import org.sanj.demo.currencyexchange.application.out.commands.CreateAccountPort;
import org.sanj.demo.currencyexchange.domain.Account;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Component
class CreateAccountAdapter implements CreateAccountPort {
  @Override
  public Account execute(final Command command) {
    throw new NotImplementedException();
  }
}
