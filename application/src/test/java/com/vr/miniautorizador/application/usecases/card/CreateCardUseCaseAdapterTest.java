package com.vr.miniautorizador.application.usecases.card;

import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.domain.ports.out.card.CreateCardGatewayPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class CreateCardUseCaseAdapterTest {
  @InjectMocks
  private CreateCardUseCaseAdapter createCardUseCaseAdapter;
  @Mock
  private CreateCardGatewayPort createCardGatewayPort;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void createCardWithSuccess() {
    final var id = UUID.randomUUID();
    final var number = "374245455400126";
    final var password = "12321";
    final var cvv = "901";
    final var expirationDate = "01/25";
    final var balance = new BigDecimal("550.25");
    final var createdAt = LocalDateTime.now().minusHours(5);
    final var updatedAt = LocalDateTime.now();

    final var requestedCard = Card.builder()
        .id(null)
        .number(number)
        .password(password)
        .cvv(cvv)
        .expirationDate(expirationDate)
        .balance(balance)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();

    final var persistedCard = Card.builder()
        .id(UUID.randomUUID())
        .number(number)
        .password(password)
        .cvv(cvv)
        .expirationDate(expirationDate)
        .balance(balance)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();

    Mockito.doReturn(persistedCard).when(this.createCardGatewayPort).createCard(requestedCard);

    final var response = this.createCardUseCaseAdapter.execute(requestedCard);

    Assertions.assertNotNull(response);
  }
}
