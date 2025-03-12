package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

import java.util.HashMap;
import java.util.Map;
import org.elsquatrecaps.autonewsextractor.model.MutableNewsExtractedData;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;
import org.json.JSONObject;

/**
 *
 * @author josep
 * @param <P>
 * @param <R>
 */
//public abstract class AbstractCalculator<P,R> implements ConfigurableAutoNewsExtractorCalculator<P, R>{
public abstract class AbstractCalculator<R> implements AutoNewsExtractorCalculator<R>{
    public static final String CONFIGURATION=Configuration.class.getName();
    public static final String PARSER_ID=Integer.class.getName();
    public static final String EXTRACTED_DATA=MutableNewsExtractedData.class.getName();
    public static final String CONSTANTS=JSONObject.class.getName();
    private final Map<String, Object> initData = new HashMap<>();
        
    @Override
    public R call(Object[] param) {
        return calculate(param);
    }    

    @Override
    public void init(Object initdata) {
        if(initdata instanceof Configuration){
            init(CONFIGURATION, initdata);
        }else if(initdata instanceof Integer){
            init(PARSER_ID, initdata);
        }else if(initdata instanceof MutableNewsExtractedData){
            init(EXTRACTED_DATA, initdata);
        }else if(initdata instanceof JSONObject){
            init(CONSTANTS, initdata);
        }else{
            init(initdata.getClass().getCanonicalName(), initdata);
        }
    }

    protected void init(String key, Object conf) {
        initData.put(key, conf);
    }
    
    protected <T> T getInitData(String key){
        return (T) initData.get(key);
    }
    
    protected boolean isEmptyParam(Object param){
        boolean ret = param == null;
        if(ret && param instanceof String){
            ret = ((String)param).isEmpty() || ((String)param).isBlank();
        }
        return ret;
    }
}
