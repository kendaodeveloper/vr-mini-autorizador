package com.vr.miniautorizador.domain.exceptions.base;

import com.vr.miniautorizador.domain.exceptions.MultipleErrorsException;

import java.util.ArrayList;
import java.util.List;

public class ExceptionManager {
  private final List<RuntimeException> exceptions;

  public ExceptionManager() {
    this.exceptions = new ArrayList<>();
  }

  public void addException(RuntimeException e) {
    this.exceptions.add(e);
  }

  private boolean hasExceptions() {
    return !this.exceptions.isEmpty();
  }

  public void throwExceptionsIfNecessary() throws RuntimeException {
    if (this.hasExceptions()) {
      if (this.exceptions.size() == 1) {
        throw this.exceptions.get(0);
      } else {
        throw new MultipleErrorsException(this.exceptions);
      }
    }
  }
}
