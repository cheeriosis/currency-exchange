package org.sanj.demo.currencyexchange.adapters.out.queries;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.commons.lang3.RandomStringUtils;
import org.sanj.demo.currencyexchange.adapters.out.commands.jpa.repositories.AccountRepository;
import org.sanj.demo.currencyexchange.application.out.queries.GenerateUniqueAccountNumberPort;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
class GenerateUniqueAccountNumberAdapter implements GenerateUniqueAccountNumberPort {
  AccountRepository accountRepository;

  @Override
  public String execute() {
    String number;

    do {
      number = RandomStringUtils.secure().nextNumeric(10);
    } while (accountRepository.existsById(number));

    return number;
  }
}
