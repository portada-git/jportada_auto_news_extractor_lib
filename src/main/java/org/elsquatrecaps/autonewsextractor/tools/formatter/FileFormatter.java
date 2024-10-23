package org.elsquatrecaps.autonewsextractor.tools.formatter;

import org.elsquatrecaps.autonewsextractor.model.BaseFactData;

/**
 *
 * @author josep
 */
public interface FileFormatter {

    void toFile(String outputFileName);

    @Override
    String toString();
    
}
