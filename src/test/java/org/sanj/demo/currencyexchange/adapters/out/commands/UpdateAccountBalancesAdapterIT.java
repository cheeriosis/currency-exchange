package org.sanj.demo.currencyexchange.adapters.out.commands;

import org.junit.jupiter.api.Test;
import org.sanj.demo.currencyexchange.application.out.commands.UpdateAccountBalancesPort;
import org.sanj.demo.currencyexchange.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.NEW_ENZO_DE_SENSO_BALANCE_MAP_AFTER_PLN_TO_USD_CONVERSION;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.UPDATE_NEW_ACCOUNT_BALANCES_COMMAND_OK_PLN_TO_USD;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.newEnzoDeSensoAccount;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Sql(value = {
    "classpath:db/clear.sql",
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class UpdateAccountBalancesAdapterIT {
  @Autowired
  private UpdateAccountBalancesPort updateAccountBalancesPort;

  @Test
  @Sql("classpath:db/accounts.sql")
  void shouldUpdateAccountBalances() {
    final var result = updateAccountBalancesPort.execute(UPDATE_NEW_ACCOUNT_BALANCES_COMMAND_OK_PLN_TO_USD);
    assertThat(result).isNotNull().isInstanceOf(Account.class).usingRecursiveComparison().ignoringFields("balances")
        .isEqualTo(newEnzoDeSensoAccount());
    assertThat(result.getBalances()).isNotEmpty().isEqualTo(NEW_ENZO_DE_SENSO_BALANCE_MAP_AFTER_PLN_TO_USD_CONVERSION);
  }

  @Test
  void shouldThrow404WhenTryingToConvertUnknownAccountBalances() {
    assertThatThrownBy(() -> updateAccountBalancesPort.execute(UPDATE_NEW_ACCOUNT_BALANCES_COMMAND_OK_PLN_TO_USD))
        .isInstanceOf(ResponseStatusException.class).hasMessage("404 NOT_FOUND \"Account not found\"");
  }
}