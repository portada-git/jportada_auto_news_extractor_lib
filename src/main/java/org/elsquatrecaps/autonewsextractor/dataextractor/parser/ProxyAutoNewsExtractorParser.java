package org.elsquatrecaps.autonewsextractor.dataextractor.parser;

import java.util.List;
import org.elsquatrecaps.autonewsextractor.model.ExtractedData;
import org.elsquatrecaps.autonewsextractor.model.ImmutableNewsExtractedData;
import org.elsquatrecaps.autonewsextractor.model.MutableNewsExtractedData;
import org.elsquatrecaps.autonewsextractor.tools.configuration.DataExtractConfiguration;
import org.elsquatrecaps.portada.jportadamscaller.PortadaMicroservicesCaller;
import org.elsquatrecaps.utilities.proxies.ProxyByAnnotationsBuilder;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;
import org.json.JSONObject;


/**
 *
 * @author josepcanellas
 */
@SuppressWarnings("unchecked")
public class ProxyAutoNewsExtractorParser implements ExtractorParserApproach<ExtractedData>{
    private static ProxyByAnnotationsBuilder<ExtractorParserApproach,ParserMarkerAnnotation> builder=null;
            
    private static void updateBuilder(String... searchPackages){
        builder = new ProxyByAnnotationsBuilder<>(ExtractorParserApproach.class, ParserMarkerAnnotation.class, searchPackages);
        builder.updateClassMap();
    }
   
    private ExtractorParserApproach extractor;
    
    private ProxyAutoNewsExtractorParser(ExtractorParserApproach p) {
        extractor = p;
    }
    
    private static ExtractorParserApproach getExtractor(String type, Configuration conf, int parserId){
        ExtractorParserApproach ret =  getExtractor(type, ((DataExtractConfiguration)conf).getDataExtractProxyPackagesToSearch());
        ret.init(conf, parserId);
        return ret;
    }
    
    private static ExtractorParserApproach getExtractor(String type, Configuration conf, int parserId, JSONObject jSONObject){
        ExtractorParserApproach ret =  getExtractor(type, ((DataExtractConfiguration)conf).getDataExtractProxyPackagesToSearch());
        ret.init(conf, parserId);
        ret.init(jSONObject);
        return ret;
    }
    
    private static ExtractorParserApproach getExtractor(String type, Configuration conf, int parserId, JSONObject jSONObject, JSONObject constants){
        ExtractorParserApproach ret =  getExtractor(type, ((DataExtractConfiguration)conf).getDataExtractProxyPackagesToSearch());
        ret.init(conf, parserId);
        ret.init(jSONObject);
        ret.init(constants);
        return ret;
    }
    
    private static ExtractorParserApproach getExtractor(String type, String... searchPackages){
        if(builder==null){
            updateBuilder(searchPackages);
        }
        return builder.getInstance(type);
    }
    
    public static ProxyAutoNewsExtractorParser getInstance(String type, String... searchPackages){
        ProxyAutoNewsExtractorParser ret;
        if(searchPackages==null || searchPackages.length==0){
            ret = getInstance(type);
        }else{
            ret = new ProxyAutoNewsExtractorParser(getExtractor(type, searchPackages));
        }
        return ret;
    }
    
    public static ProxyAutoNewsExtractorParser getInstance(String type){
        return  new ProxyAutoNewsExtractorParser(getExtractor(type, "org.elsquatrecaps.autonewsextractor.dataextractor.parser"));
    }
    
    public static ProxyAutoNewsExtractorParser getInstance(String type, Configuration conf, int parserId){
       return new ProxyAutoNewsExtractorParser(getExtractor(type, conf, parserId));
    }

    public static ProxyAutoNewsExtractorParser getInstance(String type, Configuration conf, int parserId, JSONObject jSONObject){
       return new ProxyAutoNewsExtractorParser(getExtractor(type, conf, parserId, jSONObject));
    }

    public static ProxyAutoNewsExtractorParser getInstance(String type, Configuration conf, int parserId, JSONObject jSONConfig, JSONObject constants){
       return new ProxyAutoNewsExtractorParser(getExtractor(type, conf, parserId, jSONConfig, constants));
    }

    @Override
    public void setLastParsed(ExtractedData lastParsed) {
        extractor.setLastParsed(lastParsed);
    }

    @Override
    public void init(Configuration configuration, int parserId) {
        extractor.init(configuration, parserId);
    }

    @Override
    public void init(JSONObject jsonConfig) {
        extractor.init(jsonConfig);
    }

    @Override
    public List<ExtractedData> parseFromString(String bonText, MutableNewsExtractedData partialExtractedDataToCopy) {
        return extractor.parseFromString(bonText, partialExtractedDataToCopy);
    }

    @Override
    public MutableNewsExtractedData getDefaultData(ImmutableNewsExtractedData def) {
        return extractor.getDefaultData(def);
    }

    @Override
    public void updateDefaultData(MutableNewsExtractedData ed) {
        extractor.updateDefaultData(ed);
    }

    @Override
    public void init(String challenge, String signedData) {
        extractor.init(challenge, signedData);
    }

    @Override
    public boolean needSecurityConfig() {
        return extractor.needSecurityConfig();
    }
}
