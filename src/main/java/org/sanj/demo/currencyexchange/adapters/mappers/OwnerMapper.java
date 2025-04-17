package org.sanj.demo.currencyexchange.adapters.mappers;

import org.mapstruct.Mapper;
import org.sanj.demo.currencyexchange.adapters.out.commands.jpa.models.AccountEntity;
import org.sanj.demo.currencyexchange.domain.Owner;

@Mapper
public interface OwnerMapper {
  Owner toDomain(AccountEntity entity);
}
