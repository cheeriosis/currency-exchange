package org.sanj.demo.currencyexchange.adapters.out.commands.jpa.models;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sanj.demo.currencyexchange.domain.Currency;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Embeddable
public class CurrencyBalanceRecord {
  @NotNull
  @Enumerated(EnumType.STRING)
  Currency currency;
  @NotNull
  BigDecimal amount;
}
