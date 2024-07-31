package com.vr.miniautorizador.infrastructure.inbound.api.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
  @NotNull
  @NotEmpty
  @JsonProperty("numeroCartao")
  private String cardNumber;
  @NotNull
  @NotEmpty
  @JsonProperty("senhaCartao")
  private String cardPassword;
  @NotNull
  @DecimalMin(value = "0.01")
  @JsonProperty("valor")
  private BigDecimal amount;
}
