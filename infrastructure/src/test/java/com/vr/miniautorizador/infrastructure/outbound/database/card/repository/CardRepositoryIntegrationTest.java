package com.vr.miniautorizador.infrastructure.outbound.database.card.repository;

import com.vr.miniautorizador.infrastructure.outbound.database.card.table.CardTable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
public class CardRepositoryIntegrationTest {
  @Autowired
  private CardRepository cardRepository;

  @Test
  public void findCardByNumberWhenExists() {
    final var id = UUID.randomUUID();
    final var number = this.getRandomCardNumber();
    final var password = "12321";
    final var cvv = "901";
    final var expirationDate = "01/25";
    final var ownerName = "João";
    final var balance = new BigDecimal("550.25");
    final var createdAt = LocalDateTime.now().minusHours(5);
    final var updatedAt = LocalDateTime.now();

    final var cardTable = CardTable.builder()
        .id(id)
        .number(number)
        .password(password)
        .cvv(cvv)
        .expirationDate(expirationDate)
        .ownerName(ownerName)
        .balance(balance)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();

    final var persistedCard = this.cardRepository.saveAndFlush(cardTable);

    final var existingCard = this.cardRepository.findOneByNumber(number);

    this.cardRepository.deleteById(persistedCard.getId());

    Assertions.assertNotNull(existingCard);
    Assertions.assertTrue(existingCard.isPresent());
    Assertions.assertEquals(existingCard.get().getId(), id);
    Assertions.assertEquals(existingCard.get().getNumber(), number);
    Assertions.assertEquals(existingCard.get().getPassword(), password);
    Assertions.assertEquals(existingCard.get().getCvv(), cvv);
    Assertions.assertEquals(existingCard.get().getExpirationDate(), expirationDate);
    Assertions.assertEquals(existingCard.get().getOwnerName(), ownerName);
    Assertions.assertEquals(existingCard.get().getBalance(), balance);
    Assertions.assertNotNull(existingCard.get().getCreatedAt());
    Assertions.assertNotNull(existingCard.get().getUpdatedAt());
  }

  @Test
  public void findCardByNumberWhenNotExists() {
    final var number = this.getRandomCardNumber();

    final var existingCard = this.cardRepository.findOneByNumber(number);

    Assertions.assertNotNull(existingCard);
    Assertions.assertTrue(existingCard.isEmpty());
  }

  @Test
  public void updateCardBalanceWithSufficientFunds() {
    final var id = UUID.randomUUID();
    final var number = this.getRandomCardNumber();
    final var password = "12321";
    final var cvv = "901";
    final var expirationDate = "01/25";
    final var ownerName = "João";
    final var balance = new BigDecimal("550.25");
    final var createdAt = LocalDateTime.now().minusHours(5);
    final var updatedAt = LocalDateTime.now();

    final var cardTable = CardTable.builder()
        .id(id)
        .number(number)
        .password(password)
        .cvv(cvv)
        .expirationDate(expirationDate)
        .ownerName(ownerName)
        .balance(balance)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();

    final var persistedCard = this.cardRepository.saveAndFlush(cardTable);

    final var updated = this.cardRepository.updateCardBalanceIfSufficient(persistedCard.getId(), new BigDecimal("15.25"));

    this.cardRepository.deleteById(persistedCard.getId());

    Assertions.assertNotNull(updated);
    Assertions.assertEquals(updated, 1);
  }

  @Test
  public void updateCardBalanceWithInsufficientFunds() {
    final var id = UUID.randomUUID();
    final var number = this.getRandomCardNumber();
    final var password = "12321";
    final var cvv = "901";
    final var expirationDate = "01/25";
    final var ownerName = "João";
    final var balance = new BigDecimal("550.25");
    final var createdAt = LocalDateTime.now().minusHours(5);
    final var updatedAt = LocalDateTime.now();

    final var cardTable = CardTable.builder()
        .id(id)
        .number(number)
        .password(password)
        .cvv(cvv)
        .expirationDate(expirationDate)
        .ownerName(ownerName)
        .balance(balance)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();

    final var persistedCard = this.cardRepository.saveAndFlush(cardTable);

    final var updated = this.cardRepository.updateCardBalanceIfSufficient(persistedCard.getId(), new BigDecimal("850.50"));

    this.cardRepository.deleteById(persistedCard.getId());

    Assertions.assertNotNull(updated);
    Assertions.assertEquals(updated, 0);
  }

  @Test
  public void updateCardBalanceConcurrently() throws Throwable {
    final var id = UUID.randomUUID();
    final var number = this.getRandomCardNumber();
    final var password = "12321";
    final var cvv = "901";
    final var expirationDate = "01/25";
    final var ownerName = "João";
    final var balance = new BigDecimal("99.99");
    final var createdAt = LocalDateTime.now().minusHours(5);
    final var updatedAt = LocalDateTime.now();

    final var cardTable = CardTable.builder()
        .id(id)
        .number(number)
        .password(password)
        .cvv(cvv)
        .expirationDate(expirationDate)
        .ownerName(ownerName)
        .balance(balance)
        .createdAt(createdAt)
        .updatedAt(updatedAt)
        .build();

    final var persistedCard = this.cardRepository.saveAndFlush(cardTable);

    final var random = new Random();

    final var numberOfThreads = 10;
    final var executorService = Executors.newFixedThreadPool(numberOfThreads);

    final var futures = new ArrayList<Future<?>>();

    for (int i = 0; i < numberOfThreads; i++) {
      futures.add(executorService.submit(() -> {
        BigDecimal amountToSubtract = BigDecimal.valueOf(10 + random.nextInt(16)); // Generate a value between 10 and 25
        this.cardRepository.updateCardBalanceIfSufficient(persistedCard.getId(), amountToSubtract);
      }));
    }

    // wait for threads
    for (Future<?> future : futures) {
      future.get();
    }

    executorService.shutdown();

    final var existingCard = this.cardRepository.findById(persistedCard.getId());

    Assertions.assertNotNull(existingCard);
    Assertions.assertTrue(existingCard.isPresent());

    final var finalValueOfBalance = existingCard.get().getBalance();

    this.cardRepository.deleteById(persistedCard.getId());

    Assertions.assertTrue(finalValueOfBalance.compareTo(BigDecimal.ZERO) >= 0);
  }

  private String getRandomCardNumber() {
    Random random = new Random();
    long min = 1_000_000_000L;
    long max = 9_999_999_999L;
    long number = min + (long) (random.nextDouble() * (max - min + 1));
    return Long.toString(number);
  }
}
