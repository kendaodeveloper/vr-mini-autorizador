package com.vr.miniautorizador.infrastructure.inbound.api.card;

import com.vr.miniautorizador.domain.entities.base.Pair;
import com.vr.miniautorizador.domain.entities.card.Card;
import com.vr.miniautorizador.domain.exceptions.NotFoundException;
import com.vr.miniautorizador.domain.ports.in.card.CreateCardUseCasePort;
import com.vr.miniautorizador.domain.ports.in.card.GetCardByNumberUseCasePort;
import com.vr.miniautorizador.infrastructure.inbound.api.base.advice.dto.ExceptionDto;
import com.vr.miniautorizador.infrastructure.inbound.api.card.dto.CardEndpointRequest;
import com.vr.miniautorizador.infrastructure.inbound.api.card.dto.CardEndpointResponse;
import com.vr.miniautorizador.infrastructure.inbound.api.card.mapper.CardEndpointMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cartoes")
@Tag(name = "Card Endpoint", description = "/cards")
public class CardEndpointAdapter {
  private final GetCardByNumberUseCasePort getCardByNumberUseCasePort;
  private final CreateCardUseCasePort createCardUseCasePort;

  @PostMapping
  @Operation(summary = "Create Card")
  @ApiResponses(
      value = {
          @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = CardEndpointResponse.class))),
          @ApiResponse(responseCode = "400", description = "Invalid Request Body", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
          @ApiResponse(responseCode = "422", description = "Card already exists", content = @Content(schema = @Schema(implementation = CardEndpointResponse.class))),
          @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
      }
  )
  public ResponseEntity<CardEndpointResponse> create(
      @RequestBody CardEndpointRequest request
  ) {
    final Pair<Card, Boolean> pair = this.createCardUseCasePort.execute(
        CardEndpointMapper.toEntity(request)
    );

    final Card card = pair.getFirst();
    final Boolean exists = pair.getSecond();

    CardEndpointResponse response = CardEndpointMapper.toResponse(card);

    if (exists) {
      return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(response);
    } else {
      return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
  }

  @GetMapping("/{numeroCartao}")
  @Operation(summary = "Get Card Balance By Number")
  @ApiResponses(
      value = {
          @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = BigDecimal.class))),
          @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
          @ApiResponse(responseCode = "404", description = "Card not found", content = @Content),
          @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
      }
  )
  public ResponseEntity<BigDecimal> getBalanceByNumber(@PathVariable("numeroCartao") String number) {
    try {
      final var balance = this.getCardByNumberUseCasePort.execute(number).getBalance();
      return ResponseEntity.status(HttpStatus.OK).body(balance);
    } catch (NotFoundException e) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
  }
}
