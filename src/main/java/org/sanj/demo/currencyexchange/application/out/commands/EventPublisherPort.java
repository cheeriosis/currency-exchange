package org.sanj.demo.currencyexchange.application.out.commands;

import org.sanj.demo.currencyexchange.domain.events.DomainEvent;

import java.util.List;

public interface EventPublisherPort {
  void publish(DomainEvent event);

  void publish(List<DomainEvent> events);
}
