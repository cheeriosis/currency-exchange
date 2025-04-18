package org.sanj.demo.currencyexchange.adapters.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import org.sanj.demo.currencyexchange.adapters.out.commands.jpa.models.AccountEntity;

@Mapper(uses = {ReferenceMapper.class}, builder = @Builder(disableBuilder = true))
public interface EntityExtractor {
  @BeanMapping(ignoreByDefault = true)
  AccountEntity toEntity(String number);
}
