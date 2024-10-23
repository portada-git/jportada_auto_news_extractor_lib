package org.elsquatrecaps.autonewsextractor.error;

/**
 *
 * @author josep
 */
public class AutoNewsReaderException extends Exception{

    /**
     * Creates a new instance of <code>AutoNewsReaderException</code> without
     * detail message.
     */
    public AutoNewsReaderException() {
    }

    /**
     * Constructs an instance of <code>AutoNewsReaderException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AutoNewsReaderException(String msg) {
        super(msg);
    }
    
    public AutoNewsReaderException(Throwable e){
        super(e);
    }

    public AutoNewsReaderException(String msg, Throwable e){
        super(msg, e);
    }
}
