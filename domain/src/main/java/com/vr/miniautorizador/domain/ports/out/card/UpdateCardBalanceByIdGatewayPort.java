package com.vr.miniautorizador.domain.ports.out.card;

import java.math.BigDecimal;
import java.util.UUID;

public interface UpdateCardBalanceByIdGatewayPort {
  Boolean updateCardBalanceById(UUID id, BigDecimal amount);
}
