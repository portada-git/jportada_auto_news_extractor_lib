package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import org.elsquatrecaps.autonewsextractor.dataextractor.calculators.DataExtractorCalculatorMarkerAnnotation;
import org.elsquatrecaps.autonewsextractor.dataextractor.calculators.RegexCalculator;

/**
 *
 * @author josep
 */
@DataExtractorCalculatorMarkerAnnotation(id = "PreviousDateFromElapsedDaysCalculator")
public class PreviousDateFromElapsedDaysCalculator extends RegexCalculator<String[], String>{
    public static final int ELAPSED_DAYS=0;
    public static final int DATE=1;
    public static final int DATE_FORMAT=2;
    

    @Override
    public String calculate(String[] params) {
        int backTime;
        String ret;
        Date date;
        try{
            if(params.length<3 || params[DATE_FORMAT].isEmpty()){
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
            try{
                backTime = Integer.parseInt(params[ELAPSED_DAYS]);
                Instant instant = Instant.ofEpochMilli(date.getTime());
                instant = instant.minus(backTime, ChronoUnit.DAYS);
                ret = String.format("%tY-%<tm-%<td", instant.toEpochMilli());
            }catch(NumberFormatException ex){
                ret = String.format("(%tY-%<tm-%<td)-(%s d)", date, params[ELAPSED_DAYS]);
            }                
        }catch(ParseException ex){
            ret = String.format("(%s)-(%s d.)", params[DATE], params[ELAPSED_DAYS]);            
        }catch(NumberFormatException ex){
            ret = "????";
        }
        return ret;
    }
}
