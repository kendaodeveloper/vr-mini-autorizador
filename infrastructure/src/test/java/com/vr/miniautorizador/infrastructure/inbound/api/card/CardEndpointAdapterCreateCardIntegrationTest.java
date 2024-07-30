package com.vr.miniautorizador.infrastructure.inbound.api.card;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.domain.ports.in.card.CreateCardUseCasePort;
import com.vr.miniautorizador.infrastructure.base.util.JwtUtil;
import com.vr.miniautorizador.infrastructure.inbound.api.card.dto.CardEndpointRequest;
import com.vr.miniautorizador.infrastructure.inbound.api.card.mapper.CardEndpointMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class CardEndpointAdapterCreateCardIntegrationTest {
  private static final String USER_PATH = "/cards";

  @Value("${spring.security.jwt.secret-key}")
  private UUID jwtSecretKey;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private CreateCardUseCasePort createCardUseCasePort;

  @Test
  public void createCardWhenIsCreated() throws Throwable {
    final var number = "374245455400126";
    final var password = "12321";
    final var cvv = "901";
    final var expirationDate = "01/25";
    final var ownerName = "João";
    final var balance = new BigDecimal("550.25");

    final var cardRequest = CardEndpointRequest.builder()
        .number(number)
        .password(password)
        .cvv(cvv)
        .expirationDate(expirationDate)
        .ownerName(ownerName)
        .balance(balance)
        .build();

    final var card = Card.builder()
        .id(UUID.randomUUID())
        .number(number)
        .password(password)
        .cvv(cvv)
        .expirationDate(expirationDate)
        .ownerName(ownerName)
        .balance(balance)
        .build();

    final var cardResponse = CardEndpointMapper.toResponse(card);

    Mockito.doReturn(Optional.of(card)).when(this.createCardUseCasePort).execute(CardEndpointMapper.toEntity(cardRequest));

    this.mockMvc.perform(
            MockMvcRequestBuilders.post(CardEndpointAdapterCreateCardIntegrationTest.USER_PATH)
                .header("Authorization", JwtUtil.generateEncodedJwt(this.jwtSecretKey))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(cardRequest))
        )
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().json(
            this.objectMapper.writeValueAsString(cardResponse)
        ))
        .andReturn();
  }
}
