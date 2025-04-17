package org.sanj.demo.currencyexchange.adapters.out.queries;

import org.junit.jupiter.api.Test;
import org.sanj.demo.currencyexchange.application.out.queries.GenerateUniqueAccountNumberPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GenerateUniqueAccountNumberAdapterIT {
  @Autowired
  private GenerateUniqueAccountNumberPort generateUniqueAccountNumberPort;

  @Test
  void shouldGenerateUniqueAccountNumber() {
    final var result = generateUniqueAccountNumberPort.execute();
    assertThat(result).isNotNull().isInstanceOf(String.class).containsOnlyDigits();
  }
}