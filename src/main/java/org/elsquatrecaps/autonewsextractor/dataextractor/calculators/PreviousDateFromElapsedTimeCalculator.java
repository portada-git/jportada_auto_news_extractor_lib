package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.elsquatrecaps.autonewsextractor.dataextractor.calculators.DataExtractorCalculatorMarkerAnnotation;
import org.elsquatrecaps.autonewsextractor.dataextractor.calculators.DataExtractorCalculatorMarkerAnnotation;
import org.elsquatrecaps.autonewsextractor.dataextractor.calculators.RegexCalculator;
import org.elsquatrecaps.autonewsextractor.dataextractor.calculators.RegexCalculator;

/**
 *
 * @author josep
 */
@DataExtractorCalculatorMarkerAnnotation(id = "PreviousDateFromElapsedTimeCalculator")
public class PreviousDateFromElapsedTimeCalculator extends RegexCalculator<String[], String>{
    public static final int ELAPSED_TIME=0;
    public static final int ELAPSED_TIME_UNIT=1;
    public static final int DATE=2;
    public static final int DATE_FORMAT=3;
    

    @Override
    public String calculate(String[] params) {
        String ret="";
        Date date;
        try {
            if(params.length<4 || params[DATE_FORMAT].isEmpty()){
                SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd"); 
                try{
                    date = formater.parse(params[DATE]);
                }catch(ParseException ex){
                    date = new Date(Long.parseLong(params[DATE]));
                }
            }else{
                SimpleDateFormat formater = new SimpleDateFormat(params[DATE_FORMAT]);            
                date = formater.parse(params[DATE]);
            }
            Instant instant = Instant.ofEpochMilli(date.getTime());
            int intBackTime=Integer.parseInt(params[ELAPSED_TIME]);
            if(params[ELAPSED_TIME_UNIT]!=null && params[ELAPSED_TIME_UNIT].toLowerCase().startsWith("h")){
                instant = instant.minus(intBackTime, ChronoUnit.HOURS);
            }else{
                instant = instant.minus(intBackTime, ChronoUnit.DAYS);
            }
            ret = String.format("%tY-%<tm-%<td", instant.toEpochMilli());
        } catch (NumberFormatException | ParseException ex) {
            ret = String.format("(%s)-(%s %s)", params[DATE], params[ELAPSED_TIME], params[ELAPSED_TIME_UNIT]);
        }
        return ret;
    }
}
