package org.sanj.demo.currencyexchange.application.in.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sanj.demo.currencyexchange.application.out.commands.CreateAccountPort;
import org.sanj.demo.currencyexchange.application.out.commands.UpdateAccountBalancesPort;
import org.sanj.demo.currencyexchange.application.out.queries.GenerateUniqueAccountNumberPort;
import org.sanj.demo.currencyexchange.application.out.queries.GetAccountPort;
import org.sanj.demo.currencyexchange.application.out.queries.GetCurrencyPairRatePort;
import org.sanj.demo.currencyexchange.domain.Account;
import org.sanj.demo.currencyexchange.domain.Currency;
import org.sanj.demo.currencyexchange.domain.exceptions.AccountCurrencyException;
import org.sanj.demo.currencyexchange.domain.exceptions.InvalidAmountException;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.ACCOUNT_NUMBER;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.CONVERT_10_PLN_TO_PLN_COMMAND_NOK;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.CONVERT_10_PLN_TO_USD_COMMAND_ANEG_NOK;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.CONVERT_10_PLN_TO_USD_COMMAND_A_NOK;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.CONVERT_10_PLN_TO_USD_COMMAND_BC_OK;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.CONVERT_10_PLN_TO_USD_COMMAND_OK;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.CONVERT_10_PLN_TO_USD_COMMAND_QC_NOK;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.CONVERT_10_USD_TO_PLN_COMMAND_OK;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.CREATE_ACCOUNT_COMMAND_OK;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.OPEN_ACCOUNT_COMMAND_B_NOK;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.OPEN_ACCOUNT_COMMAND_FN_NOK;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.OPEN_ACCOUNT_COMMAND_LN_NOK;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.OPEN_ACCOUNT_COMMAND_OK;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.PLNUSD_RATE;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.UPDATE_NEW_ACCOUNT_BALANCES_COMMAND_OK_PLN_TO_USD;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.UPDATE_USD_ACCOUNT_BALANCES_COMMAND_OK_PLN_TO_USD;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.UPDATE_USD_ACCOUNT_BALANCES_COMMAND_OK_USD_TO_PLN;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.USDPLN_RATE;

@ExtendWith(MockitoExtension.class)
class AccountCommandServiceTest {
  @InjectMocks
  private AccountCommandService accountCommandService;

  @Mock
  private GenerateUniqueAccountNumberPort generateUniqueAccountNumberPort;

  @Mock
  private CreateAccountPort createAccountPort;

  @Mock
  private GetCurrencyPairRatePort getCurrencyPairRatePort;

  @Mock
  private GetAccountPort getAccountPort;

  @Mock
  private UpdateAccountBalancesPort updateAccountBalancesPort;

  @AfterEach
  void tearDown() {
    verifyNoMoreInteractions(generateUniqueAccountNumberPort, createAccountPort, getCurrencyPairRatePort, getAccountPort, updateAccountBalancesPort);
  }

  @Test
  void shouldCreateNewAccountWithProvidedBalance() {
    final Account newEnzoDeSensoAccount = Fixtures.newEnzoDeSensoAccount();
    when(generateUniqueAccountNumberPort.execute()).thenReturn(ACCOUNT_NUMBER);
    when(createAccountPort.execute(CREATE_ACCOUNT_COMMAND_OK)).thenReturn(newEnzoDeSensoAccount);
    final var result = accountCommandService.execute(OPEN_ACCOUNT_COMMAND_OK);
    assertThat(result).isNotBlank().isEqualTo(ACCOUNT_NUMBER);
  }

  @Test
  void shouldThrowMissingFirstNameWhenCreatingNewAccount() {
    assertThatThrownBy(() -> accountCommandService.execute(OPEN_ACCOUNT_COMMAND_FN_NOK))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("First name and last name must not be blank");
  }

  @Test
  void shouldThrowMissingLastNameWhenCreatingNewAccount() {
    assertThatThrownBy(() -> accountCommandService.execute(OPEN_ACCOUNT_COMMAND_LN_NOK))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("First name and last name must not be blank");
  }

