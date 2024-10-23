package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

import org.elsquatrecaps.utilities.tools.configuration.Configuration;
import org.json.JSONObject;

/**
 *
 * @author josep
 */
@DataExtractorCalculatorMarkerAnnotation(id = "DataFromConstantMapAndConfigKeyCalculator")
public class DataFromConstantMapAndConfigKeyCalculator extends AbstractCalculator<String[], String>{
    private JSONObject constants;
    private Configuration conf;
    public static final int CONSTANT_MAP_KEY=0;
    public static final int CONFIG_ATTR=1;
    
    
    @Override
    public void init(Object obj){
        if(obj instanceof JSONObject){
            JSONObject cons = (JSONObject) obj;
            init(cons);
        }else if(obj instanceof Configuration){
            Configuration cons = (Configuration) obj;
            init(cons);
        }else{
            super.init(obj);
        }
    }
    
    public void init(JSONObject obj){
        constants=obj;
    }
    
    public void init(Configuration obj){
        conf=obj;
    }
    
    @Override
    public String calculate(String[] params) {
        String constantMapKey;
        String configAttr;
        String ret=null;
        JSONObject constantMap;
        String configValue;
        constantMapKey = params[CONSTANT_MAP_KEY];
        configAttr = params[CONFIG_ATTR];
        constantMap = constants.optJSONObject(constantMapKey);
        if(constantMap!=null){
            configValue = conf.getAttr(configAttr);
            if(configValue!=null){
                ret = constantMap.getString(configValue);
            }
        }
        return ret;
    }

}
