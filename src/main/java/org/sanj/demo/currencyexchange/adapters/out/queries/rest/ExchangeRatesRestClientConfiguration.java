package org.sanj.demo.currencyexchange.adapters.out.queries.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.nio.charset.StandardCharsets;

@Slf4j
@Configuration
public class ExchangeRatesRestClientConfiguration {
  @Bean
  NbpRestClient exchangeRatesRestClient(final ExchangeRatesServiceConfigurationProperties exchangeRatesServiceConfigurationProperties) {
    return HttpServiceProxyFactory
        .builderFor(RestClientAdapter.create(RestClient.builder()
            .baseUrl(exchangeRatesServiceConfigurationProperties.url())
            .requestInterceptor((request, body, execution) -> {
              log.info("Calling: {} {}", request.getMethod().name(), request.getURI());
              return execution.execute(request, body);
            })
            .defaultStatusHandler(HttpStatusCode::is2xxSuccessful,
                (request, response) -> log.info("Responded with: {}", response.getStatusCode()))
            .defaultStatusHandler(HttpStatusCode::is4xxClientError, (request, response) -> {
              log.error("Service encountered an issue: {} {}", response.getStatusCode(),
                  new String(response.getBody().readAllBytes(), StandardCharsets.UTF_8));
              throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
            })
            .defaultStatusHandler(HttpStatusCode::is5xxServerError, (request, response) -> {
              log.error("Service is unavailable: {}", response.getStatusCode());
              throw new ResponseStatusException(HttpStatus.BAD_GATEWAY);
            })
            .build()))
        .build().createClient(NbpRestClient.class);
  }
}
