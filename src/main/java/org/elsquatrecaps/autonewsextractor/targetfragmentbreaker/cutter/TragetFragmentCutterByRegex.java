package org.elsquatrecaps.autonewsextractor.targetfragmentbreaker.cutter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.elsquatrecaps.autonewsextractor.tools.RegexBuilder;
import org.elsquatrecaps.autonewsextractor.tools.configuration.RegexConfiguration;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;
import org.slf4j.helpers.MessageFormatter;
import org.elsquatrecaps.autonewsextractor.tools.configuration.TargetFragmentCutterConfiguration;

/**
 *
 * @author josep
 */
@TargetFragmentCutterMarkerAnnotation(fragmentCutterApproach = "regex")
public class TragetFragmentCutterByRegex implements TargetFragmentCutter{
    private TargetFragmentCutterConfiguration especificConfigurator;
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
        especificConfigurator = (TargetFragmentCutterConfiguration) conf;
        return this;
    }        
    
    @Override
    public String getTargetTextFromText(String bonText){
        String ret=bonText;
        RegexConfiguration regexConfiguration = (RegexConfiguration) especificConfigurator;
        Pattern pattern = RegexBuilder.getInstance(regexConfiguration, parserModel).buildRegex("fragment_initial_detector");
        Matcher matcher = pattern.matcher(bonText);
        if(matcher.find()){
            String g1 = "";
            String g2 = bonText;
            int groups = matcher.groupCount();
            boolean found = groups==2 || groups==3;
            switch (groups) {
                case 3:
                    g1 = matcher.group(2).trim();
                    g2 = matcher.group(3).trim();
                    break;
                case 2:
                    g1 = matcher.group(1).trim();
                    g2 = matcher.group(2).trim();
                    break;
                default:
                    for(int inc=0; !found && inc<30; inc+=3){
                        if(matcher.group(1+inc)!=null){
                            g1= matcher.group(2+inc);
                            g2 = matcher.group(3+inc).trim();
                            found=true;
                        }
                    }   
                    break;
            }
            if(found){
                g2 = getTheEndOfText(g2);
                String nex="\n";
                if(g1.endsWith("\n")||g2.startsWith("\n")){
                    nex="";
                }
                ret = g1.concat(nex).concat(g2);
            }            
        }else{
            ret = getTheEndOfText(ret);
        }
        return ret;
    }

    private String getTheEndOfText(String text){
        String ret = text;
        RegexConfiguration regexConfiguration = (RegexConfiguration) especificConfigurator;
        Pattern patternEnd = RegexBuilder.getInstance(regexConfiguration, parserModel).buildRegex("fragment_end_detector");
        Matcher matcher = patternEnd.matcher(text);
        if(matcher.find()){
            int groups = matcher.groupCount();
            boolean found = groups==1 || groups==2;
            switch (groups) {
                case 2:
                case 1:
                    ret = matcher.group(1).trim();
                    break;
                default:
                    for(int inc=0; !found && inc<20; inc+=2){
                        if(matcher.group(1+inc)!=null){
                            ret= matcher.group(1+inc).trim();
                            found=true;
                        }
                    }   
                    break;
            }
        }
        return ret;
    }
}
