package com.vr.miniautorizador.application.usecases.card;

import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.domain.exceptions.NotFoundException;
import com.vr.miniautorizador.domain.ports.out.card.GetCardByNumberGatewayPort;
import com.vr.miniautorizador.domain.util.PasswordUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public class GetCardByNumberUseCaseAdapterTest {
  @InjectMocks
  private GetCardByNumberUseCaseAdapter getCardByNumberUseCaseAdapter;
  @Mock
  private GetCardByNumberGatewayPort getCardByNumberGatewayPort;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void getCardWhenExists() {
    final var id = UUID.randomUUID();
    final var number = "374245455400126";
    final var password = "12321";
    final var cvv = "901";
    final var expirationDate = "01/25";
    final var ownerName = "João";
    final var balance = new BigDecimal("550.25");
    final var createdAt = LocalDateTime.now().minusHours(5);
    final var updatedAt = LocalDateTime.now();

    final var existingCard = Card.builder()
        .id(id)
        .number(number)
        .password(PasswordUtil.encrypt(password))
        .cvv(cvv)
        .expirationDate(expirationDate)
        .ownerName(ownerName)
        .balance(balance)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();

    Mockito.doReturn(Optional.of(existingCard)).when(this.getCardByNumberGatewayPort).getCardByNumber(number);

    final var response = this.getCardByNumberUseCaseAdapter.execute(number);

    Assertions.assertNotNull(response);
    Assertions.assertEquals(response.getId(), existingCard.getId());
    Assertions.assertEquals(response.getNumber(), existingCard.getNumber());
    Assertions.assertEquals(response.getPassword(), existingCard.getPassword());
    Assertions.assertEquals(response.getCvv(), existingCard.getCvv());
    Assertions.assertEquals(response.getExpirationDate(), existingCard.getExpirationDate());
    Assertions.assertEquals(response.getOwnerName(), existingCard.getOwnerName());
    Assertions.assertEquals(response.getBalance(), existingCard.getBalance());
    Assertions.assertNotNull(response.getCreatedAt());
    Assertions.assertNotNull(response.getUpdatedAt());
  }

  @Test
  public void getCardWhenNotExists() {
    final var number = "374245455400126";

    Mockito.doReturn(Optional.empty()).when(this.getCardByNumberGatewayPort).getCardByNumber(number);

    Assertions.assertThrows(NotFoundException.class, () -> this.getCardByNumberUseCaseAdapter.execute(number));
  }
}
