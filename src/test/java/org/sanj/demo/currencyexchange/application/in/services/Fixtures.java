package org.sanj.demo.currencyexchange.application.in.services;

import org.sanj.demo.currencyexchange.adapters.out.commands.jpa.models.AccountEntity;
import org.sanj.demo.currencyexchange.adapters.out.commands.jpa.models.CurrencyBalanceRecord;
import org.sanj.demo.currencyexchange.adapters.out.queries.rest.NbpRestClient;
import org.sanj.demo.currencyexchange.application.in.commands.ConvertAmountUseCase;
import org.sanj.demo.currencyexchange.application.in.commands.OpenAccountUseCase;
import org.sanj.demo.currencyexchange.application.out.commands.CreateAccountPort;
import org.sanj.demo.currencyexchange.application.out.commands.UpdateAccountBalancesPort;
import org.sanj.demo.currencyexchange.domain.Account;
import org.sanj.demo.currencyexchange.domain.Currency;
import org.sanj.demo.currencyexchange.domain.Owner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

import static org.sanj.demo.currencyexchange.domain.Currency.PLN;
import static org.sanj.demo.currencyexchange.domain.Currency.USD;

public class Fixtures {
  public static final String ACCOUNT_NUMBER = "9078563412";
  public static final String FIRST_NAME_ENZO = "Enzo";
  public static final String LAST_NAME_DE_SENSO = "De Senso";
  public static final BigDecimal INITIAL_BALANCE_23 = BigDecimal.valueOf(23.0);
  public static final BigDecimal BALANCE_45 = BigDecimal.valueOf(45);
  public static final Map<Currency, BigDecimal> INITIAL_BALANCE_MAP = Map.of(PLN, INITIAL_BALANCE_23);

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
            PLN, INITIAL_BALANCE_23,
            USD, BALANCE_45
        ))
        .build();
  }

  public static Account poorEnzoDeSensoAccount() {
    return Account.builder().number(ACCOUNT_NUMBER).owner(ENZO_DE_SENSO).balances(Map.of(PLN, BigDecimal.valueOf(4))).build();
  }

  public static final BigDecimal CONVERT_AMOUNT = BigDecimal.TEN;
  public static final BigDecimal USDPLN_RATE = BigDecimal.valueOf(3.761);
  public static final BigDecimal PLNUSD_RATE = BigDecimal.valueOf(0.2659);

  public static final ConvertAmountUseCase.Command CONVERT_10_PLN_TO_USD_COMMAND_OK = new ConvertAmountUseCase.Command(ACCOUNT_NUMBER, CONVERT_AMOUNT,
      PLN, USD);
  public static final ConvertAmountUseCase.Command CONVERT_10_PLN_TO_USD_COMMAND_A_NOK = new ConvertAmountUseCase.Command(ACCOUNT_NUMBER, null,
      PLN, USD);
  public static final ConvertAmountUseCase.Command CONVERT_10_PLN_TO_USD_COMMAND_ANEG_NOK = new ConvertAmountUseCase.Command(ACCOUNT_NUMBER,
      BigDecimal.valueOf(-5), PLN, USD);
  public static final ConvertAmountUseCase.Command CONVERT_10_PLN_TO_USD_COMMAND_BC_OK = new ConvertAmountUseCase.Command(ACCOUNT_NUMBER,
      CONVERT_AMOUNT, null, USD);
  public static final ConvertAmountUseCase.Command CONVERT_10_PLN_TO_USD_COMMAND_QC_NOK = new ConvertAmountUseCase.Command(ACCOUNT_NUMBER,
      CONVERT_AMOUNT, PLN, null);
  public static final ConvertAmountUseCase.Command CONVERT_10_PLN_TO_PLN_COMMAND_NOK = new ConvertAmountUseCase.Command(ACCOUNT_NUMBER,
      CONVERT_AMOUNT,
      PLN, PLN);
  public static final ConvertAmountUseCase.Command CONVERT_10_USD_TO_PLN_COMMAND_OK = new ConvertAmountUseCase.Command(ACCOUNT_NUMBER, CONVERT_AMOUNT,
      USD, PLN);

  public static final Map<Currency, BigDecimal> NEW_ENZO_DE_SENSO_BALANCE_MAP_AFTER_PLN_TO_USD_CONVERSION = Map.of(
      PLN, BigDecimal.valueOf(13.0),
      USD, BigDecimal.valueOf(2.659));
  public static final Map<Currency, BigDecimal> USD_ENZO_DE_SENSO_BALANCE_MAP_AFTER_PLN_TO_USD_CONVERSION = Map.of(
      PLN, BigDecimal.valueOf(13.0),
      USD, BigDecimal.valueOf(47.659));
  public static final Map<Currency, BigDecimal> USD_ENZO_DE_SENSO_BALANCE_MAP_AFTER_USD_TO_PLN_CONVERSION = Map.of(
      PLN, BigDecimal.valueOf(60.61),
      USD, BigDecimal.valueOf(35));

  public static final UpdateAccountBalancesPort.Command UPDATE_NEW_ACCOUNT_BALANCES_COMMAND_OK_PLN_TO_USD = new UpdateAccountBalancesPort.Command(
      ACCOUNT_NUMBER, NEW_ENZO_DE_SENSO_BALANCE_MAP_AFTER_PLN_TO_USD_CONVERSION);
  public static final UpdateAccountBalancesPort.Command UPDATE_USD_ACCOUNT_BALANCES_COMMAND_OK_PLN_TO_USD = new UpdateAccountBalancesPort.Command(
      ACCOUNT_NUMBER, USD_ENZO_DE_SENSO_BALANCE_MAP_AFTER_PLN_TO_USD_CONVERSION);
  public static final UpdateAccountBalancesPort.Command UPDATE_USD_ACCOUNT_BALANCES_COMMAND_OK_USD_TO_PLN = new UpdateAccountBalancesPort.Command(
      ACCOUNT_NUMBER, USD_ENZO_DE_SENSO_BALANCE_MAP_AFTER_USD_TO_PLN_CONVERSION);


  public static CurrencyBalanceRecord plnBalanceRecord() {
    return CurrencyBalanceRecord.builder().currency(PLN).amount(INITIAL_BALANCE_23).build();
  }

  public static CurrencyBalanceRecord usdBalanceRecord() {
    return CurrencyBalanceRecord.builder().currency(USD).amount(BALANCE_45).build();
  }

  public static AccountEntity newEnzoDeSensoAccountEntity() {
    return AccountEntity.builder().number(ACCOUNT_NUMBER).firstName(FIRST_NAME_ENZO).lastName(LAST_NAME_DE_SENSO).balances(Set.of(plnBalanceRecord()))
        .build();
  }

  public static AccountEntity usdEnzoDeSensoAccountEntity() {
    return AccountEntity.builder().number(ACCOUNT_NUMBER).firstName(FIRST_NAME_ENZO).lastName(LAST_NAME_DE_SENSO)
        .balances(Set.of(plnBalanceRecord(), usdBalanceRecord()))
        .build();
  }

  public static final CreateAccountPort.Command CREATE_NEW_ENZO_DE_SENSO_ACCOUNT_COMMAND_OK = new CreateAccountPort.Command(ACCOUNT_NUMBER,
      ENZO_DE_SENSO, INITIAL_BALANCE_MAP);
  public static final CreateAccountPort.Command CREATE_MULTI_ENZO_DE_SENSO_ACCOUNT_COMMAND_OK = new CreateAccountPort.Command(ACCOUNT_NUMBER,
      ENZO_DE_SENSO, Map.of(
      PLN, INITIAL_BALANCE_23,
      USD, BALANCE_45
  ));

  public static final NbpRestClient.Response NBP_RATES_RESPONSE = new NbpRestClient.Response("C", "dolar amerykański", "USD",
      Set.of(new NbpRestClient.RateEntry("076/C/NBP/2025", LocalDate.of(2025, 4, 18), BigDecimal.valueOf(3.7300), BigDecimal.valueOf(3.8054), null)));
}
