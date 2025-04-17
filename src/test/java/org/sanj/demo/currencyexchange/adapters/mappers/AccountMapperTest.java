package org.sanj.demo.currencyexchange.adapters.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.sanj.demo.currencyexchange.adapters.out.commands.jpa.models.AccountEntity;
import org.sanj.demo.currencyexchange.domain.Account;
import org.springframework.test.util.ReflectionTestUtils;

import static org.assertj.core.api.Assertions.assertThat;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.CREATE_MULTI_ENZO_DE_SENSO_ACCOUNT_COMMAND_OK;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.CREATE_NEW_ENZO_DE_SENSO_ACCOUNT_COMMAND_OK;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.newEnzoDeSensoAccount;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.newEnzoDeSensoAccountEntity;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.usdEnzoDeSensoAccount;
import static org.sanj.demo.currencyexchange.application.in.services.Fixtures.usdEnzoDeSensoAccountEntity;

class AccountMapperTest {
  private final AccountMapper accountMapper = new AccountMapperImpl();
  private final OwnerMapper ownerMapper = new OwnerMapperImpl();

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(accountMapper, "ownerMapper", ownerMapper);
  }

  @Test
  void shouldMapAccountEntityWithSingleCurrencyToDomain() {
    final var result = accountMapper.toDomain(newEnzoDeSensoAccountEntity());
    assertThat(result).isNotNull().isInstanceOf(Account.class).isEqualTo(newEnzoDeSensoAccount());
  }

  @Test
  void shouldMapAccountEntityWithTwoCurrenciesToDomain() {
    final var result = accountMapper.toDomain(usdEnzoDeSensoAccountEntity());
    assertThat(result).isNotNull().isInstanceOf(Account.class).isEqualTo(usdEnzoDeSensoAccount());
  }

  @Test
  void shouldMapAccountCreationCommandWithSingleCurrencyToEntity() {
    final var result = accountMapper.toEntity(CREATE_NEW_ENZO_DE_SENSO_ACCOUNT_COMMAND_OK);
    assertThat(result).isNotNull().isInstanceOf(AccountEntity.class).usingRecursiveComparison().ignoringFields("creationTime")
        .isEqualTo(newEnzoDeSensoAccountEntity());
  }

  @Test
  void shouldMapAccountCreationCommandWithTwoCurrenciesToEntity() {
    final var result = accountMapper.toEntity(CREATE_MULTI_ENZO_DE_SENSO_ACCOUNT_COMMAND_OK);
    assertThat(result).isNotNull().isInstanceOf(AccountEntity.class).usingRecursiveComparison().ignoringFields("creationTime")
        .isEqualTo(usdEnzoDeSensoAccountEntity());
  }
}