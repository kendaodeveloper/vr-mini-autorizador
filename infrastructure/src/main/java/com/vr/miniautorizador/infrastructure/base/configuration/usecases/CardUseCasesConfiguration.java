package com.vr.miniautorizador.infrastructure.base.configuration.usecases;

import com.vr.miniautorizador.application.usecases.card.CreateCardUseCaseAdapter;
import com.vr.miniautorizador.domain.ports.in.card.CreateCardUseCasePort;
import com.vr.miniautorizador.domain.ports.out.card.CreateCardGatewayPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CardUseCasesConfiguration {
  @Bean
  public CreateCardUseCasePort createCardUseCasePort(
      final CreateCardGatewayPort createCardGatewayPort
  ) {
    return new CreateCardUseCaseAdapter(
        createCardGatewayPort
    );
  }
}
