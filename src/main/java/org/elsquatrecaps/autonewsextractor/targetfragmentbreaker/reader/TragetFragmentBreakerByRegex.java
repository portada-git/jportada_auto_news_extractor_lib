package org.elsquatrecaps.autonewsextractor.targetfragmentbreaker.reader;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.elsquatrecaps.autonewsextractor.error.AutoNewsReaderRuntimeException;
import org.elsquatrecaps.autonewsextractor.tools.ReaderTools;
import org.elsquatrecaps.autonewsextractor.tools.RegexBuilder;
import org.elsquatrecaps.autonewsextractor.tools.configuration.Configurable;
import org.elsquatrecaps.autonewsextractor.tools.configuration.RegexConfiguration;
import org.elsquatrecaps.autonewsextractor.tools.configuration.TargetFragmentBreakerConfiguration;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;

/**
 *
 * @author josep
 */
@TargetFragmentBreakerMarkerAnnotation(fragmentBreakerApproach = "regex")
public abstract class TragetFragmentBreakerByRegex extends AbstractReader implements Configurable{
    static Map<String, Boolean> isAppendFile = new HashMap<>();
    private TargetFragmentBreakerConfiguration especificConfigurator;
    
    @Override
    public void init(Configuration conf){
        especificConfigurator = (TargetFragmentBreakerConfiguration) conf;
    }        
    
    protected String getTargetTextFromText(String bonText){
        String ret=bonText;
        RegexConfiguration regexConfiguration = (RegexConfiguration) especificConfigurator;
        Pattern pattern = RegexBuilder.getInstance(regexConfiguration.getRegexBasePath(), 
                regexConfiguration.getFactModel()).buildRegex("fragment_initial_detector");
        Matcher matcher = pattern.matcher(bonText);
        if(matcher.find()){
            String g3 = matcher.group(3);
            Pattern patternEnd = RegexBuilder.getInstance(regexConfiguration.getRegexBasePath(), 
                regexConfiguration.getFactModel()).buildRegex("fragment_end_detector");
            Matcher matcherEnd = patternEnd.matcher(g3);
            if(matcherEnd.find()){
                g3 = matcherEnd.replaceAll("$1");
            }
            ret = matcher.replaceAll("$1\n$2\n".concat(g3));
            
        }else{
            Pattern patternEnd = RegexBuilder.getInstance(regexConfiguration.getRegexBasePath(), 
                regexConfiguration.getFactModel()).buildRegex("fragment_end_detector");
            Matcher matcherEnd = patternEnd.matcher(ret);
            if(matcherEnd.find()){
                ret = matcherEnd.replaceAll("$1");
            }
        }
        return ret;
    }
    
    @Override
  protected String prepareTextFromFile(String file) {
        String rawText;
        String bonText;
        String regex_name = "is_page_number";
        try {
            RegexConfiguration regexConfiguration = (RegexConfiguration) especificConfigurator;        
            Pattern pattern = RegexBuilder.getInstance(regexConfiguration).buildRegex(regex_name);
            rawText = ReaderTools.textFileRawContent(especificConfigurator.getOriginDir(), file);
            rawText = rawText.replaceAll("\r\n", "\n");
            while (pattern.matcher(rawText).matches()) {
                rawText = rawText.substring(rawText.indexOf("\n") + 1);
            }
            bonText = ReaderTools.singleLf2Space(rawText);
            bonText = ReaderTools.doubleLf2SingleLf(bonText);
        } catch (PatternSyntaxException ex) {
            throw new AutoNewsReaderRuntimeException(String.format("The expression regular named '%s' is bad. Please, revise it", regex_name));
        } catch (IOException ex) {
//            Logger.getLogger(TragetFragmentBreakerByRegex.class.getName()).log(Level.SEVERE, null, ex);
            throw new AutoNewsReaderRuntimeException(String.format("The file %s doesn't exist or can't be read", file), ex);
        }
        return bonText;
    }

    
    
