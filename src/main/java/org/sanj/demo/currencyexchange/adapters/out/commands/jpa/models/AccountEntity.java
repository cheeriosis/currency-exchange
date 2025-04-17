package org.sanj.demo.currencyexchange.adapters.out.commands.jpa.models;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity(name = "accounts")
public class AccountEntity {
  @Id
  String number;
  @NotBlank
  String firstName;
  @NotBlank
  String lastName;
  @NotNull
  @PastOrPresent
  @Builder.Default
  OffsetDateTime creationTime = OffsetDateTime.now();
  @Builder.Default
  @ElementCollection
  @CollectionTable(name = "currency_balances", joinColumns = @JoinColumn(name = "account_number"))
  Set<CurrencyBalanceRecord> balances = new HashSet<>();
}
