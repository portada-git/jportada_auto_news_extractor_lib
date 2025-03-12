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
public class DataExtractorCalculatorProxyClass /*implements AutoNewsExtractorCalculator<JSONObject, Object>*/{
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

    public DataExtractorCalculatorProxyClass() {
    }
    
    public void init(String key, Object value){
        extraData.put(key, value);
    }
    
    public void init(Map.Entry<String, Object> entry){
        extraData.put(entry.getKey(), entry.getValue());
    }
    
//    private <P> ConfigurableAutoNewsExtractorCalculator<P,String> getCalculator(String calculatorId){
    private <R> AutoNewsExtractorCalculator<R> getCalculator(String calculatorId){
        AutoNewsExtractorCalculator<R> ret;
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
    private <R> AutoNewsExtractorCalculator<R> getCalculator(String calculatorId, DataExtractConfiguration conf){
//        ConfigurableAutoNewsExtractorCalculator<P,String> ret;
        AutoNewsExtractorCalculator<R> ret;
        ret = getCalculator(calculatorId, conf.getDataExtractCalculatorBuilderPackagesToSearch());
        return ret;
    }
    
    private static <R> AutoNewsExtractorCalculator<R> getCalculator(String calculatorId, String... searchPackages){
        AutoNewsExtractorCalculator<R> ret;
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
    
//    @Override
    public <R> R calculate(JSONObject param) {
//        int currentPos=0;
//        int extraLength=0;
        Object[] params;
        R ret;
//        ConfigurableAutoNewsExtractorCalculator<Object, String> calc = getCalculator(param.getString("calculator"));
        AutoNewsExtractorCalculator<R> calc = getCalculator(param.getString("calculator"));
        if(param.has("init_data")){
            for(int i=0; i<param.getJSONArray("init_data").length(); i++){
                Object initData = param.getJSONArray("init_data").get(i);
                if(initData instanceof String){
                    calc.init(extraData.get(initData));
                }else{
                    calc.init(initData);
                }
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
        params = new Object[length];
        int id=0;
        for(int i=0; i<lengthParams; i++, id++){
            JSONObject p = param.getJSONArray(PARAMS).getJSONObject(i);
            if(p.getString("type").equals(FIELD_VALUE_PARAM_TYPE)){                
                String[] aParam = p.getString("value").split("\\.");
                if(aParam.length>1){
                    Object obj = extraData.get(aParam[0]);
                    if(obj != null){
                        for(int pos=1; pos<aParam.length; pos++){
                            if(obj instanceof ExtractedData){
                                obj = ((ExtractedData)obj).getAsObject(aParam[pos]);
                            }else if(obj instanceof Map){
                                obj = ((Map)obj).get(aParam[pos]);
                            }else if(obj instanceof JSONObject){
                                obj = ((JSONObject) obj).get(aParam[pos]);
                            }
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
                if(obj != null){
                    for(int pos=1; pos<aParam.length; pos++){
                        if(obj instanceof ExtractedData){
                            obj = ((ExtractedData)obj).getAsObject(aParam[pos]);
                        }else if(obj instanceof Map){
                            obj = ((Map)obj).get(aParam[pos]);
                        }else if(obj instanceof JSONObject){
                            obj = ((JSONObject) obj).get(aParam[pos]);
                        }
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

        ret = calc.calculate(params);

        return ret;
    }

//    @Override
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
