package org.sanj.demo.currencyexchange.adapters.in.queries;

import org.junit.jupiter.api.Test;
import org.sanj.demo.currencyexchange.application.in.queries.GetAccountUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.ACCOUNT_NUMBER;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.FIRST_NAME_ENZO;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.INITIAL_BALANCE_23;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.LAST_NAME_DE_SENSO;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.newEnzoDeSensoAccount;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountQueryController.class)
class AccountQueryControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GetAccountUseCase getAccountUseCase;

  @Test
  void shouldReturnNewlyCreatedAccount() throws Exception {
    when(getAccountUseCase.execute(ACCOUNT_NUMBER)).thenReturn(newEnzoDeSensoAccount());

    mockMvc.perform(get("/accounts/%s".formatted(ACCOUNT_NUMBER)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.number").value(ACCOUNT_NUMBER))
        .andExpect(jsonPath("$.owner.firstName").value(FIRST_NAME_ENZO))
        .andExpect(jsonPath("$.owner.lastName").value(LAST_NAME_DE_SENSO))
        .andExpect(jsonPath("$.balances.PLN").value(INITIAL_BALANCE_23));
  }

  @Test
  void shouldReturn400WhenAccountNumberTooShort() throws Exception {
    verify(getAccountUseCase, never()).execute("12345");

    mockMvc.perform(get("/accounts/%s".formatted("12345"))).andExpect(status().isBadRequest());
  }

  @Test
  void shouldReturn404WhenAccountDoesNotExist() throws Exception {
    when(getAccountUseCase.execute(ACCOUNT_NUMBER)).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));

    mockMvc.perform(get("/accounts/%s".formatted(ACCOUNT_NUMBER))).andExpect(status().isNotFound());
  }
}