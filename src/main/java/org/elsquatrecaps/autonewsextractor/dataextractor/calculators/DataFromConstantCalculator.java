package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

import org.elsquatrecaps.utilities.tools.configuration.Configuration;
import org.json.JSONObject;

/**
 *
 * @author josep
 */
@DataExtractorCalculatorMarkerAnnotation(id = "DataFromConstantCalculator")
public class DataFromConstantCalculator extends AbstractCalculator<String[], String>{
    public static final int CONSTANT_KEY=0;
    
    @Override
    public String calculate(String[] params) {
        String constantKey;
        String ret;
        constantKey = params[CONSTANT_KEY];
        ret = getConstats().optString(constantKey, "");
        return ret;
    }
    
    public JSONObject getConstats(){
        return getInitData(CONSTANTS);
    }
}
