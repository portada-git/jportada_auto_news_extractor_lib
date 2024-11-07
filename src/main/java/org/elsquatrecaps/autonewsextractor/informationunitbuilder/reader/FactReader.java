package org.elsquatrecaps.autonewsextractor.informationunitbuilder.reader;

import java.util.List;

/**
 *
 * @author josep
 */
public interface FactReader {  
//    public void readFileAndSaveData(String file);
//    public void readFileAndSaveData(String file, Date date);
//    public void readFileAndSaveData(String[] file);
//    public void readFileAndSaveData(String[] file, Date date);
//    public void readFileAndSaveData(List<String> file);
//    public void readFileAndSaveData(List<String> file, Date date);
    String readFileAndGetText(String file);
    String readFileAndGetText(String[] file);
    String readFileAndGetText(List<String> file);
}
