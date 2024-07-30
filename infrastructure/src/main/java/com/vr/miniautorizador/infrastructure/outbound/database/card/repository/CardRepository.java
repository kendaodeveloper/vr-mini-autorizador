package com.vr.miniautorizador.infrastructure.outbound.database.card.repository;

import com.vr.miniautorizador.infrastructure.outbound.database.card.table.CardTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CardRepository extends JpaRepository<CardTable, UUID> {
}
