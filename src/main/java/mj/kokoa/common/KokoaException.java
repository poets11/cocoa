package mj.kokoa.common;

/**
 * Created by poets11 on 2016. 8. 23..
 */
public class KokoaException extends RuntimeException {
    public KokoaException() {
    }

    public KokoaException(String message) {
        super(message);
    }

    public KokoaException(String message, Throwable cause) {
        super(message, cause);
    }

    public KokoaException(Throwable cause) {
        super(cause);
    }

    public KokoaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
