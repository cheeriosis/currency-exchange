package org.sanj.demo.currencyexchange.adapters.out.queries.rest;

import org.sanj.demo.currencyexchange.domain.Currency;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;

public interface NbpRestClient {
  @GetExchange("/C/{code}")
  Response getRate(@PathVariable final Currency code);

  record Response(String table, String currency, String code, Collection<RateEntry> rates) {
  }

  record RateEntry(String no, LocalDate effectiveDate, BigDecimal bid, BigDecimal ask, BigDecimal mid) {
  }
}
