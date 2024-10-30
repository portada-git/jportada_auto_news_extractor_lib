package org.elsquatrecaps.autonewsextractor.error;

/**
 *
 * @author josep
 */
public class AutoNewsRuntimeException extends RuntimeException{

    /**
     * Creates a new instance of <code>AutoNewsReaderRuntimeException</code> without detail message.
     */
    public AutoNewsRuntimeException() {
    }


    /**
     * Constructs an instance of <code>AutoNewsReaderRuntimeException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public AutoNewsRuntimeException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>AutoNewsReaderRuntimeException</code> with the original error.
     * @param e the original throwable error.
     */
    public AutoNewsRuntimeException(Throwable e){
        super(e);
    }

    /**
     * Constructs an instance of <code>AutoNewsReaderRuntimeException</code> with the specified detail 
     * message and the the original error.
     * @param msg the detail message.
     * @param e the original throwable.
     */
    public AutoNewsRuntimeException(String msg, Throwable e){
        super(msg, e);
    }
}
