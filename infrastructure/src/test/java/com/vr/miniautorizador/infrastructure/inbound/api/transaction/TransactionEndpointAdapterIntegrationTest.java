package com.vr.miniautorizador.infrastructure.inbound.api.transaction;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vr.miniautorizador.domain.entities.transaction.TransactionStatus;
import com.vr.miniautorizador.domain.ports.in.transaction.CreateTransactionUseCasePort;
import com.vr.miniautorizador.infrastructure.base.util.JwtUtil;
import com.vr.miniautorizador.infrastructure.inbound.api.transaction.dto.TransactionEndpointRequest;
import com.vr.miniautorizador.infrastructure.inbound.api.transaction.mapper.TransactionEndpointMapper;
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
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class TransactionEndpointAdapterIntegrationTest {
  private static final String PATH = "/transacoes";

  @Value("${spring.security.jwt.secret-key}")
  private UUID jwtSecretKey;
  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private CreateTransactionUseCasePort createTransactionUseCasePort;

  @Test
  public void createTransactionWhenIsOk() throws Throwable {
    final var cardNumber = "374245455400126";
    final var cardPassword = "12321";
    final var amount = new BigDecimal("550.25");

    final var request = TransactionEndpointRequest.builder()
        .cardNumber(cardNumber)
        .cardPassword(cardPassword)
        .amount(amount)
        .build();

    Mockito.doReturn(TransactionStatus.CREATED.getText())
        .when(this.createTransactionUseCasePort).execute(TransactionEndpointMapper.toEntity(request));

    this.mockMvc.perform(
            MockMvcRequestBuilders.post(TransactionEndpointAdapterIntegrationTest.PATH)
                .header("Authorization", JwtUtil.generateEncodedJwt(this.jwtSecretKey))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(request))
        )
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.content().string(TransactionStatus.CREATED.getText()))
        .andReturn();
  }

  @Test
  public void createTransactionWhenIsNotOk() throws Throwable {
    final var cardNumber = "374245455400126";
    final var cardPassword = "12321";
    final var amount = new BigDecimal("550.25");

    final var request = TransactionEndpointRequest.builder()
        .cardNumber(cardNumber)
        .cardPassword(cardPassword)
        .amount(amount)
        .build();

    Mockito.doReturn(TransactionStatus.UNEXPECTED_ERROR.getText())
        .when(this.createTransactionUseCasePort).execute(TransactionEndpointMapper.toEntity(request));

    this.mockMvc.perform(
            MockMvcRequestBuilders.post(TransactionEndpointAdapterIntegrationTest.PATH)
                .header("Authorization", JwtUtil.generateEncodedJwt(this.jwtSecretKey))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(request))
        )
        .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
        .andExpect(MockMvcResultMatchers.content().string(TransactionStatus.UNEXPECTED_ERROR.getText()))
        .andReturn();
  }

  @Test
  public void createTransactionWhenCardNumberIsNotFilled() throws Throwable {
    final var cardPassword = "12321";
    final var amount = new BigDecimal("550.25");

    final var request = TransactionEndpointRequest.builder()
        .cardNumber(null)
        .cardPassword(cardPassword)
        .amount(amount)
        .build();

    this.mockMvc.perform(
            MockMvcRequestBuilders.post(TransactionEndpointAdapterIntegrationTest.PATH)
                .header("Authorization", JwtUtil.generateEncodedJwt(this.jwtSecretKey))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(request))
        )
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn();
  }

  @Test
  public void createTransactionWhenCardPasswordIsNotFilled() throws Throwable {
    final var cardNumber = "374245455400126";
    final var amount = new BigDecimal("550.25");

    final var request = TransactionEndpointRequest.builder()
        .cardNumber(cardNumber)
        .cardPassword(null)
        .amount(amount)
        .build();

    this.mockMvc.perform(
            MockMvcRequestBuilders.post(TransactionEndpointAdapterIntegrationTest.PATH)
                .header("Authorization", JwtUtil.generateEncodedJwt(this.jwtSecretKey))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(request))
        )
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn();
  }

  @Test
  public void createTransactionWhenAmountIsNotFilled() throws Throwable {
    final var cardNumber = "374245455400126";
    final var cardPassword = "12321";
    final var amount = new BigDecimal(0);

    final var request = TransactionEndpointRequest.builder()
        .cardNumber(cardNumber)
        .cardPassword(cardPassword)
        .amount(amount)
        .build();

    this.mockMvc.perform(
            MockMvcRequestBuilders.post(TransactionEndpointAdapterIntegrationTest.PATH)
                .header("Authorization", JwtUtil.generateEncodedJwt(this.jwtSecretKey))
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(request))
        )
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andReturn();
  }
}
