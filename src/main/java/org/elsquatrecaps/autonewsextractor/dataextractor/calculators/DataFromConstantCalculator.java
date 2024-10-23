package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

import org.elsquatrecaps.utilities.tools.configuration.Configuration;
import org.json.JSONObject;

/**
 *
 * @author josep
 */
@DataExtractorCalculatorMarkerAnnotation(id = "DataFromConstantCalculator")
public class DataFromConstantCalculator extends AbstractCalculator<String[], String>{
    private JSONObject constants;
    public static final int CONSTANT_KEY=0;
    
    
    @Override
    public void init(Object obj){
        if(obj instanceof JSONObject){
            JSONObject cons = (JSONObject) obj;
            init(cons);
        }
    }
    
    public void init(JSONObject obj){
        constants=obj;
    }
    
    @Override
    public String calculate(String[] params) {
        String constantKey;
        String ret;
        constantKey = params[CONSTANT_KEY];
        ret = constants.optString(constantKey, "");
        return ret;
    }

}
