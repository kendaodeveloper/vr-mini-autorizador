package com.vr.miniautorizador.domain.ports.out.card;

import java.math.BigDecimal;
import java.util.UUID;

public interface UpdateCardBalanceByIdGatewayPort {
  void updateCardBalanceById(UUID id, BigDecimal balance);
}
