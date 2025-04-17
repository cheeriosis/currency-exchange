package org.sanj.demo.currencyexchange.adapters.out.queries;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sanj.demo.currencyexchange.adapters.out.queries.rest.NbpRestClient;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.NBP_RATES_RESPONSE;
import static org.sanj.demo.currencyexchange.domain.Currency.PLN;
import static org.sanj.demo.currencyexchange.domain.Currency.USD;

@ExtendWith(MockitoExtension.class)
class GetCurrencyPairRateAdapterTest {
  @InjectMocks
  private GetCurrencyPairRateAdapter getCurrencyPairRateAdapter;

  @Mock
  private NbpRestClient exchangeRateRestClient;

  @Test
  void shouldReturnPLNUSDRateOnSpecifiedDate() {
    when(exchangeRateRestClient.getRate(USD)).thenReturn(NBP_RATES_RESPONSE);
    final var result = getCurrencyPairRateAdapter.execute(PLN, USD);
    assertThat(result).isNotNull().isInstanceOf(BigDecimal.class).isEqualTo(BigDecimal.valueOf(0.2628));
  }

  @Test
  void shouldReturnUSDPLNRateOnSpecifiedDate() {
    when(exchangeRateRestClient.getRate(USD)).thenReturn(NBP_RATES_RESPONSE);
    final var result = getCurrencyPairRateAdapter.execute(USD, PLN);
    assertThat(result).isNotNull().isInstanceOf(BigDecimal.class).isEqualTo(BigDecimal.valueOf(3.7300));
  }
}
