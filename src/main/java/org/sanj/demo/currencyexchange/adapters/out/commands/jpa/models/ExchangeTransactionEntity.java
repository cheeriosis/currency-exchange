package org.sanj.demo.currencyexchange.adapters.out.commands.jpa.models;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sanj.demo.currencyexchange.domain.Currency;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "exchange_transactions")
public class ExchangeTransactionEntity {
  @Id
  UUID id;
  @NotNull
  @ManyToOne
  @JoinColumn(name = "account_number", referencedColumnName = "number")
  AccountEntity account;
  @NotNull
  @Enumerated(EnumType.STRING)
  Currency fromCurrency;
  @NotNull
  BigDecimal fromAmount;
  @NotNull
  @Enumerated(EnumType.STRING)
  Currency toCurrency;
  @NotNull
  BigDecimal toAmount;
  @NotNull
  BigDecimal rate;
  @NotNull
  @PastOrPresent
  @Builder.Default
  OffsetDateTime occurrenceTime = OffsetDateTime.now();
}