  @Test
  void shouldThrowMissingInitialBalanceWhenCreatingNewAccount() {
    when(generateUniqueAccountNumberPort.execute()).thenReturn(ACCOUNT_NUMBER);
    assertThatThrownBy(() -> accountCommandService.execute(OPEN_ACCOUNT_COMMAND_B_NOK))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Initial balance must be greater than zero");
  }

  @Test
  void shouldConvert10PLNToUSD() {
    final Account usdEnzoDeSensoAccount = Fixtures.usdEnzoDeSensoAccount();
    when(getAccountPort.execute(ACCOUNT_NUMBER)).thenReturn(usdEnzoDeSensoAccount);
    when(getCurrencyPairRatePort.execute(Currency.PLN, Currency.USD)).thenReturn(PLNUSD_RATE);
    accountCommandService.execute(CONVERT_10_PLN_TO_USD_COMMAND_OK);
    verify(updateAccountBalancesPort).execute(argThat(command -> ACCOUNT_NUMBER.equals(command.number()) && command.balances().entrySet().stream()
        .allMatch(e -> UPDATE_USD_ACCOUNT_BALANCES_COMMAND_OK_PLN_TO_USD.balances().get(e.getKey()).compareTo(e.getValue()) == 0)));
  }

  @Test
  void shouldConvert10PLNToNewUSDBalance() {
    final Account newEnzoDeSensoAccount = Fixtures.newEnzoDeSensoAccount();
    when(getAccountPort.execute(ACCOUNT_NUMBER)).thenReturn(newEnzoDeSensoAccount);
    when(getCurrencyPairRatePort.execute(Currency.PLN, Currency.USD)).thenReturn(PLNUSD_RATE);
    accountCommandService.execute(CONVERT_10_PLN_TO_USD_COMMAND_OK);
    verify(updateAccountBalancesPort).execute(argThat(command -> ACCOUNT_NUMBER.equals(command.number()) && command.balances().entrySet().stream()
        .allMatch(e -> UPDATE_NEW_ACCOUNT_BALANCES_COMMAND_OK_PLN_TO_USD.balances().get(e.getKey()).compareTo(e.getValue()) == 0)));
  }

  @Test
  void shouldConvert10USDToPLN() {
    final Account usdEnzoDeSensoAccount = Fixtures.usdEnzoDeSensoAccount();
    when(getAccountPort.execute(ACCOUNT_NUMBER)).thenReturn(usdEnzoDeSensoAccount);
    when(getCurrencyPairRatePort.execute(Currency.USD, Currency.PLN)).thenReturn(USDPLN_RATE);
    accountCommandService.execute(CONVERT_10_USD_TO_PLN_COMMAND_OK);
    verify(updateAccountBalancesPort).execute(argThat(command -> ACCOUNT_NUMBER.equals(command.number()) && command.balances().entrySet().stream()
        .allMatch(e -> UPDATE_USD_ACCOUNT_BALANCES_COMMAND_OK_USD_TO_PLN.balances().get(e.getKey()).compareTo(e.getValue()) == 0)));
  }

  @Test
  void shouldThrowMissingAccountCurrencyWhenConverting10USDToPLN() {
    final Account newEnzoDeSensoAccount = Fixtures.newEnzoDeSensoAccount();
    when(getAccountPort.execute(ACCOUNT_NUMBER)).thenReturn(newEnzoDeSensoAccount);
    when(getCurrencyPairRatePort.execute(Currency.USD, Currency.PLN)).thenReturn(USDPLN_RATE);
    assertThatThrownBy(() -> accountCommandService.execute(CONVERT_10_USD_TO_PLN_COMMAND_OK))
        .isInstanceOf(AccountCurrencyException.class)
        .hasMessage("Account has no USD currency");
    verify(updateAccountBalancesPort, never()).execute(any(UpdateAccountBalancesPort.Command.class));
  }

  @Test
  void shouldThrowInsufficientBalanceWhenConverting10PLNToUSD() {
    final Account poorEnzoDeSensoAccount = Fixtures.poorEnzoDeSensoAccount();
    when(getAccountPort.execute(ACCOUNT_NUMBER)).thenReturn(poorEnzoDeSensoAccount);
    when(getCurrencyPairRatePort.execute(Currency.PLN, Currency.USD)).thenReturn(PLNUSD_RATE);
    assertThatThrownBy(() -> accountCommandService.execute(CONVERT_10_PLN_TO_USD_COMMAND_OK))
        .isInstanceOf(InvalidAmountException.class)
        .hasMessage("Invalid PLN balance");
    verify(updateAccountBalancesPort, never()).execute(any(UpdateAccountBalancesPort.Command.class));
  }

