package org.sanj.demo.currencyexchange.adapters.in.queries;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.sanj.demo.currencyexchange.application.in.queries.GetAccountUseCase;
import org.sanj.demo.currencyexchange.domain.Account;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RestController
@RequestMapping("/accounts")
class AccountQueryController {
  GetAccountUseCase getAccountUseCase;

  @Operation(summary = "Retrieve detailed account information")
  @ApiResponse(responseCode = "200", description = "OK")
  @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
  @ApiResponse(responseCode = "404", description = "Not found", content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
  @GetMapping("/{number}")
  Account getByNumber(@PathVariable @NotBlank @Size(min = 10, max = 10) @Pattern(regexp = "\\d+") final String number) {
    return getAccountUseCase.execute(number);
  }
}
