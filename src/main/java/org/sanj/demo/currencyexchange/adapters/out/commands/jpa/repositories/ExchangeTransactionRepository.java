package org.sanj.demo.currencyexchange.adapters.out.commands.jpa.repositories;

import org.sanj.demo.currencyexchange.adapters.out.commands.jpa.models.ExchangeTransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ExchangeTransactionRepository extends JpaRepository<ExchangeTransactionEntity, UUID> {
}
