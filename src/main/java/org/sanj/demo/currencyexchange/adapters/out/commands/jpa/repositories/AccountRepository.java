package org.sanj.demo.currencyexchange.adapters.out.commands.jpa.repositories;

import org.sanj.demo.currencyexchange.adapters.out.commands.jpa.models.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<AccountEntity, String> {
}
