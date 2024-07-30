package com.vr.miniautorizador.domain.entities.card;

import com.vr.miniautorizador.domain.entities.base.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Card extends Entity<UUID> implements Serializable {
  private String number;
  private String password;
  private String cvv;
  private String expirationDate;
  private String ownerName;
  private BigDecimal balance;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
