package org.sanj.demo.currencyexchange.adapters.out.queries;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sanj.demo.currencyexchange.adapters.out.queries.rest.NbpRestClient;
import org.sanj.demo.currencyexchange.application.out.queries.GetCurrencyPairRatePort;
import org.sanj.demo.currencyexchange.domain.Currency;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Component
class GetCurrencyPairRateAdapter implements GetCurrencyPairRatePort {
  NbpRestClient exchangeRateRestClient;

  @Override
  public BigDecimal execute(final Currency from, final Currency to) {
    if (Currency.PLN.equals(from)) {
      return exchangeRateRestClient.getRate(to).rates().stream().findFirst().map(NbpRestClient.RateEntry::ask)
          .map(ask -> BigDecimal.ONE.divide(ask, 4, RoundingMode.HALF_UP))
          .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not retrieve currency rate"));
    }

    return exchangeRateRestClient.getRate(from).rates().stream().findFirst().map(NbpRestClient.RateEntry::bid)
        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not retrieve currency rate"));
  }
}
