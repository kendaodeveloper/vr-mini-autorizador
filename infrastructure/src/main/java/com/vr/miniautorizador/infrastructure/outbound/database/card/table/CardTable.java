package com.vr.miniautorizador.infrastructure.outbound.database.card.table;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "card")
public class CardTable {
  @Id
  @JdbcTypeCode(SqlTypes.VARCHAR)
  private UUID id;

  @Column(nullable = false, unique = true)
  private String number;

  @Column(nullable = false)
  private String password;

  @Column
  private String cvv;

  @Column(name = "expiration_date")
  private String expirationDate;

  @Column(nullable = false)
  private BigDecimal balance;

  @Column(name = "created_at", nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(name = "updated_at")
  private LocalDateTime updatedAt;

  @PrePersist
  protected void onCreate() {
    createdAt = LocalDateTime.now();
  }

  @PreUpdate
  protected void onUpdate() {
    updatedAt = LocalDateTime.now();
  }
}