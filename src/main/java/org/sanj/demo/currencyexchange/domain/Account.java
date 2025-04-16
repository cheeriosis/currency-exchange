package org.sanj.demo.currencyexchange.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.sanj.demo.currencyexchange.domain.validators.BalanceValidator;
import org.sanj.demo.currencyexchange.domain.validators.CurrencyAmountValidator;

import java.math.BigDecimal;
import java.util.Map;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@ToString
@EqualsAndHashCode
@Builder
public class Account {
  String number;
  Owner owner;
  Map<Currency, BigDecimal> balances;

  public static Account create(final String number, final Owner owner, final BigDecimal initialBalance) {
    CurrencyAmountValidator.validateInitialBalance(initialBalance);
    return Account.builder().number(number).owner(owner).balances(Map.of(Currency.PLN, initialBalance)).build();
  }

  public static Account load(final String number, final Owner owner, final Map<Currency, BigDecimal> balances) {
    return Account.builder().number(number).owner(owner).balances(balances).build();
  }

  public void moveMoney(final CurrencyAmount from, final CurrencyAmount to) {
    BalanceValidator.validate(this.balances, from, to);
    balances = Map.of(
        from.getCurrency(), balances.get(from.getCurrency()).subtract(from.getAmount()),
        to.getCurrency(), balances.getOrDefault(to.getCurrency(), BigDecimal.valueOf(0)).add(to.getAmount())
    );
  }
}
