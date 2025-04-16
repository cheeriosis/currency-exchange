package org.sanj.demo.currencyexchange.application.in.commands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.sanj.demo.currencyexchange.domain.Currency;

import java.math.BigDecimal;

public interface ConvertAmountUseCase {
  void execute(Command command);

  record Command(@NotBlank String accountNumber, @NotNull BigDecimal amount, @NotNull Currency from, @NotNull Currency to) {
  }
}
