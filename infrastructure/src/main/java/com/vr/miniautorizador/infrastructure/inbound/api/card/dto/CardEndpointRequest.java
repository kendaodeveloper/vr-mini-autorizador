package com.vr.miniautorizador.infrastructure.inbound.api.card.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CardEndpointRequest {
  @NotNull
  @NotEmpty
  @JsonProperty("numeroCartao")
  private String number;
  @NotNull
  @NotEmpty
  @JsonProperty("senha")
  private String password;
  @JsonProperty("cvv")
  private String cvv;
  @JsonProperty("dataExpiracao")
  private String expirationDate;
  @JsonProperty("nomeDono")
  private String ownerName;
  @JsonProperty("saldo")
  private BigDecimal balance;
}