  @Test
  void shouldThrowInvalidConversionRateWhenConvertingAmount() {
    final Account poorEnzoDeSensoAccount = Fixtures.poorEnzoDeSensoAccount();
    when(getAccountPort.execute(ACCOUNT_NUMBER)).thenReturn(poorEnzoDeSensoAccount);
    when(getCurrencyPairRatePort.execute(Currency.PLN, Currency.USD)).thenReturn(BigDecimal.valueOf(-0.43));
    assertThatThrownBy(() -> accountCommandService.execute(CONVERT_10_PLN_TO_USD_COMMAND_OK))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Conversion rate must be greater than zero");
    verify(updateAccountBalancesPort, never()).execute(any(UpdateAccountBalancesPort.Command.class));
  }

  @Test
  void shouldThrowSameBaseAndQuoteCurrencyWhenConvertingAmount() {
    assertThatThrownBy(() -> accountCommandService.execute(CONVERT_10_PLN_TO_PLN_COMMAND_NOK))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Base and quote currency cannot be equal");
    verify(getAccountPort, never()).execute(ACCOUNT_NUMBER);
    verify(getCurrencyPairRatePort, never()).execute(Currency.PLN, Currency.USD);
    verify(updateAccountBalancesPort, never()).execute(any(UpdateAccountBalancesPort.Command.class));
  }

  @Test
  void shouldThrowMissingAmountWhenConvertingAmount() {
    assertThatThrownBy(() -> accountCommandService.execute(CONVERT_10_PLN_TO_USD_COMMAND_A_NOK))
        .isInstanceOf(NullPointerException.class);
    verify(getAccountPort, never()).execute(ACCOUNT_NUMBER);
    verify(getCurrencyPairRatePort, never()).execute(Currency.PLN, Currency.USD);
    verify(updateAccountBalancesPort, never()).execute(any(UpdateAccountBalancesPort.Command.class));
  }

  @Test
  void shouldThrowInvalidAmountWhenConvertingAmount() {
    assertThatThrownBy(() -> accountCommandService.execute(CONVERT_10_PLN_TO_USD_COMMAND_ANEG_NOK))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Amount must be greater than zero");
    verify(getAccountPort, never()).execute(ACCOUNT_NUMBER);
    verify(getCurrencyPairRatePort, never()).execute(Currency.PLN, Currency.USD);
    verify(updateAccountBalancesPort, never()).execute(any(UpdateAccountBalancesPort.Command.class));
  }

  @Test
  void shouldThrowMissingBaseCurrencyWhenConvertingAmount() {
    assertThatThrownBy(() -> accountCommandService.execute(CONVERT_10_PLN_TO_USD_COMMAND_BC_OK))
        .isInstanceOf(NullPointerException.class);
    verify(getAccountPort, never()).execute(ACCOUNT_NUMBER);
    verify(getCurrencyPairRatePort, never()).execute(Currency.PLN, Currency.USD);
    verify(updateAccountBalancesPort, never()).execute(any(UpdateAccountBalancesPort.Command.class));
  }

  @Test
  void shouldThrowMissingQuoteCurrencyWhenConvertingAmount() {
    final Account usdEnzoDeSensoAccount = Fixtures.usdEnzoDeSensoAccount();
    when(getAccountPort.execute(ACCOUNT_NUMBER)).thenReturn(usdEnzoDeSensoAccount);
    when(getCurrencyPairRatePort.execute(Currency.PLN, null)).thenThrow(IllegalArgumentException.class);
    assertThatThrownBy(() -> accountCommandService.execute(CONVERT_10_PLN_TO_USD_COMMAND_QC_NOK))
        .isInstanceOf(IllegalArgumentException.class);
    verify(updateAccountBalancesPort, never()).execute(any(UpdateAccountBalancesPort.Command.class));
  }
}