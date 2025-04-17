package org.sanj.demo.currencyexchange.adapters.out.queries;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.sanj.demo.currencyexchange.adapters.out.commands.jpa.repositories.AccountRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GenerateUniqueAccountNumberAdapterTest {
  @InjectMocks
  private GenerateUniqueAccountNumberAdapter generateUniqueAccountNumberAdapter;

  @Mock
  private AccountRepository accountRepository;

  @Test
  void shouldRetryTwiceWhenGeneratedNumberExists() {
    when(accountRepository.existsById(any())).thenReturn(true).thenReturn(true).thenReturn(false);
    final var result = generateUniqueAccountNumberAdapter.execute();
    assertThat(result).isNotNull().isInstanceOf(String.class).containsOnlyDigits();
    verify(accountRepository, times(3)).existsById(any());
  }
}