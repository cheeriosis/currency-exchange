package org.sanj.demo.currencyexchange.domain.validators;

import lombok.experimental.UtilityClass;

@UtilityClass
public class OwnerValidator {
  public static void validate(final String firstName, final String lastName) {
    if (isBlank(firstName) || isBlank(lastName)) {
      throw new IllegalArgumentException("First name and last name must not be blank");
    }
  }

  private boolean isBlank(final String value) {
    return value == null || value.isBlank();
  }
}
