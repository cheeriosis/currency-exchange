package org.sanj.demo.currencyexchange.adapters.out.queries;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sanj.demo.currencyexchange.adapters.mappers.AccountMapper;
import org.sanj.demo.currencyexchange.adapters.out.commands.jpa.repositories.AccountRepository;
import org.sanj.demo.currencyexchange.application.in.queries.GetAccountUseCase;
import org.sanj.demo.currencyexchange.application.out.queries.GetAccountPort;
import org.sanj.demo.currencyexchange.domain.Account;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

/**
 * GetAccountUseCase should have its own implementation using AccountView entity and repository but at this stage it would be identical to the
 * command model, hence this shared class. Not production worthy!
 */
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@Transactional(readOnly = true)
class GetAccountAdapter implements GetAccountUseCase, GetAccountPort {
  AccountRepository accountRepository;
  AccountMapper accountMapper;

  @Override
  public Account execute(final String number) {
    return accountRepository.findById(number).map(accountMapper::toDomain)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
  }
}
