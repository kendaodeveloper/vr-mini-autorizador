package com.vr.miniautorizador.domain.exceptions;

import java.util.List;

public class MultipleErrorsException extends RuntimeException {
  private final List<RuntimeException> exceptions;

  public MultipleErrorsException(List<RuntimeException> exceptions) {
    super("Multiple Errors");
    this.exceptions = exceptions;
  }

  public List<RuntimeException> getExceptions() {
    return this.exceptions;
  }
}
