package com.vr.miniautorizador.infrastructure.inbound.api.transaction.dto;

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
  private String cardNumber;
  private String cardPassword;
  private BigDecimal value;
}
