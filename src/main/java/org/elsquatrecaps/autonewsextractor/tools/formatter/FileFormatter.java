package org.elsquatrecaps.autonewsextractor.tools.formatter;

/**
 *
 * @author josep
 */
public interface FileFormatter {

    void toFile(String outputFileName);

    @Override
    String toString();
    
}
