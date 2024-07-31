package com.vr.miniautorizador.infrastructure.inbound.api.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionEndpointRequest {
  @Schema(example = "6549873025634501")
  @Size(max = 25)
  @NotNull
  @NotEmpty
  @JsonProperty("numeroCartao")
  private String cardNumber;
  @Schema(example = "1234")
  @Size(max = 10)
  @NotNull
  @NotEmpty
  @JsonProperty("senhaCartao")
  private String cardPassword;
  @Schema(example = "25.75")
  @NotNull
  @DecimalMin(value = "0.01")
  @JsonProperty("valor")
  private BigDecimal amount;
}
