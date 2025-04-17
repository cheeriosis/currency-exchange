package org.sanj.demo.currencyexchange.adapters.out.commands;

import org.junit.jupiter.api.Test;
import org.sanj.demo.currencyexchange.application.out.commands.CreateAccountPort;
import org.sanj.demo.currencyexchange.domain.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.CREATE_NEW_ENZO_DE_SENSO_ACCOUNT_COMMAND_OK;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.newEnzoDeSensoAccount;

@SpringBootTest
class CreateAccountAdapterIT {
  @Autowired
  private CreateAccountPort createAccountPort;

  @Test
  void shouldCreateNewAccountWithProvidedBalance() {
    final var result = createAccountPort.execute(CREATE_NEW_ENZO_DE_SENSO_ACCOUNT_COMMAND_OK);
    assertThat(result).isNotNull().isInstanceOf(Account.class).isEqualTo(newEnzoDeSensoAccount());
  }
}