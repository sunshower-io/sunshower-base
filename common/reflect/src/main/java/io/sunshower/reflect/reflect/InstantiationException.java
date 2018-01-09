package io.sunshower.reflect.reflect;

public class InstantiationException extends ReflectionException {

  public InstantiationException() {}

  public InstantiationException(String message) {
    super(message);
  }

  public InstantiationException(String message, Throwable cause) {
    super(message, cause);
  }

  public InstantiationException(Throwable cause) {
    super(cause);
  }

  public InstantiationException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
