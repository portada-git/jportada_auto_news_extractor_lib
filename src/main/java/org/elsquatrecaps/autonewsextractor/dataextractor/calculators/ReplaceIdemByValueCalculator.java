package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

import org.elsquatrecaps.autonewsextractor.tools.RegexBuilder;

/**
 *
 * @author josep
 */
@DataExtractorCalculatorMarkerAnnotation(id = "ReplaceIdemByValueCalculator")
public class ReplaceIdemByValueCalculator extends RegexCalculator<String[], String>{
    public static final int VALUE_TO_CHECK=0;
    public static final int VALUE_TO_REPLACE=1;
    

    @Override
    public String calculate(String[] params) {
        String ret="";
        if(params[VALUE_TO_REPLACE]!= null && !params[VALUE_TO_REPLACE].matches("\\?+")){
            String strIdemPattern = RegexBuilder.getInstance(this.getBasePath(), this.getSearchPath(), this.getVariant()).getStrPatternFromFile("idem");
            String backTime = params[VALUE_TO_CHECK];

            if(backTime.matches(strIdemPattern)){   
                ret = params[VALUE_TO_REPLACE];
            }     
        }
        return ret;
    }
}
