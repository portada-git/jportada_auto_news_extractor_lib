package org.elsquatrecaps.autonewsextractor.tools.configuration;

import org.elsquatrecaps.utilities.tools.configuration.Configuration;

/**
 *
 * @author josepcanellas
 */
public interface InformationUnitBuilderrConfiguration extends Configuration{
    String getOriginDir();
    String getFileExtension();
    String getFragmentBreakerApproach();
    void setOriginDir(String originDir);
    void setFileExtension(String originDir);
    void setFragmentBreakerApproach(String originDir);
    String[] getInformationUnitBuilderProxyPackagesToSearch();
    void setInformationUnitBuilderProxyPackagesToSearch(String[] packages);
    String[] getTargetfragmentBreakerProxyPackagesToSearch();
    void setTargetfragmentBreakerProxyPackagesToSearch(String[] packages);
            
}
