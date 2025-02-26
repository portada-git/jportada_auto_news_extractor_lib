package org.elsquatrecaps.autonewsextractor.tools.configuration;

/**
 *
 * @author josep
 */
public interface RegexConfiguration extends ParserConfiguration{

    String getFactModel();

    String getNewspaper();

    String getRegexBasePath();

    String getOcrEngineModel();

    void setFactModel(String val);

    void setNewspaper(String val);

    void setRegexBasePath(String val);

    void setOcrEngineModel(String val);
    
    void setRunForDebugging(Boolean val);
        
}
