package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author josep
 */
public enum ExtraDataCalculatorEnum {
    CONFIGURATION,
    PARSER_ID,
    EXTRACTED_DATA,
    LAST_EXTRACTED_DATA,
    CONSTANTS;
    
    private static final Map<String, ExtraDataCalculatorEnum> enumForNames = new HashMap<>();
    static{
        for(ExtraDataCalculatorEnum e: ExtraDataCalculatorEnum.values()){
            enumForNames.put(e.toString(), e);
        }
    }

    public static ExtraDataCalculatorEnum getValue(String v){
        return enumForNames.get(v.toLowerCase());
    }
    

    @Override
    public String toString(){
        return this.name().toLowerCase();
    }
}
