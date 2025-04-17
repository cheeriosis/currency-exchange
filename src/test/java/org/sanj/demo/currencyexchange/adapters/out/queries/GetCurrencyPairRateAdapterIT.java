package org.sanj.demo.currencyexchange.adapters.out.queries;

import org.junit.jupiter.api.Test;
import org.sanj.demo.currencyexchange.application.out.queries.GetCurrencyPairRatePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sanj.demo.currencyexchange.domain.Currency.PLN;
import static org.sanj.demo.currencyexchange.domain.Currency.USD;

@SpringBootTest
class GetCurrencyPairRateAdapterIT {
  @Autowired
  private GetCurrencyPairRatePort getCurrencyPairRatePort;

  @Test
  void shouldReturnPLNUSDRateOnSpecifiedDate() {
    final var result = getCurrencyPairRatePort.execute(PLN, USD);
    assertThat(result).isNotNull().isInstanceOf(BigDecimal.class);
  }

  @Test
  void shouldReturnUSDPLNRateOnSpecifiedDate() {
    final var result = getCurrencyPairRatePort.execute(USD, PLN);
    assertThat(result).isNotNull().isInstanceOf(BigDecimal.class);
  }
}