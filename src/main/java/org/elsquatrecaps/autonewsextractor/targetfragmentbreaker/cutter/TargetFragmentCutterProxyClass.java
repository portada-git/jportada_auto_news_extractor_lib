package org.elsquatrecaps.autonewsextractor.targetfragmentbreaker.cutter;

import org.elsquatrecaps.autonewsextractor.tools.configuration.Configurable;
import org.elsquatrecaps.autonewsextractor.tools.configuration.TargetFragmentBreakerConfiguration;
import org.elsquatrecaps.utilities.proxies.ProxyByAnnotationsBuilder;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;

/**
 *
 * @author josepcanellas
 */
@SuppressWarnings("unchecked")
public class TargetFragmentCutterProxyClass implements TargetFragmentCutter{
    private static ProxyByAnnotationsBuilder<TargetFragmentCutter,TargetFragmentCutterMarkerAnnotation> builder=null;
            
    private static void updateBuilder(String... searchPackages){
        builder = new ProxyByAnnotationsBuilder<>(TargetFragmentCutter.class, TargetFragmentCutterMarkerAnnotation.class, searchPackages);
        builder.updateClassMap();
    }
   
    private TargetFragmentCutter breaker;
    
    private TargetFragmentCutterProxyClass(TargetFragmentCutter r) {
        breaker = r;
    }
    
    private static TargetFragmentCutter getBreaker(String type){
        return getBreaker(type, "org.elsquatrecaps.autonewsextractor.targetfragmentbreaker.breaker");
    }
    
    private static TargetFragmentCutter getBreaker(String type, Configuration conf){
        TargetFragmentCutter ret =  getBreaker(type, ((TargetFragmentBreakerConfiguration)conf).getTargetfragmentBreakerProxyPackagesToSearch());
        Configurable c = (Configurable) ret;
        c.init(conf);
        return ret;
    }
    
    private static TargetFragmentCutter getBreaker(String type, Configuration conf, int parserModel){
        TargetFragmentCutter ret =  getBreaker(type, ((TargetFragmentBreakerConfiguration)conf).getTargetfragmentBreakerProxyPackagesToSearch());
        ret.init(conf);
        ret.init(parserModel);
        return ret;
    }
    
    private static TargetFragmentCutter getBreaker(String type, String... searchPackages){
        if(builder==null){
            updateBuilder(searchPackages);
        }
        return builder.getInstance(type);
    }
    
    public static TargetFragmentCutterProxyClass getInstance(String type){
       return new TargetFragmentCutterProxyClass(getBreaker(type));
    }
    
    public static TargetFragmentCutterProxyClass getInstance(String type, Configuration conf){
       return new TargetFragmentCutterProxyClass(getBreaker(type, conf));
    }

    public static TargetFragmentCutterProxyClass getInstance(String type, Configuration conf, int parserModel){
       return new TargetFragmentCutterProxyClass(getBreaker(type, conf, parserModel));
    }

    public static TargetFragmentCutterProxyClass getInstance(String type, String... searchPackages){
       return new TargetFragmentCutterProxyClass(getBreaker(type, searchPackages));
    }

    @Override
    public String getTargetTextFromText(String bonText) {
        return breaker.getTargetTextFromText(bonText);
    }

    @Override
    public <T> TargetFragmentCutterProxyClass init(T conf) {
        breaker.init(conf);
        return this;
    }
}
