package org.elsquatrecaps.autonewsextractor.tools.configuration;

/**
 *
 * @author josepcanellas
 */
public interface TargetFragmentCutterConfiguration extends RegexConfiguration{
    String[] getTargetfragmentBreakerProxyPackagesToSearch();

    void setTargetfragmentBreakerProxyPackagesToSearch(String[] packages);

    String getExtractorApproach();
    
    String getOriginDir();
}
