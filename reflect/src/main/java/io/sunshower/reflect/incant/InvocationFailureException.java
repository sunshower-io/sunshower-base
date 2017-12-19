package io.sunshower.reflect.incant;


public class InvocationFailureException extends RuntimeException {

    public InvocationFailureException() {
        super();
    }

    public InvocationFailureException(String message) {
        super(message);
    }

    public InvocationFailureException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvocationFailureException(Throwable cause) {
        super(cause);
    }

    protected InvocationFailureException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
