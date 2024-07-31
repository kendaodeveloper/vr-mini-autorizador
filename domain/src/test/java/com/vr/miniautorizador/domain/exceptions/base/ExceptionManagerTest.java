package com.vr.miniautorizador.domain.exceptions.base;

import com.vr.miniautorizador.domain.exceptions.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ExceptionManagerTest {
  @Test
  public void validateExceptionManagerWhenDoesNotHaveExceptions() {
    final var exceptionManager = new ExceptionManager();
    Assertions.assertDoesNotThrow(exceptionManager::throwExceptionsIfNecessary);
  }

  @Test
  public void validateExceptionManagerWhenHasOneException() {
    final var exceptionManager = new ExceptionManager();

    exceptionManager.addException(new NotFoundException("abc"));

    final var exception = Assertions.assertThrows(
        RuntimeException.class, exceptionManager::throwExceptionsIfNecessary
    );

    Assertions.assertNotNull(exception);
    Assertions.assertEquals(exception.getMessage(), "abc");
    Assertions.assertInstanceOf(NotFoundException.class, exception);
  }

  @Test
  public void validateExceptionManagerWhenHasMultipleExceptions() {
    final var exceptionManager = new ExceptionManager();

    exceptionManager.addException(new BadRequestException("123"));
    exceptionManager.addException(new ConflictException("xpto"));
    exceptionManager.addException(new NotFoundException("abc"));
    exceptionManager.addException(new UnprocessableEntityException("010"));

    final var exception = Assertions.assertThrows(
        RuntimeException.class, exceptionManager::throwExceptionsIfNecessary
    );

    Assertions.assertNotNull(exception);
    Assertions.assertEquals(exception.getMessage(), "Multiple Errors");
    Assertions.assertInstanceOf(MultipleErrorsException.class, exception);
    Assertions.assertEquals(((MultipleErrorsException) exception).getExceptions().size(), 4);
    Assertions.assertEquals(((MultipleErrorsException) exception).getExceptions().get(0).getMessage(), "123");
    Assertions.assertEquals(((MultipleErrorsException) exception).getExceptions().get(1).getMessage(), "xpto");
    Assertions.assertEquals(((MultipleErrorsException) exception).getExceptions().get(2).getMessage(), "abc");
    Assertions.assertEquals(((MultipleErrorsException) exception).getExceptions().get(3).getMessage(), "010");
  }
}
