package com.vr.miniautorizador.infrastructure.inbound.api.card.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardEndpointResponse {
  @JsonProperty("id")
  private UUID id;
  @JsonProperty("numeroCartao")
  private String number;
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
