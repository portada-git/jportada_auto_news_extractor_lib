package org.elsquatrecaps.autonewsextractor.targetfragmentbreaker.reader;

import java.util.Date;
import java.util.List;

/**
 *
 * @author josep
 */
public interface FactReader{  
//    public void readFileAndSaveData(String file);
//    public void readFileAndSaveData(String file, Date date);
//    public void readFileAndSaveData(String[] file);
//    public void readFileAndSaveData(String[] file, Date date);
//    public void readFileAndSaveData(List<String> file);
//    public void readFileAndSaveData(List<String> file, Date date);
    public String readFileAndGetText(String file);
    public String readFileAndGetText(String[] file);
    public String readFileAndGetText(List<String> file);
}
