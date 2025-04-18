package org.sanj.demo.currencyexchange.adapters.out;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Component
class MidnightCacheEvictor {
  CacheManager cacheManager;

  @Scheduled(cron = "0 0 0 * * *")
  void evict() {
    final var exchangeRatesCache = cacheManager.getCache("exchangeRates");

    if (Objects.nonNull(exchangeRatesCache)) {
      exchangeRatesCache.clear();
      log.info("Cleared `exchangeRates` cache");
    }
  }
}
