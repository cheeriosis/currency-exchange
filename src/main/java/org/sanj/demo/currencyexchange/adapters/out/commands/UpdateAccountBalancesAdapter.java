package org.sanj.demo.currencyexchange.adapters.out.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.sanj.demo.currencyexchange.adapters.mappers.AccountMapper;
import org.sanj.demo.currencyexchange.adapters.mappers.ExchangeTransactionMapper;
import org.sanj.demo.currencyexchange.adapters.out.commands.jpa.repositories.AccountRepository;
import org.sanj.demo.currencyexchange.adapters.out.commands.jpa.repositories.ExchangeTransactionRepository;
import org.sanj.demo.currencyexchange.application.out.commands.UpdateAccountBalancesPort;
import org.sanj.demo.currencyexchange.domain.Account;
import org.sanj.demo.currencyexchange.domain.events.MoneyConvertedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Component
@Transactional
class UpdateAccountBalancesAdapter implements UpdateAccountBalancesPort {
  AccountRepository accountRepository;
  AccountMapper accountMapper;
  ExchangeTransactionRepository exchangeTransactionRepository;
  ExchangeTransactionMapper exchangeTransactionMapper;

  @Override
  public Account execute(final Command command) {
    return accountRepository.findById(command.number()).map(existing -> {
      existing.setBalances(accountMapper.toEntityBalanceSet(command.balances()));
      return accountMapper.toDomain(existing);
    }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found"));
  }

  @EventListener
  void handle(final MoneyConvertedEvent event) {
    log.info("Handling: {}", event);
    exchangeTransactionRepository.save(exchangeTransactionMapper.toEntity(event));
  }
}
