package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

import org.elsquatrecaps.autonewsextractor.tools.RegexBuilder;

/**
 *
 * @author josep
 */
@DataExtractorCalculatorMarkerAnnotation(id = "ReplaceIdemByValueCalculator")
public class ReplaceIdemByValueCalculator extends RegexCalculator<String>{
    public static final int VALUE_TO_CHECK=0;
    public static final int VALUE_TO_REPLACE=1;
    

    @Override
    public String calculate(Object[] params) {
        String ret="";
        if(!isEmptyParam(params[VALUE_TO_REPLACE]) && !((String)params[VALUE_TO_REPLACE]).matches("\\?+")){
            String strIdemPattern = RegexBuilder.getInstance(this.getBasePath(), this.getSearchPath(), this.getVariant()).getStrPatternFromFile("idem");
            String backTime = ((String)params[VALUE_TO_CHECK]);

            if(backTime.matches(strIdemPattern)){   
                ret = ((String)params[VALUE_TO_REPLACE]);
            }     
        }
        return ret;
    }
}
