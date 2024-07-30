package com.vr.miniautorizador.infrastructure.outbound.database.card;

import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.infrastructure.outbound.database.card.repository.CardRepository;
import com.vr.miniautorizador.infrastructure.outbound.database.card.table.CardTable;
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

public class CardDatabaseGatewayAdapterCreateCardTest {
  @InjectMocks
  private CardDatabaseGatewayAdapter adapter;
  @Mock
  private CardRepository cardRepository;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void createCardWhenIsSuccess() {
    final var id = UUID.randomUUID();
    final var number = "374245455400126";
    final var password = "12321";
    final var cvv = "901";
    final var expirationDate = "01/25";
    final var balance = new BigDecimal("550.25");
    final var createdAt = LocalDateTime.now().minusHours(5);
    final var updatedAt = LocalDateTime.now();

    final var card = Card.builder()
        .id(id)
        .number(number)
        .password(password)
        .cvv(cvv)
        .expirationDate(expirationDate)
        .balance(balance)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();

    Mockito.doNothing().when(cardRepository).saveAndFlush(Mockito.any(CardTable.class));

    final var response = this.adapter.createCard(card);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(response.getId(), card.getId());
    Assertions.assertEquals(response.getNumber(), card.getNumber());
    Assertions.assertEquals(response.getPassword(), card.getPassword());
    Assertions.assertEquals(response.getCvv(), card.getCvv());
    Assertions.assertEquals(response.getExpirationDate(), card.getExpirationDate());
    Assertions.assertEquals(response.getBalance(), card.getBalance());
    Assertions.assertEquals(response.getCreatedAt(), card.getCreatedAt());
    Assertions.assertNull(response.getUpdatedAt());
  }
}
