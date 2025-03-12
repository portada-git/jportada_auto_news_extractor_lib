package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

import org.elsquatrecaps.utilities.tools.configuration.Configuration;
import org.json.JSONObject;

/**
 *
 * @author josep
 */
@DataExtractorCalculatorMarkerAnnotation(id = "DataFromConstantMapAndConfigKeyCalculator")
public class DataFromConstantMapAndConfigKeyCalculator extends AbstractCalculator<String>{
    public static final int CONSTANT_MAP_KEY=0;
    public static final int CONFIG_ATTR=1;

    @Override
    public String calculate(Object[] params) {
        String constantMapKey;
        String configAttr;
        String ret=null;
        JSONObject constantMap;
        String configValue;
        constantMapKey = (String) params[CONSTANT_MAP_KEY];
        configAttr = (String) params[CONFIG_ATTR];
        constantMap = getConstants().optJSONObject(constantMapKey);
        if(constantMap!=null){
            configValue = getConfiguration().getAttr(configAttr);
            if(configValue!=null){
                ret = constantMap.getString(configValue);
            }
        }
        return ret;
    }
    
    public JSONObject getConstants(){
        return super.getInitData(CONSTANTS);
    }

    public Configuration getConfiguration(){
        return super.getInitData(CONFIGURATION);
    }

}
