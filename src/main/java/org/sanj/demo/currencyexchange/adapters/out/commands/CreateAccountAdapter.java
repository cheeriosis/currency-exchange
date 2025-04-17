package org.sanj.demo.currencyexchange.adapters.out.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sanj.demo.currencyexchange.adapters.mappers.AccountMapper;
import org.sanj.demo.currencyexchange.adapters.out.commands.jpa.repositories.AccountRepository;
import org.sanj.demo.currencyexchange.application.out.commands.CreateAccountPort;
import org.sanj.demo.currencyexchange.domain.Account;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
@Transactional
class CreateAccountAdapter implements CreateAccountPort {
  AccountRepository accountRepository;
  AccountMapper accountMapper;

  @Override
  public Account execute(final Command command) {
    return accountMapper.toDomain(accountRepository.save(accountMapper.toEntity(command)));
  }
}
