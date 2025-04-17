package org.sanj.demo.currencyexchange.adapters.in.commands;

import org.junit.jupiter.api.Test;
import org.sanj.demo.currencyexchange.application.in.commands.ConvertAmountUseCase;
import org.sanj.demo.currencyexchange.application.in.commands.OpenAccountUseCase;
import org.sanj.demo.currencyexchange.domain.exceptions.AccountCurrencyException;
import org.sanj.demo.currencyexchange.domain.exceptions.InvalidAmountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.ACCOUNT_NUMBER;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.CONVERT_10_PLN_TO_USD_COMMAND_OK;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.CONVERT_AMOUNT;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.FIRST_NAME_ENZO;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.INITIAL_BALANCE_23;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.LAST_NAME_DE_SENSO;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.OPEN_ACCOUNT_COMMAND_OK;
import static org.sanj.demo.currencyexchange.domain.Currency.PLN;
import static org.sanj.demo.currencyexchange.domain.Currency.USD;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountCommandController.class)
class AccountCommandControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OpenAccountUseCase openAccountUseCase;

  @MockBean
  private ConvertAmountUseCase convertAmountUseCase;

  @Test
  void shouldOpenAccountWithProvidedInitialBalance() throws Exception {
    when(openAccountUseCase.execute(OPEN_ACCOUNT_COMMAND_OK)).thenReturn(ACCOUNT_NUMBER);

    mockMvc.perform(post("/accounts")
            .contentType(APPLICATION_JSON)
            .content("""
                {
                  "firstName": "%s",
                  "lastName": "%s",
                  "initialBalance": %s
                }""".formatted(FIRST_NAME_ENZO, LAST_NAME_DE_SENSO, INITIAL_BALANCE_23)))
        .andExpect(status().isCreated())
        .andExpect(header().string(HttpHeaders.LOCATION, "/accounts/%s".formatted(ACCOUNT_NUMBER)));

    verify(convertAmountUseCase, never()).execute(any());
  }

  @Test
  void shouldReturn400WhenOpeningAccountWithZeroInitialBalance() throws Exception {
    mockMvc.perform(post("/accounts")
            .contentType(APPLICATION_JSON)
            .content("""
                {
                  "firstName": "%s",
                  "lastName": "%s",
                  "initialBalance": %s
                }""".formatted(FIRST_NAME_ENZO, LAST_NAME_DE_SENSO, BigDecimal.ZERO)))
        .andExpect(status().isBadRequest());

    verify(openAccountUseCase, never()).execute(any());
    verify(convertAmountUseCase, never()).execute(any());
  }

  @Test
  void shouldConvertMoney() throws Exception {
    mockMvc.perform(patch("/accounts/%s/balances:exchange".formatted(ACCOUNT_NUMBER))
            .contentType(APPLICATION_JSON)
            .content("""
                {
                  "amount": %s,
                  "from": "%s",
                  "to": "%s"
                }""".formatted(CONVERT_AMOUNT, PLN, USD)))
        .andExpect(status().isNoContent());

    verify(openAccountUseCase, never()).execute(any());
    verify(convertAmountUseCase).execute(CONVERT_10_PLN_TO_USD_COMMAND_OK);
  }

  @Test
  void shouldReturn400WhenConvertingFromInsufficientMoneyCurrency() throws Exception {
    doThrow(new InvalidAmountException(USD)).when(convertAmountUseCase).execute(any());

    mockMvc.perform(patch("/accounts/%s/balances:exchange".formatted(ACCOUNT_NUMBER))
            .contentType(APPLICATION_JSON)
            .content("""
                {
                  "amount": %s,
                  "from": "%s",
                  "to": "%s"
                }""".formatted(CONVERT_AMOUNT, PLN, USD)))
        .andExpect(status().isBadRequest());

    verify(openAccountUseCase, never()).execute(any());
    verify(convertAmountUseCase).execute(CONVERT_10_PLN_TO_USD_COMMAND_OK);
  }

  @Test
  void shouldReturn404WhenConvertingFromUnknownAccount() throws Exception {
    doThrow(new ResponseStatusException(NOT_FOUND)).when(convertAmountUseCase).execute(any());

    mockMvc.perform(patch("/accounts/%s/balances:exchange".formatted(ACCOUNT_NUMBER))
            .contentType(APPLICATION_JSON)
            .content("""
                {
                  "amount": %s,
                  "from": "%s",
                  "to": "%s"
                }""".formatted(CONVERT_AMOUNT, PLN, USD)))
        .andExpect(status().isNotFound());

    verify(openAccountUseCase, never()).execute(any());
    verify(convertAmountUseCase).execute(CONVERT_10_PLN_TO_USD_COMMAND_OK);
  }

  @Test
  void shouldReturn422WhenConvertingFromMissingCurrency() throws Exception {
    doThrow(new AccountCurrencyException(PLN)).when(convertAmountUseCase).execute(any());

    mockMvc.perform(patch("/accounts/%s/balances:exchange".formatted(ACCOUNT_NUMBER))
            .contentType(APPLICATION_JSON)
            .content("""
                {
                  "amount": %s,
                  "from": "%s",
                  "to": "%s"
                }""".formatted(CONVERT_AMOUNT, PLN, USD)))
        .andExpect(status().isUnprocessableEntity());

    verify(openAccountUseCase, never()).execute(any());
    verify(convertAmountUseCase).execute(CONVERT_10_PLN_TO_USD_COMMAND_OK);
  }
}