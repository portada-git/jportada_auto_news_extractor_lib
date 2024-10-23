package org.elsquatrecaps.autonewsextractor.informationunitbuilder.runnable;

import org.elsquatrecaps.autonewsextractor.tools.configuration.Configurable;
import org.elsquatrecaps.autonewsextractor.tools.configuration.InformationUnitBuilderrConfiguration;
import org.elsquatrecaps.utilities.proxies.ProxyByAnnotationsBuilder;
import org.elsquatrecaps.utilities.tools.Callback;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;
import org.json.JSONArray;

/**
 *
 * @author josepcanellas
 */
@SuppressWarnings("unchecked")
public class InfromationUnitBuilderProxyClass implements InformationUnitBuilder{
    private static ProxyByAnnotationsBuilder<InformationUnitBuilder,InformationUnitBuilderMarkerAnnotation> builder=null;
            
    private static void updateBuilder(String... searchPackages){
        builder = new ProxyByAnnotationsBuilder<>(InformationUnitBuilder.class, InformationUnitBuilderMarkerAnnotation.class, searchPackages);
        builder.updateClassMap();
    }
    
    
    private final InformationUnitBuilder runnable;
    
//    private InfromationUnitBuilderProxyClass(String joinerType, String metadataSource) {
//        runnable = getRunnable(joinerType.concat(".").concat(metadataSource));
//    }

    private InfromationUnitBuilderProxyClass(InformationUnitBuilder r) {
        runnable = r;
    }
    
    private static InformationUnitBuilder getRunnable(String type){
        return getInstance(type, "org.elsquatrecaps.autonewsextractor.informationunitbuilder.runnable");
    }
    
    private static InformationUnitBuilder getRunnable(String type, String... searchPackages){
        if(builder==null){
            updateBuilder(searchPackages);
        }
        return builder.getInstance(type);
    }

    public static InformationUnitBuilder getRunnable(String joinerType, String metadataSource){
        return getRunnable(joinerType.concat(".").concat(metadataSource));
    }
    
    public static InformationUnitBuilder getRunnable(String joinerType, String metadataSource, Configuration conf){
        InformationUnitBuilder ret = getRunnable(joinerType.concat(".").concat(metadataSource), ((InformationUnitBuilderrConfiguration)conf).getInformationUnitBuilderProxyPackagesToSearch());
        Configurable c = (Configurable) ret;
        c.init(conf);
        return ret;
    }
    
    public static InformationUnitBuilder getRunnable(String joinerType, String metadataSource, String... searchPackages){
        return getRunnable(joinerType.concat(".").concat(metadataSource), searchPackages);
    }

    public static InfromationUnitBuilderProxyClass getInstance(String joinerType, String metadataSource){
       return new InfromationUnitBuilderProxyClass(getRunnable(joinerType.concat(".").concat(metadataSource)));
    }
    
    public static InfromationUnitBuilderProxyClass getInstance(String joinerType, String metadataSource, InformationUnitBuilderrConfiguration conf){
       return new InfromationUnitBuilderProxyClass(getRunnable(joinerType, metadataSource, conf));
    }
    
    public static InfromationUnitBuilderProxyClass getInstance(String joinerType, String metadataSource, String... searchPackages){
       return new InfromationUnitBuilderProxyClass(getRunnable(joinerType.concat(".").concat(metadataSource), searchPackages));
    }

    @Override
    public void run() {
        runnable.run();
    }

    @Override
    public void createAndProcessEachInformationUnitText(Callback<? extends InformationUnitBuilderFromSdlFiles.TextInformationUnitDataParamsForCallback, Void> callbak) {
        runnable.createAndProcessEachInformationUnitText(callbak);
    }

    @Override
    public void createAndProcessEachInformationUnitFiles(Callback<? extends InformationUnitBuilderFromSdlFiles.FileInformationUnitDataParamsForCallback, Void> callbak) {
        runnable.createAndProcessEachInformationUnitFiles(callbak);
    }

    @Override
    public JSONArray getAllInformationUnitAsJson() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
