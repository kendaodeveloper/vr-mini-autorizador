package com.vr.miniautorizador.infrastructure.inbound.api.card.dto;

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
  private String number;
  private String password;
  private String cvv;
  private String expirationDate;
  private String ownerName;
  private BigDecimal balance;
}
