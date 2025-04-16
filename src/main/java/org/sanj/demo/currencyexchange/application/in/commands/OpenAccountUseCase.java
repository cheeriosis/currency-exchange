package org.sanj.demo.currencyexchange.application.in.commands;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public interface OpenAccountUseCase {
  String execute(Command command);

  record Command(@NotBlank String firstName, @NotBlank String lastName, @NotNull @Positive BigDecimal initialBalance) {
  }
}
