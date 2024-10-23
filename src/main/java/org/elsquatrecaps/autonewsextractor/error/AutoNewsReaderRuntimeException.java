package org.elsquatrecaps.autonewsextractor.error;

/**
 *
 * @author josep
 */
public class AutoNewsReaderRuntimeException extends RuntimeException{

    /**
     * Creates a new instance of <code>AutoNewsReaderRuntimeException</code> without detail message.
     */
    public AutoNewsReaderRuntimeException() {
    }


    /**
     * Constructs an instance of <code>AutoNewsReaderRuntimeException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public AutoNewsReaderRuntimeException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>AutoNewsReaderRuntimeException</code> with the original error.
     * @param e the original throwable error.
     */
    public AutoNewsReaderRuntimeException(Throwable e){
        super(e);
    }

    /**
     * Constructs an instance of <code>AutoNewsReaderRuntimeException</code> with the specified detail 
     * message and the the original error.
     * @param msg the detail message.
     * @param e the original throwable.
     */
    public AutoNewsReaderRuntimeException(String msg, Throwable e){
        super(msg, e);
    }
}
