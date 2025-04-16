package org.sanj.demo.currencyexchange.application.in.services;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.sanj.demo.currencyexchange.application.in.commands.ConvertAmountUseCase;
import org.sanj.demo.currencyexchange.application.in.commands.OpenAccountUseCase;
import org.sanj.demo.currencyexchange.application.out.commands.CreateAccountPort;
import org.sanj.demo.currencyexchange.application.out.commands.UpdateAccountBalancesPort;
import org.sanj.demo.currencyexchange.application.out.queries.GenerateUniqueAccountNumberPort;
import org.sanj.demo.currencyexchange.application.out.queries.GetAccountPort;
import org.sanj.demo.currencyexchange.application.out.queries.GetCurrencyPairRatePort;
import org.sanj.demo.currencyexchange.domain.Account;
import org.sanj.demo.currencyexchange.domain.CurrencyAmount;
import org.sanj.demo.currencyexchange.domain.Owner;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Service
class AccountCommandService implements OpenAccountUseCase, ConvertAmountUseCase {
  GenerateUniqueAccountNumberPort generateUniqueAccountNumberPort;
  CreateAccountPort createAccountPort;
  GetCurrencyPairRatePort getCurrencyPairRatePort;
  GetAccountPort getAccountPort;
  UpdateAccountBalancesPort updateAccountBalancesPort;

  @Override
  public String execute(final OpenAccountUseCase.Command command) {
    final var owner = Owner.create(command.firstName(), command.lastName());
    final var account = Account.create(generateUniqueAccountNumberPort.execute(), owner, command.initialBalance());
    return createAccountPort.execute(new CreateAccountPort.Command(account.getNumber(), account.getOwner(), account.getBalances())).getNumber();
  }

  @Override
  public void execute(final ConvertAmountUseCase.Command command) {
    final var from = CurrencyAmount.create(command.from(), command.amount());
    if (from.getCurrency().equals(command.to())) {
      throw new IllegalArgumentException("Base and quote currency cannot be equal");
    }

    final var account = getAccountPort.execute(command.accountNumber());
    final var rate = getCurrencyPairRatePort.execute(command.from(), command.to());
    account.moveMoney(from, from.convert(command.to(), rate));
    updateAccountBalancesPort.execute(new UpdateAccountBalancesPort.Command(account.getNumber(), account.getBalances()));
  }
}
