package org.elsquatrecaps.autonewsextractor.tools.configuration;

import org.elsquatrecaps.utilities.tools.configuration.Configuration;

/**
 *
 * @author josepcanellas
 */
public interface DataExtractConfiguration extends Configuration{
    String[] getDataExtractProxyPackagesToSearch();

    void setDataExtractProxyPackagesToSearch(String[] packages);
    
    String[] getDataExtractCalculatorBuilderPackagesToSearch();

    void setDataExtractCalculatorBuilderPackagesToSearch(String[] packages);
    
    String getJsonConfigFile();

    void setJsonConfigFile(String file);
    

    //String getExtractorApproach();
}
