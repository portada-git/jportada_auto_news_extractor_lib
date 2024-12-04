package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

import java.util.HashMap;
import java.util.Map;
import org.elsquatrecaps.autonewsextractor.model.ExtractedData;
import org.elsquatrecaps.autonewsextractor.tools.configuration.DataExtractConfiguration;
import org.elsquatrecaps.utilities.proxies.ProxyByAnnotationsBuilder;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;
import org.json.JSONObject;

/**
 *
 * @author josepcanellas
 */
@SuppressWarnings("unchecked")
public class DataExtractorCalculatorProxyClass implements AutoNewsExtractorCalculator<JSONObject, String>{
    public static final String LITERAL_PARAMS = "literalParams";
    public static final String FIELD_PARAMS = "fieldParams";
    public static final String PARAMS = "params";
    public static final String FIELD_VALUE_PARAM_TYPE = "fieldValue";
    public static final String FIELD_NAME_PARAM_TYPE = "fieldName";
    public static final String LITERAL_PARAM_TYPE = "literal";
//    private static ProxyByAnnotationsBuilder<ConfigurableAutoNewsExtractorCalculator,DataExtractorCalculatorMarkerAnnotation> builder=null;
    private static ProxyByAnnotationsBuilder<AutoNewsExtractorCalculator,DataExtractorCalculatorMarkerAnnotation> builder=null;
            
    private static void updateBuilder(String... searchPackages){
//        builder = new ProxyByAnnotationsBuilder<>(ConfigurableAutoNewsExtractorCalculator.class, DataExtractorCalculatorMarkerAnnotation.class, searchPackages);
        builder = new ProxyByAnnotationsBuilder<>(AutoNewsExtractorCalculator.class, DataExtractorCalculatorMarkerAnnotation.class, searchPackages);
        builder.updateClassMap();
    }
   
    //private ConfigurableAutoNewsExtractorCalculator<P, R> calculator;
    private final Map<String, Object> extraData = new HashMap<>();
//    private Configuration conf;
//    private ExtractedData extractedData;
//    private int parserId;

    public DataExtractorCalculatorProxyClass() {
    }
    
//    private DataExtractorCalculatorProxyClass(Configuration conf, int parserId, ExtractedData extractedData) {
//        this(conf, extractedData);
//        this.parserId=parserId;
//    }
//    
//    private DataExtractorCalculatorProxyClass(Configuration conf, ExtractedData extractedData) {
//        this.extractedData = extractedData;
//        this.conf=conf;
//    }
    
    public void init(String key, Object value){
        extraData.put(key, value);
    }
    
    public void init(Map.Entry<String, Object> entry){
        extraData.put(entry.getKey(), entry.getValue());
    }
    
//    private <P> ConfigurableAutoNewsExtractorCalculator<P,String> getCalculator(String calculatorId){
    private <P> AutoNewsExtractorCalculator<P,String> getCalculator(String calculatorId){
//        ConfigurableAutoNewsExtractorCalculator<P,String> ret;
        AutoNewsExtractorCalculator<P,String> ret;
        Configuration conf;
        if(extraData.containsKey(ExtraDataCalculatorEnum.CONFIGURATION.toString())){
            conf = (Configuration) extraData.get(ExtraDataCalculatorEnum.CONFIGURATION.toString());
            if(conf instanceof DataExtractConfiguration){
                DataExtractConfiguration c = (DataExtractConfiguration) conf;
                ret = getCalculator(calculatorId, c.getDataExtractCalculatorBuilderPackagesToSearch());
            }else{
                ret = getCalculator(calculatorId, "org.elsquatrecaps.autonewsextractor.dataextractor.calculators");
            }
        }else{
            ret = getCalculator(calculatorId, "org.elsquatrecaps.autonewsextractor.dataextractor.calculators");
        }
        return ret;
    }
    
//    private <P> ConfigurableAutoNewsExtractorCalculator<P,String> getCalculator(String calculatorId, DataExtractConfiguration conf){
    private <P> AutoNewsExtractorCalculator<P,String> getCalculator(String calculatorId, DataExtractConfiguration conf){
//        ConfigurableAutoNewsExtractorCalculator<P,String> ret;
        AutoNewsExtractorCalculator<P,String> ret;
        ret = getCalculator(calculatorId, conf.getDataExtractCalculatorBuilderPackagesToSearch());
        return ret;
    }
    
//    private static <P> ConfigurableAutoNewsExtractorCalculator<P,String> getCalculator(String calculatorId, String... searchPackages){
    private static <P> AutoNewsExtractorCalculator<P,String> getCalculator(String calculatorId, String... searchPackages){
//        ConfigurableAutoNewsExtractorCalculator<P,String> ret;
        AutoNewsExtractorCalculator<P,String> ret;
        if(builder==null){
            updateBuilder(searchPackages);
        }
        ret = builder.getInstance(calculatorId);
        return ret;
    }
    
