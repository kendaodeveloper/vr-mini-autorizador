package com.vr.miniautorizador.infrastructure.base.configuration.usecases;

import com.vr.miniautorizador.application.usecases.transaction.CreateTransactionUseCaseAdapter;
import com.vr.miniautorizador.domain.ports.in.transaction.CreateTransactionUseCasePort;
import com.vr.miniautorizador.domain.ports.out.card.GetCardByNumberGatewayPort;
import com.vr.miniautorizador.domain.ports.out.card.UpdateCardBalanceByIdGatewayPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionUseCasesConfiguration {
  @Bean
  public CreateTransactionUseCasePort createTransactionUseCasePort(
      final GetCardByNumberGatewayPort getCardByNumberGatewayPort,
      final UpdateCardBalanceByIdGatewayPort updateCardBalanceByIdGatewayPort
  ) {
    return new CreateTransactionUseCaseAdapter(
        getCardByNumberGatewayPort,
        updateCardBalanceByIdGatewayPort
    );
  }
}
