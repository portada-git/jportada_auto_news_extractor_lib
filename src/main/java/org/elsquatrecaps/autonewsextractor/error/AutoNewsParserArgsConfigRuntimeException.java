package org.elsquatrecaps.autonewsextractor.error;

/**
 *
 * @author josep
 */
public class AutoNewsParserArgsConfigRuntimeException extends RuntimeException{

    /**
     * Creates a new instance of <code>AutoNewsReaderRuntimeException</code> without detail message.
     */
    public AutoNewsParserArgsConfigRuntimeException() {
    }


    /**
     * Constructs an instance of <code>AutoNewsReaderRuntimeException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public AutoNewsParserArgsConfigRuntimeException(String msg) {
        super(msg);
    }

    /**
     * Constructs an instance of <code>AutoNewsReaderRuntimeException</code> with the original error.
     * @param e the original throwable error.
     */
    public AutoNewsParserArgsConfigRuntimeException(Throwable e){
        super(e);
    }

    /**
     * Constructs an instance of <code>AutoNewsReaderRuntimeException</code> with the specified detail 
     * message and the the original error.
     * @param msg the detail message.
     * @param e the original throwable.
     */
    public AutoNewsParserArgsConfigRuntimeException(String msg, Throwable e){
        super(msg, e);
    }
}
