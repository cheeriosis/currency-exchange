package org.sanj.demo.currencyexchange.adapters.out.commands;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.sanj.demo.currencyexchange.application.out.commands.EventPublisherPort;
import org.sanj.demo.currencyexchange.domain.events.DomainEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
@Component
class EventPublisherAdapter implements EventPublisherPort {
  ApplicationEventPublisher applicationEventPublisher;

  @Override
  public void publish(final DomainEvent event) {
    log.info("Publishing event: {}", event);
    applicationEventPublisher.publishEvent(event);
  }

  @Override
  public void publish(final List<DomainEvent> events) {
    if (!Objects.isNull(events)) {
      events.forEach(applicationEventPublisher::publishEvent);
    }
  }
}
