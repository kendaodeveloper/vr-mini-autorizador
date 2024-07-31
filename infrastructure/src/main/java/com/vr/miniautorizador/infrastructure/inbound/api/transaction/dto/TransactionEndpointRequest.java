package com.vr.miniautorizador.infrastructure.inbound.api.transaction.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
  @JsonProperty("numeroCartao")
  private String cardNumber;
  @JsonProperty("senhaCartao")
  private String cardPassword;
  @JsonProperty("valor")
  private BigDecimal amount;
}
