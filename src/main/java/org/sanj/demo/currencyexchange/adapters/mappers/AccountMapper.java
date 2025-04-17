package org.sanj.demo.currencyexchange.adapters.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.sanj.demo.currencyexchange.adapters.out.commands.jpa.models.AccountEntity;
import org.sanj.demo.currencyexchange.adapters.out.commands.jpa.models.CurrencyBalanceRecord;
import org.sanj.demo.currencyexchange.application.out.commands.CreateAccountPort;
import org.sanj.demo.currencyexchange.domain.Account;
import org.sanj.demo.currencyexchange.domain.Currency;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(uses = {OwnerMapper.class})
public interface AccountMapper {
  @Mapping(target = "owner", source = ".")
  @Mapping(target = "balances", source = "balances", qualifiedByName = "toDomainBalanceMap")
  Account toDomain(AccountEntity entity);

  @Named("toDomainBalanceMap")
  default Map<Currency, BigDecimal> toDomainBalanceMap(final Collection<CurrencyBalanceRecord> balances) {
    return balances.stream().collect(Collectors.toMap(CurrencyBalanceRecord::getCurrency, CurrencyBalanceRecord::getAmount));
  }

  @Mapping(target = "firstName", source = "owner.firstName")
  @Mapping(target = "lastName", source = "owner.lastName")
  @Mapping(target = "balances", source = "balances", qualifiedByName = "toEntityBalanceSet")
  @Mapping(target = "creationTime", ignore = true)
  AccountEntity toEntity(CreateAccountPort.Command command);

  @Named("toEntityBalanceSet")
  default Set<CurrencyBalanceRecord> toEntityBalanceSet(final Map<Currency, BigDecimal> balances) {
    return balances.entrySet().stream()
        .map(entry -> CurrencyBalanceRecord.builder().currency(entry.getKey()).amount(entry.getValue()).build()).collect(Collectors.toSet());
  }
}
