package com.vr.miniautorizador.infrastructure.inbound.api.card.dto;

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
  private UUID id;
  private String number;
  private String password;
  private String cvv;
  private String expirationDate;
  private String ownerName;
  private BigDecimal balance;
}
