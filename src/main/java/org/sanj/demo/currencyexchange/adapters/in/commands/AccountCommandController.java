package org.sanj.demo.currencyexchange.adapters.in.commands;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sanj.demo.currencyexchange.application.in.commands.ConvertAmountUseCase;
import org.sanj.demo.currencyexchange.application.in.commands.OpenAccountUseCase;
import org.sanj.demo.currencyexchange.domain.Currency;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.net.URI;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/accounts")
class AccountCommandController {
  OpenAccountUseCase openAccountUseCase;
  ConvertAmountUseCase convertAmountUseCase;

  @Operation(summary = "Open a multi-currency account")
  @ApiResponse(responseCode = "201", description = "Created")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  ResponseEntity<Void> openAccount(@RequestBody @Validated final OpenAccountRequest request) {
    final String number = openAccountUseCase.execute(request.asCommand());
    return ResponseEntity.created(URI.create("/accounts/%s".formatted(number))).build();
  }

  @Operation(summary = "Convert money between provided currencies")
  @ApiResponse(responseCode = "204", description = "No content")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
  @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
  @ApiResponse(responseCode = "422", description = "Unprocessable entity", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
  @PatchMapping("/{number}/balances:exchange")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void exchange(@PathVariable @NotBlank @Size(min = 10, max = 10) @Pattern(regexp = "\\d+") final String number,
                @RequestBody @Validated final ConvertAmountRequest request) {
    convertAmountUseCase.execute(request.asCommand(number));
  }

  record OpenAccountRequest(@NotBlank String firstName, @NotBlank String lastName, @NotNull @Positive BigDecimal initialBalance) {
    public OpenAccountUseCase.Command asCommand() {
      return new OpenAccountUseCase.Command(firstName, lastName, initialBalance);
    }
  }

  record ConvertAmountRequest(@NotNull @Positive BigDecimal amount, @NotNull Currency from, @NotNull Currency to) {
    public ConvertAmountUseCase.Command asCommand(final String number) {
      return new ConvertAmountUseCase.Command(number, amount, from, to);
    }
  }
}
