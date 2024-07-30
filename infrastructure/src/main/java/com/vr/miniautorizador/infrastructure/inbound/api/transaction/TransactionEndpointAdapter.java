package com.vr.miniautorizador.infrastructure.inbound.api.transaction;

import com.vr.miniautorizador.domain.ports.in.transaction.CreateTransactionUseCasePort;
import com.vr.miniautorizador.infrastructure.inbound.api.base.advice.dto.ExceptionDto;
import com.vr.miniautorizador.infrastructure.inbound.api.transaction.dto.TransactionEndpointRequest;
import com.vr.miniautorizador.infrastructure.inbound.api.transaction.mapper.TransactionEndpointMapper;
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
@RequestMapping("/transactions")
@Tag(name = "Transaction Endpoint", description = "/transactions")
public class TransactionEndpointAdapter {
  private final CreateTransactionUseCasePort createTransactionUseCasePort;

  @PostMapping
  @Operation(summary = "Create Transaction")
  @ApiResponses(
      value = {
          @ApiResponse(responseCode = "201", description = "Created", content = @Content(schema = @Schema(implementation = TransactionEndpointMapper.class))),
          @ApiResponse(responseCode = "400", description = "Invalid Request Body", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
          @ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
          @ApiResponse(responseCode = "404", description = "Card not found", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
          @ApiResponse(responseCode = "422", description = "Invalid Password or Insufficient Funds", content = @Content(schema = @Schema(implementation = ExceptionDto.class))),
          @ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(schema = @Schema(implementation = ExceptionDto.class)))
      }
  )
  @ResponseStatus(HttpStatus.CREATED)
  public String create(
      @RequestBody TransactionEndpointRequest request
  ) {
    return this.createTransactionUseCasePort.execute(TransactionEndpointMapper.toEntity(request));
  }
}
