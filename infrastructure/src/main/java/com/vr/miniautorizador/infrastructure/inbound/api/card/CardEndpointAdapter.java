package com.vr.miniautorizador.infrastructure.inbound.api.card;

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
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cards")
@Tag(name = "Card Endpoint", description = "/cards")
public class CardEndpointAdapter {
  private final GetCardByNumberUseCasePort getCardByNumberUseCasePort;
  private final CreateCardUseCasePort createCardUseCasePort;

  @GetMapping("/{number}")
  @Operation(summary = "Get Card By Number")
  @ApiResponses(
      value = {
          @ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = CardEndpointResponse.class))),
          @ApiResponse(responseCode = "400", description = "Invalid parameters", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
          @ApiResponse(responseCode = "404", description = "Card not found", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
          @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
      }
  )
  @ResponseStatus(HttpStatus.OK)
  public CardEndpointResponse getByNumber(@PathVariable("number") String number) {
    return CardEndpointMapper.toResponse(
        this.getCardByNumberUseCasePort.execute(number)
    );
  }

  @PostMapping
  @Operation(summary = "Create Card")
  @ApiResponses(
      value = {
          @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = CardEndpointResponse.class))),
          @ApiResponse(responseCode = "400", description = "Invalid Request Body", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
          @ApiResponse(responseCode = "409", description = "Card already exists", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
          @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
      }
  )
  @ResponseStatus(HttpStatus.CREATED)
  public CardEndpointResponse create(
      @RequestBody CardEndpointRequest request
  ) {
    return CardEndpointMapper.toResponse(
        this.createCardUseCasePort.execute(
            CardEndpointMapper.toEntity(request)
        )
    );
  }
}
