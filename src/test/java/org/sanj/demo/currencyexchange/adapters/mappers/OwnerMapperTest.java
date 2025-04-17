package org.sanj.demo.currencyexchange.adapters.mappers;

import org.junit.jupiter.api.Test;
import org.sanj.demo.currencyexchange.domain.Owner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.ENZO_DE_SENSO;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.newEnzoDeSensoAccountEntity;

class OwnerMapperTest {
  private final OwnerMapper ownerMapper = new OwnerMapperImpl();

  @Test
  void shouldMapAccountEntityToDomain() {
    final var result = ownerMapper.toDomain(newEnzoDeSensoAccountEntity());
    assertThat(result).isNotNull().isInstanceOf(Owner.class).isEqualTo(ENZO_DE_SENSO);
  }
}