package com.vr.miniautorizador.infrastructure.base.configuration.usecases;

import com.vr.miniautorizador.application.usecases.card.CreateCardUseCaseAdapter;
import com.vr.miniautorizador.application.usecases.card.GetCardByNumberUseCaseAdapter;
import com.vr.miniautorizador.domain.ports.in.card.CreateCardUseCasePort;
import com.vr.miniautorizador.domain.ports.in.card.GetCardByNumberUseCasePort;
import com.vr.miniautorizador.domain.ports.out.card.CreateCardGatewayPort;
import com.vr.miniautorizador.domain.ports.out.card.GetCardByNumberGatewayPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardUseCasesConfiguration {
  @Bean
  public GetCardByNumberUseCasePort getCardByNumberUseCasePort(
      final GetCardByNumberGatewayPort getCardByNumberGatewayPort
  ) {
    return new GetCardByNumberUseCaseAdapter(
        getCardByNumberGatewayPort
    );
  }

  @Bean
  public CreateCardUseCasePort createCardUseCasePort(
      final GetCardByNumberGatewayPort getCardByNumberGatewayPort,
      final CreateCardGatewayPort createCardGatewayPort
  ) {
    return new CreateCardUseCaseAdapter(
        getCardByNumberGatewayPort,
        createCardGatewayPort
    );
  }
}
