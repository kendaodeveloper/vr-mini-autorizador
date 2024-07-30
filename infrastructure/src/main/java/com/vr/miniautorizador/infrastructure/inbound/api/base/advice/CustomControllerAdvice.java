package com.vr.miniautorizador.infrastructure.inbound.api.base.advice;

import com.vr.miniautorizador.domain.exceptions.*;
import com.vr.miniautorizador.infrastructure.inbound.api.base.advice.dto.ExceptionDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomControllerAdvice {
  @ExceptionHandler({
      HttpMessageNotReadableException.class,
      MethodArgumentTypeMismatchException.class,
      MissingServletRequestParameterException.class,
      BadRequestException.class,
      MultipleErrorsException.class
  })
  public ResponseEntity<ExceptionDto> handleBadRequestException(RuntimeException e) {
    return new ResponseEntity<>(
        new ExceptionDto(
            HttpStatus.BAD_REQUEST.value(),
            e.toString(),
            e instanceof MultipleErrorsException ?
                ((MultipleErrorsException) e).getExceptions().stream().map(Throwable::toString).collect(Collectors.toList()) :
                List.of(e.toString())
        ), HttpStatus.BAD_REQUEST
    );
  }

  @ExceptionHandler({
      NoResourceFoundException.class,
      NotFoundException.class
  })
  public ResponseEntity<ExceptionDto> handleNotFoundException(NotFoundException e) {
    return new ResponseEntity<>(
        new ExceptionDto(
            HttpStatus.NOT_FOUND.value(),
            e.toString(),
            List.of(e.toString())
        ), HttpStatus.NOT_FOUND
    );
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<ExceptionDto> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException e) {
    return new ResponseEntity<>(
        new ExceptionDto(
            HttpStatus.METHOD_NOT_ALLOWED.value(),
            e.toString(),
            List.of(e.toString())
        ), HttpStatus.METHOD_NOT_ALLOWED
    );
  }

  @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
  public ResponseEntity<ExceptionDto> handleNotAcceptableException(HttpMediaTypeNotAcceptableException e) {
    return new ResponseEntity<>(
        new ExceptionDto(
            HttpStatus.NOT_ACCEPTABLE.value(),
            e.toString(),
            List.of(e.toString())
        ), HttpStatus.NOT_ACCEPTABLE
    );
  }

  @ExceptionHandler(ConflictException.class)
  public ResponseEntity<ExceptionDto> handleConflictException(ConflictException e) {
    return new ResponseEntity<>(
        new ExceptionDto(
            HttpStatus.CONFLICT.value(),
            e.toString(),
            List.of(e.toString())
        ), HttpStatus.CONFLICT
    );
  }

  @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
  public ResponseEntity<ExceptionDto> handleUnsupportedMediaTypeException(HttpMediaTypeNotSupportedException e) {
    return new ResponseEntity<>(
        new ExceptionDto(
            HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(),
            e.toString(),
            List.of(e.toString())
        ), HttpStatus.UNSUPPORTED_MEDIA_TYPE
    );
  }

  @ExceptionHandler(UnprocessableEntityException.class)
  public ResponseEntity<ExceptionDto> handleUnprocessableEntityException(UnprocessableEntityException e) {
    return new ResponseEntity<>(
        new ExceptionDto(
            HttpStatus.UNPROCESSABLE_ENTITY.value(),
            e.toString(),
            List.of(e.toString())
        ), HttpStatus.UNPROCESSABLE_ENTITY
    );
  }

  @ExceptionHandler(Throwable.class)
  public ResponseEntity<ExceptionDto> handleInternalServerErrorException(Throwable e) {
    return new ResponseEntity<>(
        new ExceptionDto(
            HttpStatus.INTERNAL_SERVER_ERROR.value(),
            e.toString(),
            List.of(e.toString())
        ), HttpStatus.INTERNAL_SERVER_ERROR
    );
  }
}