    @Override
    protected String file2Text(String file){
        String ret = "";
//        String ret = this.prepareBoatFactTextFromText(prepareTextFromFile(file));
        return ret;
    }
    
//    @Override
//    protected void saveDataFromText(String bonText, Date date) { 
//        String lastCountry="";
//        String pModel;
//        File f = new File(especificConfigurator.getOutputFile());
//        File fp = f.getParentFile();
//        String name = f.getName();
//        FileFormatter<Fact> factFormatter;
//        List<Fact> factList=null;
//        List<BoatFactInfoTextByCountryHandler> infoTextByCountryHandlers = getHandlersFromString(bonText, date);
//        if(!infoTextByCountryHandlers.isEmpty()){
//            for(BoatFactInfoTextByCountryHandler  handler: infoTextByCountryHandlers){
//                 pModel = handler.getParseModel();
//                 if(factList!= null && !factList.isEmpty()){
//                     handler.setLastParsedData(factList.get(factList.size()-1));
//                 }else{
////                     BoatFact bf = new BoatFact();
////                     bf.setBoat(new Boat(lastCountry, null, null));
////                     handler.setLastParsedData(bf);
//                 }
//                 factList = (List<Fact>) handler.getFactList();
//                 factFormatter = (FileFormatter<Fact>) handler.getCsvFormater();
//                 f = new File(fp, handler.getParseModel().concat(name));
//                 if(!isAppendFile.containsKey(pModel)){
//                     isAppendFile.put(pModel, AppArguments.getConfigutation().isAppendOutputFile());
//                 }
//                 factFormatter.setAppendFile(isAppendFile.get(pModel));
//                 factFormatter.format(factList);
//                 factFormatter.toFile(f.getPath());
//                 isAppendFile.put(pModel, Boolean.TRUE);
////                 lastCountry = 
//            }
//        }else{
//            String msg;
//            msg = String.format("Warning. A la data %1$te-%1$tm-%1$tY no s'ha trobat cap bandera.", date);
//            Logger.getLogger(TragetFragmentBreakerByRegex.class.getName()).log(Level.WARNING, msg);
//        }
//    }
    
//    protected  List<BoatFactInfoTextByCountryHandler> getHandlersFromString(String bonText, Date date){
//        int maxGroups = 12;
//        boolean found;
//        AutoNewsExtractorConfiguration arguments=AppArguments.getConfigutation();
//        int elapsedDaysFromArrival=1;
//        String country=null;
//        int firstPos=0;
//        int endPos;
//        String text;
//        String[] parseModels = arguments.getParseModel();
//        List<BoatFactInfoTextByCountryHandler> parsers = new ArrayList<>();        
//        for(String parseModel:parseModels){
//            String regexPath = String.format("%s/%s/%s/%s",  
//                arguments.getFactModel(),
//                arguments.getNewspaper(),
//                parseModel,
//                arguments.getRegexModel());
//            RegexBuilder regexBuilder = new RegexBuilder(arguments.getRegexBasePath(),regexPath);       //ALERTA! NONËS RPROVES!         
//            Pattern handleByCountry = regexBuilder.buildRegex("flag");
//
//            Matcher matcher = handleByCountry.matcher(bonText);
//
//            if(matcher.find()){
//                found = false;
//                for(int inc=0; !found && inc<maxGroups; inc+=2){
//                    if(matcher.group(1+inc)!=null){
//                        elapsedDaysFromArrival = calculateElapsedTime(matcher.group(1+inc));
//                        country = matcher.group(2+inc);
//                        found=true;
//                    }else if(matcher.group(2+inc)!=null){
//                        country = matcher.group(2+inc);
//                        found=true;
//                    }
//                }
//                firstPos = matcher.end();
//            }        
//            while(matcher.find()){
//                endPos = matcher.start();
//                text = bonText.substring(firstPos, endPos);
//                parsers.add(
//                    new BoatFactInfoTextByCountryHandler(
//                        text,
//                        new ProxyFactParserForEmbarcacionesLLegadas<>(parseModel, arguments.getRegexBasePath(), regexPath, country, date, elapsedDaysFromArrival)
//                    )
//                );
//                found = false;
//                for(int inc=0; !found && inc<maxGroups; inc+=2){
//                    if(matcher.group(1+inc)!=null){
//                        elapsedDaysFromArrival = calculateElapsedTime(matcher.group(1+inc), elapsedDaysFromArrival);
//                        country = matcher.group(2+inc);
//                        found=true;
//                    }else if(matcher.group(2+inc)!=null){
//                        country = matcher.group(2+inc);
//                        found=true;
//                    }
//                }
//                firstPos = matcher.end();            
//            }
//            if(country==null){
//                country="???";
//            }        
//            text = bonText.substring(firstPos);
//            parsers.add(
//                    new BoatFactInfoTextByCountryHandler(
//                            text,
//                            new ProxyFactParserForEmbarcacionesLLegadas<>(parseModel, arguments.getRegexBasePath(), regexPath, country, date, elapsedDaysFromArrival)
//
//                    )
//            );
//        }
//        return parsers;
//    }
//    
//    
//    private int calculateElapsedTime(String when, int lastWhen){
//        int elapsedDaysFromArrival;
//        if(when.matches(RegexBuilder.getInstance().getStrPatternFromFile("start_with_idem"))){
//            elapsedDaysFromArrival=lastWhen;
//        }else{
//            elapsedDaysFromArrival = calculateElapsedTime(when);
//        }
//         return elapsedDaysFromArrival;
//    }
//    
//    private int calculateElapsedTime(String when){
//        int elapsedDaysFromArrival=1;
//        when = when.replace('\n', ' ');
//        if(when.matches(RegexBuilder.getInstance().getStrPatternFromFile("contains_anteayer"))){
//            elapsedDaysFromArrival = 2;
//        }else if(when.matches(RegexBuilder.getInstance().getStrPatternFromFile("contains_ayer"))){
//            elapsedDaysFromArrival=1;
//        }else if(when.matches(RegexBuilder.getInstance().getStrPatternFromFile("contains_hoy"))){
//            elapsedDaysFromArrival=0;
//        }
//        return elapsedDaysFromArrival;
//    }
}
