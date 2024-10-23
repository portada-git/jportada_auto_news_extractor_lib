package org.elsquatrecaps.autonewsextractor.error;

/**
 *
 * @author josep
 */
public class AutoNewsRegexNotFoundException extends Exception{

    /**
     * Creates a new instance of <code>AutoNewsReaderException</code> without
     * detail message.
     */
    public AutoNewsRegexNotFoundException() {
    }

    /**
     * Constructs an instance of <code>AutoNewsReaderException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public AutoNewsRegexNotFoundException(String msg) {
        super(msg);
    }
    
    public AutoNewsRegexNotFoundException(Throwable e){
        super(e);
    }

    public AutoNewsRegexNotFoundException(String msg, Throwable e){
        super(msg, e);
    }
}
