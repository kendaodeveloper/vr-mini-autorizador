package com.vr.miniautorizador.infrastructure.outbound.database.card.repository;

import com.vr.miniautorizador.infrastructure.outbound.database.card.table.CardTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<CardTable, UUID> {
  Optional<CardTable> findOneByNumber(String number);

  @Transactional
  @Modifying
  @Query("UPDATE CardTable c SET c.balance = (c.balance - :amount) WHERE c.id = :cardId AND c.balance >= :amount")
  Integer updateCardBalanceIfSufficient(UUID cardId, BigDecimal amount);
}