    public static DataExtractorCalculatorProxyClass getInstance(Map.Entry<String, Object>... configParams){
        DataExtractorCalculatorProxyClass ret = new DataExtractorCalculatorProxyClass();
        for(Map.Entry<String, Object> entry: configParams){
            ret.init(entry);
        }
        return ret;
    }
    
//    public static DataExtractorCalculatorProxyClass getInstance(Configuration conf, int parserId, ExtractedData extractedData){
//        return new DataExtractorCalculatorProxyClass(conf, extractedData);
//    }
    
//    public static DataExtractorCalculatorProxyClass getInstance(String calculatorId){
//       return new DataExtractorCalculatorProxyClass(getCalculator(calculatorId));
//    }
//    
//    public static DataExtractorCalculatorProxyClass getInstance(String calculatorId, Configuration conf){
//       return new DataExtractorCalculatorProxyClass(getCalculator(calculatorId, conf));
//    }
//
//    public static DataExtractorCalculatorProxyClass getInstance(String calculatorId, Configuration conf, ExtractedData extractedData){
//       return new DataExtractorCalculatorProxyClass(getCalculator(calculatorId, conf));
//    }
//
//    public static DataExtractorCalculatorProxyClass getInstance(String calculatorId, String... searchPackages){
//       return new DataExtractorCalculatorProxyClass(getCalculator(calculatorId, searchPackages));
//    }
//
//    public static DataExtractorCalculatorProxyClass getInstance(String calculatorId, ExtractedData extractedData, String... searchPackages){
//       return new DataExtractorCalculatorProxyClass(getCalculator(calculatorId, searchPackages));
//    }

//    @Override
//    public void init(Object... params) {
//        calculator.init(params);
//    }
//
//    @Override
//    public void init(Configuration conf, Integer parserId, ExtractedData extractedData) {
//        calculator.init(conf, parserId, extractedData);
//    }
//
//    @Override
//    public voiFIELD_VALUES_AS_PARAMSd init(Configuration conf, ExtractedData extractedData) {
//        calculator.init(conf, extractedData);
//    }

