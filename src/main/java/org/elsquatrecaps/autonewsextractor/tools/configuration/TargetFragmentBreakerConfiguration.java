package org.elsquatrecaps.autonewsextractor.tools.configuration;

/**
 *
 * @author josepcanellas
 */
public interface TargetFragmentBreakerConfiguration extends RegexConfiguration{
    String[] getTargetfragmentBreakerProxyPackagesToSearch();

    void setTargetfragmentBreakerProxyPackagesToSearch(String[] packages);

    String getExtractorApproach();
    
    String getOriginDir();
}
