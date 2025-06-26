package org.elsquatrecaps.autonewsextractor.informationunitbuilder.reader;

import java.util.List;

/**
 *
 * @author josep
 */
public interface FactReader {  
    String readFileAndGetText(String file);
    String readFileAndGetText(String[] file);
    String readFileAndGetText(List<String> file);
    String getText(String text);
    String getText(String[] text);
    String getText(List<String> text);
}
