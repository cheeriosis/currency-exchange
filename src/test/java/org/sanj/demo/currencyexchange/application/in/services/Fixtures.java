package org.sanj.demo.currencyexchange.application.in.services;

import org.sanj.demo.currencyexchange.application.in.commands.ConvertAmountUseCase;
import org.sanj.demo.currencyexchange.application.in.commands.OpenAccountUseCase;
import org.sanj.demo.currencyexchange.application.out.commands.CreateAccountPort;
import org.sanj.demo.currencyexchange.application.out.commands.UpdateAccountBalancesPort;
import org.sanj.demo.currencyexchange.domain.Account;
import org.sanj.demo.currencyexchange.domain.Currency;
import org.sanj.demo.currencyexchange.domain.Owner;

import java.math.BigDecimal;
import java.util.Map;

public class Fixtures {
  public static final String ACCOUNT_NUMBER = "9078563412";
  public static final String FIRST_NAME_ENZO = "Enzo";
  public static final String LAST_NAME_DE_SENSO = "De Senso";
  public static final BigDecimal INITIAL_BALANCE_23 = BigDecimal.valueOf(23.0);
  public static final Map<Currency, BigDecimal> INITIAL_BALANCE_MAP = Map.of(Currency.PLN, INITIAL_BALANCE_23);

  public static final Owner ENZO_DE_SENSO = Owner.builder().firstName(FIRST_NAME_ENZO).lastName(LAST_NAME_DE_SENSO).build();

  public static final OpenAccountUseCase.Command OPEN_ACCOUNT_COMMAND_OK = new OpenAccountUseCase.Command(FIRST_NAME_ENZO, LAST_NAME_DE_SENSO,
      INITIAL_BALANCE_23);
  public static final OpenAccountUseCase.Command OPEN_ACCOUNT_COMMAND_FN_NOK = new OpenAccountUseCase.Command("", LAST_NAME_DE_SENSO,
      INITIAL_BALANCE_23);
  public static final OpenAccountUseCase.Command OPEN_ACCOUNT_COMMAND_LN_NOK = new OpenAccountUseCase.Command(FIRST_NAME_ENZO, null,
      INITIAL_BALANCE_23);
  public static final OpenAccountUseCase.Command OPEN_ACCOUNT_COMMAND_B_NOK = new OpenAccountUseCase.Command(FIRST_NAME_ENZO, LAST_NAME_DE_SENSO,
      BigDecimal.valueOf(-3));

  public static final CreateAccountPort.Command CREATE_ACCOUNT_COMMAND_OK = new CreateAccountPort.Command(ACCOUNT_NUMBER, ENZO_DE_SENSO,
      INITIAL_BALANCE_MAP);

  // recreate for tests that change the balances map
  public static Account newEnzoDeSensoAccount() {
    return Account.builder().number(ACCOUNT_NUMBER).owner(ENZO_DE_SENSO).balances(INITIAL_BALANCE_MAP).build();
  }

  public static Account usdEnzoDeSensoAccount() {
    return Account.builder().number(ACCOUNT_NUMBER).owner(ENZO_DE_SENSO)
        .balances(Map.of(
            Currency.PLN, INITIAL_BALANCE_23,
            Currency.USD, BigDecimal.valueOf(45)
        ))
        .build();
  }

  public static Account poorEnzoDeSensoAccount() {
    return Account.builder().number(ACCOUNT_NUMBER).owner(ENZO_DE_SENSO).balances(Map.of(Currency.PLN, BigDecimal.valueOf(4))).build();
  }

  public static final BigDecimal CONVERT_AMOUNT = BigDecimal.TEN;
  public static final BigDecimal USDPLN_RATE = BigDecimal.valueOf(3.761);
  public static final BigDecimal PLNUSD_RATE = BigDecimal.valueOf(0.2659);

  public static final ConvertAmountUseCase.Command CONVERT_10_PLN_TO_USD_COMMAND_OK = new ConvertAmountUseCase.Command(ACCOUNT_NUMBER, CONVERT_AMOUNT,
      Currency.PLN, Currency.USD);
  public static final ConvertAmountUseCase.Command CONVERT_10_PLN_TO_USD_COMMAND_A_NOK = new ConvertAmountUseCase.Command(ACCOUNT_NUMBER, null,
      Currency.PLN, Currency.USD);
  public static final ConvertAmountUseCase.Command CONVERT_10_PLN_TO_USD_COMMAND_ANEG_NOK = new ConvertAmountUseCase.Command(ACCOUNT_NUMBER,
      BigDecimal.valueOf(-5), Currency.PLN, Currency.USD);
  public static final ConvertAmountUseCase.Command CONVERT_10_PLN_TO_USD_COMMAND_BC_OK = new ConvertAmountUseCase.Command(ACCOUNT_NUMBER,
      CONVERT_AMOUNT, null, Currency.USD);
  public static final ConvertAmountUseCase.Command CONVERT_10_PLN_TO_USD_COMMAND_QC_NOK = new ConvertAmountUseCase.Command(ACCOUNT_NUMBER,
      CONVERT_AMOUNT, Currency.PLN, null);
  public static final ConvertAmountUseCase.Command CONVERT_10_PLN_TO_PLN_COMMAND_NOK = new ConvertAmountUseCase.Command(ACCOUNT_NUMBER,
      CONVERT_AMOUNT,
      Currency.PLN, Currency.PLN);
  public static final ConvertAmountUseCase.Command CONVERT_10_USD_TO_PLN_COMMAND_OK = new ConvertAmountUseCase.Command(ACCOUNT_NUMBER, CONVERT_AMOUNT,
      Currency.USD, Currency.PLN);

  public static final Map<Currency, BigDecimal> NEW_ENZO_DE_SENSO_BALANCE_MAP_AFTER_PLN_TO_USD_CONVERSION = Map.of(
      Currency.PLN, BigDecimal.valueOf(13.0),
      Currency.USD, BigDecimal.valueOf(2.659));
  public static final Map<Currency, BigDecimal> USD_ENZO_DE_SENSO_BALANCE_MAP_AFTER_PLN_TO_USD_CONVERSION = Map.of(
      Currency.PLN, BigDecimal.valueOf(13.0),
      Currency.USD, BigDecimal.valueOf(47.659));
  public static final Map<Currency, BigDecimal> USD_ENZO_DE_SENSO_BALANCE_MAP_AFTER_USD_TO_PLN_CONVERSION = Map.of(
      Currency.PLN, BigDecimal.valueOf(60.61),
      Currency.USD, BigDecimal.valueOf(35));

  public static final UpdateAccountBalancesPort.Command UPDATE_NEW_ACCOUNT_BALANCES_COMMAND_OK_PLN_TO_USD = new UpdateAccountBalancesPort.Command(
      ACCOUNT_NUMBER, NEW_ENZO_DE_SENSO_BALANCE_MAP_AFTER_PLN_TO_USD_CONVERSION);
  public static final UpdateAccountBalancesPort.Command UPDATE_USD_ACCOUNT_BALANCES_COMMAND_OK_PLN_TO_USD = new UpdateAccountBalancesPort.Command(
      ACCOUNT_NUMBER, USD_ENZO_DE_SENSO_BALANCE_MAP_AFTER_PLN_TO_USD_CONVERSION);
  public static final UpdateAccountBalancesPort.Command UPDATE_USD_ACCOUNT_BALANCES_COMMAND_OK_USD_TO_PLN = new UpdateAccountBalancesPort.Command(
      ACCOUNT_NUMBER, USD_ENZO_DE_SENSO_BALANCE_MAP_AFTER_USD_TO_PLN_CONVERSION);
}
