package org.sanj.demo.currencyexchange.domain;

import lombok.Builder;
import lombok.Value;
import org.sanj.demo.currencyexchange.domain.validators.OwnerValidator;

@Value
@Builder
public class Owner {
  String firstName;
  String lastName;

  public static Owner create(final String firstName, final String lastName) {
    OwnerValidator.validate(firstName, lastName);
    return Owner.builder().firstName(firstName).lastName(lastName).build();
  }

  public static Owner load(final String firstName, final String lastName) {
    return Owner.builder().firstName(firstName).lastName(lastName).build();
  }
}
