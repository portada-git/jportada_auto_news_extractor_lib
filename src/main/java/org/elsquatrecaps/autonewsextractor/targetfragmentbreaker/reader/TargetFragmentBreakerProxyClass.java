package org.elsquatrecaps.autonewsextractor.targetfragmentbreaker.reader;

import java.util.List;
import org.elsquatrecaps.autonewsextractor.tools.configuration.Configurable;
import org.elsquatrecaps.autonewsextractor.tools.configuration.TargetFragmentBreakerConfiguration;
import org.elsquatrecaps.utilities.proxies.ProxyByAnnotationsBuilder;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;

/**
 *
 * @author josepcanellas
 */
@SuppressWarnings("unchecked")
public class TargetFragmentBreakerProxyClass implements FactReader{
    private static ProxyByAnnotationsBuilder<FactReader,TargetFragmentBreakerMarkerAnnotation> builder=null;
            
    private static void updateBuilder(String... searchPackages){
        builder = new ProxyByAnnotationsBuilder<>(FactReader.class, TargetFragmentBreakerMarkerAnnotation.class, searchPackages);
        builder.updateClassMap();
    }
   
    private FactReader reader;
    
    private TargetFragmentBreakerProxyClass(FactReader r) {
        reader = r;
    }
    
    private static FactReader getReader(String type){
        return getReader(type, "org.elsquatrecaps.autonewsextractor.targetfragmentbreaker.reader");
    }
    
    private static FactReader getReader(String type, Configuration conf){
        FactReader ret =  getReader(type, ((TargetFragmentBreakerConfiguration)conf).getTargetfragmentBreakerProxyPackagesToSearch());
        Configurable c = (Configurable) ret;
        c.init(conf);
        return ret;
    }
    
    private static FactReader getReader(String type, String... searchPackages){
        if(builder==null){
            updateBuilder(searchPackages);
        }
        return builder.getInstance(type);
    }
    
    public static TargetFragmentBreakerProxyClass getInstance(String type){
       return new TargetFragmentBreakerProxyClass(getReader(type));
    }
    
    public static TargetFragmentBreakerProxyClass getInstance(String type, Configuration conf){
       return new TargetFragmentBreakerProxyClass(getReader(type, conf));
    }

    public static TargetFragmentBreakerProxyClass getInstance(String type, String... searchPackages){
       return new TargetFragmentBreakerProxyClass(getReader(type, searchPackages));
    }

//    @Override
//    public void readFileAndSaveData(String file) {
//        reader.readFileAndSaveData(file);
//    }
//
//    @Override
//    public void readFileAndSaveData(String file, Date date) {
//        reader.readFileAndSaveData(file, date);
//    }
//
//    @Override
//    public void readFileAndSaveData(String[] file) {
//        reader.readFileAndSaveData(file);
//    }
//
//    @Override
//    public void readFileAndSaveData(String[] file, Date date) {
//        reader.readFileAndSaveData(file, date);
//    }
//
//    @Override
//    public void readFileAndSaveData(List<String> file) {
//        reader.readFileAndSaveData(file);
//    }
//
//    @Override
//    public void readFileAndSaveData(List<String> file, Date date) {
//        reader.readFileAndSaveData(file, date);
//    }

    @Override
    public String readFileAndGetText(String file) {
        return reader.readFileAndGetText(file);
    }

    @Override
    public String readFileAndGetText(String[] file) {
        return reader.readFileAndGetText(file);
    }

    @Override
    public String readFileAndGetText(List<String> file) {
        return reader.readFileAndGetText(file);
    }
}
