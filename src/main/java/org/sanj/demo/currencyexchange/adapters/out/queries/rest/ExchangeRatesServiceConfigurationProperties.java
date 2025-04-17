package org.sanj.demo.currencyexchange.adapters.out.queries.rest;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app.out.services.exchange-rates")
record ExchangeRatesServiceConfigurationProperties(String url) {
}
