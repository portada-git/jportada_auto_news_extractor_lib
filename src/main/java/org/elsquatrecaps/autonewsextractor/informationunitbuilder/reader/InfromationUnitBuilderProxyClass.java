package org.elsquatrecaps.autonewsextractor.informationunitbuilder.reader;

import java.util.ArrayList;
import java.util.List;
import org.elsquatrecaps.autonewsextractor.model.PublicationInfo;
import org.elsquatrecaps.autonewsextractor.tools.configuration.Configurable;
import org.elsquatrecaps.autonewsextractor.tools.configuration.InformationUnitBuilderrConfiguration;
import org.elsquatrecaps.utilities.iterators.ConsumerIterator;
import org.elsquatrecaps.utilities.proxies.ProxyByAnnotationsBuilder;
import org.elsquatrecaps.utilities.tools.Callback;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;

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
    
    private final InformationUnitBuilder informationUnitBuilder;
    
    private InfromationUnitBuilderProxyClass(InformationUnitBuilder r) {
        informationUnitBuilder = r;
    }
    
    private static InformationUnitBuilder getInformationUnitBuilder(String type){
        return getInstance(type, "org.elsquatrecaps.autonewsextractor.informationunitbuilder.runnable");
    }
    
    private static InformationUnitBuilder getInformationUnitBuilder(String type, String... searchPackages){
        if(builder==null){
            updateBuilder(searchPackages);
        }
        return builder.getInstance(type);
    }

    public static InformationUnitBuilder getInformationUnitBuilder(String joinerType, String metadataSource){
        return InfromationUnitBuilderProxyClass.getInformationUnitBuilder(joinerType.concat(".").concat(metadataSource));
    }
    
    public static InformationUnitBuilder getInformationUnitBuilder(String joinerType, String metadataSource, Configuration conf){
        InformationUnitBuilder ret = InfromationUnitBuilderProxyClass.getInformationUnitBuilder(joinerType.concat(".").concat(metadataSource), ((InformationUnitBuilderrConfiguration)conf).getInformationUnitBuilderProxyPackagesToSearch());
        Configurable c = (Configurable) ret;
        c.init(conf);
        return ret;
    }
    
    public static InformationUnitBuilder getInformationUnitBuilder(String joinerType, String metadataSource, String... searchPackages){
        return InfromationUnitBuilderProxyClass.getInformationUnitBuilder(joinerType.concat(".").concat(metadataSource), searchPackages);
    }

    public static InfromationUnitBuilderProxyClass getInstance(String joinerType, String metadataSource){
       return new InfromationUnitBuilderProxyClass(InfromationUnitBuilderProxyClass.getInformationUnitBuilder(joinerType.concat(".").concat(metadataSource)));
    }
    
    public static InfromationUnitBuilderProxyClass getInstance(String joinerType, String metadataSource, InformationUnitBuilderrConfiguration conf){
       return new InfromationUnitBuilderProxyClass(InfromationUnitBuilderProxyClass.getInformationUnitBuilder(joinerType, metadataSource, conf));
    }
    
    public static InfromationUnitBuilderProxyClass getInstance(String joinerType, String metadataSource, String... searchPackages){
       return new InfromationUnitBuilderProxyClass(InfromationUnitBuilderProxyClass.getInformationUnitBuilder(joinerType.concat(".").concat(metadataSource), searchPackages));
    }

    @Override
    public void createAndProcessEachInformationUnitText(Callback<InformationUnitDataParamsFromTextsForCallback, Void> callbak, 
            PublicationInfo publicationInfo, 
            String... text) {
        informationUnitBuilder.createAndProcessEachInformationUnitText(callbak, publicationInfo, text);
    }

    @Override
    public void createAndProcessEachInformationUnitFiles(Callback<InformationUnitDataParamsFromFilesForCallback, Void> callbak,
            String modelVersion) {
        informationUnitBuilder.createAndProcessEachInformationUnitFiles(callbak, modelVersion);
    }

    @Override
    public void createAndProcessEachInformationUnitFiles(
            Callback<InformationUnitDataParamsFromFilesForCallback, Void> callbak,
            ConsumerIterator<DataToProcess> iterator,
            String modelVersion) {
        informationUnitBuilder.createAndProcessEachInformationUnitFiles(callbak, iterator, modelVersion);
    }

    @Override
    public void createAndProcessEachInformationUnitFiles(
            Callback<InformationUnitDataParamsFromFilesForCallback, Void> callbak,
            String originDir,
            String modelVersion) {
        informationUnitBuilder.createAndProcessEachInformationUnitFiles(callbak, originDir, modelVersion);
    }

    @Override
    public void createAndProcessEachInformationUnitFiles(
            Callback<InformationUnitDataParamsFromFilesForCallback, Void> callbak,
            String originDir,
            String extension,
            String modelVersion) {
        informationUnitBuilder.createAndProcessEachInformationUnitFiles(callbak, originDir, extension, modelVersion);
    }

    @Override
    public void createAndProcessEachInformationUnitFiles(Callback<InformationUnitDataParamsFromFilesForCallback, Void> callbak,
            PublicationInfo publicationInfo) {
        informationUnitBuilder.createAndProcessEachInformationUnitFiles(callbak, publicationInfo);
    }

//    @Override
//    public JSONArray getAllInformationUnitAsJson() {
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
//    }

    @Override
    public String readFileAndGetText(String file) {
        return informationUnitBuilder.readFileAndGetText(file);
    }

    @Override
    public String readFileAndGetText(String[] file) {
        return informationUnitBuilder.readFileAndGetText(file);
    }

    @Override
    public String readFileAndGetText(List<String> file) {
        return informationUnitBuilder.readFileAndGetText(file);
    }

    @Override
    public String getText(String text) {
        return informationUnitBuilder.getText(text);
    }

    @Override
    public String getText(String[] text) {
        return informationUnitBuilder.getText(text);
    }

    @Override
    public String getText(List<String> text) {
        return informationUnitBuilder.getText(text);
    }

}