    @Override
    public String calculate(JSONObject param) {
//        int currentPos=0;
//        int extraLength=0;
        Object[] params;
        String ret;
//        ConfigurableAutoNewsExtractorCalculator<Object, String> calc = getCalculator(param.getString("calculator"));
        AutoNewsExtractorCalculator<Object, String> calc = getCalculator(param.getString("calculator"));
        if(param.has("init_data")){
            for(int i=0; i<param.getJSONArray("init_data").length(); i++){
                calc.init(extraData.get(param.getJSONArray("init_data").get(i)));
            }
        }
        int length=0;
        int lengthLiterals=0;
        int lengthFields=0;
        int lengthParams=0;
        if(param.has(LITERAL_PARAMS)){
            length += (lengthLiterals=param.getJSONArray(LITERAL_PARAMS).length());
        }
        if(param.has(FIELD_PARAMS)){
            length += (lengthFields = param.getJSONArray(FIELD_PARAMS).length());
        }
        if(param.has(PARAMS)){
            length += (lengthParams = param.getJSONArray(PARAMS).length());
        }
        params = new String[length];
        int id=0;
        for(int i=0; i<lengthParams; i++, id++){
            JSONObject p = param.getJSONArray(PARAMS).getJSONObject(i);
            if(p.getString("type").equals(FIELD_VALUE_PARAM_TYPE)){                
                String[] aParam = p.getString("value").split("\\.");
                if(aParam.length>1){
                    Object obj = extraData.get(aParam[0]);
                    for(int pos=1; pos<aParam.length; pos++){
                        if(obj instanceof ExtractedData){
                            obj = ((ExtractedData)obj).get(aParam[pos]);
                        }else if(obj instanceof Map){
                            obj = ((Map)obj).get(aParam[pos]);
                        }else if(obj instanceof JSONObject){
                            obj = ((JSONObject) obj).get(aParam[pos]);
                        }
                    }
                    params[i] = obj;
                }else{
                    params[i] = extraData.get(aParam[0]);            
                }
            }else{
                params[i] = p.optString("value");
            }
        }
        for(int i=0; i<lengthFields; i++, id++){
            String[] aParam = param.getJSONArray(FIELD_PARAMS).getString(i).split("\\.");
            if(aParam.length>1){
                Object obj = extraData.get(aParam[0]);
                for(int pos=1; pos<aParam.length; pos++){
                    if(obj instanceof ExtractedData){
                        obj = ((ExtractedData)obj).get(aParam[pos]);
                    }else if(obj instanceof Map){
                        obj = ((Map)obj).get(aParam[pos]);
                    }else if(obj instanceof JSONObject){
                        obj = ((JSONObject) obj).get(aParam[pos]);
                    }
                }
                params[id] = obj;
            }else{
                params[id] = extraData.get(aParam[0]);            
            }
        }
        for(int i=0; i < lengthLiterals; id++,i++){
            params[id] = param.getJSONArray(LITERAL_PARAMS).optString(i);
        }
//        
//        if(param.getJSONArray("type").toList().contains(ExtraDataCalculatorEnum.REGEX_CONFIGURABLE.toString())){
//            calc.init(conf, parserId);
//        }else if(param.getJSONArray("type").toList().contains(ExtraDataCalculatorEnum.REGEX_CONFIGURABLE.toString())){
//            calc.init(conf);
//        }
//        if(param.getJSONArray("type").toList().contains(ExtraDataCalculatorEnum.NEWS_PUBLICATION_DATE.toString())){
//            extraLength++;
//        }
//        if(param.getJSONArray("type").toList().contains(ExtraDataCalculatorEnum.FIELD_VALUES_AS_PARAMS.toString())){
//            params = new String[param.getJSONArray("fieldParams").length()+extraLength];
//            for(int i=0; i<param.getJSONArray("fieldParams").length(); i++){
//                params[i] = extractedData.get(param.getJSONArray("fieldParams").getString(i));
//            }
//            currentPos = param.getJSONArray("fieldParams").length();
//        }else if(extraLength>0){
//            params = new String[extraLength];
//        }
//        if(param.getJSONArray("type").toList().contains(ExtraDataCalculatorEnum.NEWS_PUBLICATION_DATE.toString())){
//            params[currentPos++]=String.valueOf(((NewsExtractedData)extractedData).getPublicationDate().getTime());
//        }
        ret = calc.calculate(params);

        return ret;
    }

    @Override
    public String call(JSONObject param) {
        return calculate(param);
    }

    @Override
    public void init(Object obj) {
        if(obj instanceof Configuration){
            Configuration conf = (Configuration) obj;
            init(conf);
        }else if(obj instanceof Integer){
            Integer parserId = (Integer) obj;
            init(parserId);
        }
    }

    public void init(Configuration conf) {
        init(ExtraDataCalculatorEnum.CONFIGURATION.toString(), conf);
    }

    public void init(Integer parserId) {
        init(ExtraDataCalculatorEnum.PARSER_ID.toString(), parserId);
    }
}
