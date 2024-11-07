package org.elsquatrecaps.autonewsextractor.targetfragmentbreaker.cutter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.elsquatrecaps.autonewsextractor.tools.RegexBuilder;
import org.elsquatrecaps.autonewsextractor.tools.configuration.RegexConfiguration;
import org.elsquatrecaps.autonewsextractor.tools.configuration.TargetFragmentBreakerConfiguration;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;

/**
 *
 * @author josep
 */
@TargetFragmentCutterMarkerAnnotation(fragmentCutterApproach = "regex")
public class TragetFragmentCutterByRegex implements TargetFragmentCutter{
    private TargetFragmentBreakerConfiguration especificConfigurator;
    private Integer parserModel;
    
    @Override
    public <Object> TragetFragmentCutterByRegex init(Object v){
        if(v instanceof Configuration){
            init((Configuration) v);
        }else if(v instanceof Integer){
            init((Integer) v);
        }
        return this;
    }        
    
    public TragetFragmentCutterByRegex init(Integer model){
        parserModel =  model;
        return this;
    }        
    
    public TragetFragmentCutterByRegex init(Configuration conf){
        especificConfigurator = (TargetFragmentBreakerConfiguration) conf;
        return this;
    }        
    
    @Override
    public String getTargetTextFromText(String bonText){
        String ret=bonText;
        RegexConfiguration regexConfiguration = (RegexConfiguration) especificConfigurator;
        Pattern pattern = RegexBuilder.getInstance(regexConfiguration, parserModel).buildRegex("fragment_initial_detector");
        Matcher matcher = pattern.matcher(bonText);
        if(matcher.find()){
            String g1;
            String g2;
            int groups = matcher.groupCount();
            if(groups==3){
                g1 = matcher.group(2).trim();
                g2 = matcher.group(3).trim();
            }else{
                g1 = matcher.group(1).trim();
                g2 = matcher.group(2).trim();
            }
            Pattern patternEnd = RegexBuilder.getInstance(regexConfiguration, parserModel).buildRegex("fragment_end_detector");
            Matcher matcherEnd = patternEnd.matcher(g2);
            if(matcherEnd.find()){
                g2 = matcherEnd.replaceAll("$1").trim();
            }
            ret = g1.concat("\n").concat(g2);
            
        }else{
            Pattern patternEnd = RegexBuilder.getInstance(regexConfiguration, parserModel).buildRegex("fragment_end_detector");
            Matcher matcherEnd = patternEnd.matcher(ret);
            if(matcherEnd.find()){
                ret = matcherEnd.replaceAll("$1");
            }
        }
        return ret;
    }
}
