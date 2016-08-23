package mj.cocoa.instance;

/**
 * Created by poets11 on 2016. 8. 23..
 */
public class CocoaException extends RuntimeException {
    public CocoaException() {
    }

    public CocoaException(String message) {
        super(message);
    }

    public CocoaException(String message, Throwable cause) {
        super(message, cause);
    }

    public CocoaException(Throwable cause) {
        super(cause);
    }

    public CocoaException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
