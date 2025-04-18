package org.sanj.demo.currencyexchange.adapters.mappers;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.constraints.NotNull;
import org.mapstruct.ObjectFactory;
import org.mapstruct.TargetType;
import org.springframework.stereotype.Component;

@Component
public class ReferenceMapper {
  @PersistenceContext
  private EntityManager entityManager;

  @ObjectFactory
  public <T> T map(@NotNull final String id, @TargetType final Class<T> type) {
    return entityManager.getReference(type, id);
  }
}
