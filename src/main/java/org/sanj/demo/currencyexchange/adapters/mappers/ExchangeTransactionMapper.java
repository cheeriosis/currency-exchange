package org.sanj.demo.currencyexchange.adapters.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.sanj.demo.currencyexchange.adapters.out.commands.jpa.models.ExchangeTransactionEntity;
import org.sanj.demo.currencyexchange.domain.events.MoneyConvertedEvent;

import java.util.UUID;

@Mapper(uses = {EntityExtractor.class}, imports = {UUID.class})
public interface ExchangeTransactionMapper {
  @Mapping(target = "id", expression = "java(UUID.randomUUID())")
  @Mapping(target = "account", source = "accountNumber")
  @Mapping(target = "fromCurrency", source = "from.currency")
  @Mapping(target = "fromAmount", source = "from.amount")
  @Mapping(target = "toCurrency", source = "to.currency")
  @Mapping(target = "toAmount", source = "to.amount")
  @Mapping(target = "occurrenceTime", source = "timestamp")
  ExchangeTransactionEntity toEntity(MoneyConvertedEvent event);
}
