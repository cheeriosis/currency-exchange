package org.sanj.demo.currencyexchange.adapters.out.queries;

import org.junit.jupiter.api.Test;
import org.sanj.demo.currencyexchange.application.out.queries.GetAccountPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.server.ResponseStatusException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.ACCOUNT_NUMBER;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.newEnzoDeSensoAccount;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
@Sql(value = {
    "classpath:db/clear.sql",
}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class GetAccountAdapterIT {
  @Autowired
  private GetAccountPort getAccountPort;

  @Test
  @Sql("classpath:db/accounts.sql")
  void shouldReturnNewlyCreatedAccount() {
    final var result = getAccountPort.execute(ACCOUNT_NUMBER);
    assertThat(result).isNotNull().isEqualTo(newEnzoDeSensoAccount());
  }

  @Test
  void shouldThrow404WhenAccountDoesNotExist() {
    assertThatThrownBy(() -> getAccountPort.execute("unknown-id"))
        .isInstanceOf(ResponseStatusException.class)
        .hasMessage("404 NOT_FOUND \"Account not found\"");
  }
}