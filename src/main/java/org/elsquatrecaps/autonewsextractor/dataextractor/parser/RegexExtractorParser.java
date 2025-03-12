package org.elsquatrecaps.autonewsextractor.dataextractor.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.elsquatrecaps.autonewsextractor.dataextractor.calculators.DataExtractorCalculatorProxyClass;
import org.elsquatrecaps.autonewsextractor.dataextractor.calculators.ExtraDataCalculatorEnum;
import org.elsquatrecaps.autonewsextractor.model.ExtractedData;
import org.elsquatrecaps.autonewsextractor.model.ImmutableNewsExtractedData;
import org.elsquatrecaps.autonewsextractor.model.MutableNewsExtractedData;
import org.elsquatrecaps.autonewsextractor.tools.RegexBuilder;
import org.elsquatrecaps.autonewsextractor.tools.configuration.RegexConfiguration;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;
import org.elsquatrecaps.utilities.tools.configuration.DevelopmentConfiguration;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author josep
 * @param <E>
 */
@ParserMarkerAnnotation(approach = "regex")
public class RegexExtractorParser<E extends ExtractedData> implements ExtractorParserApproach<E>{
    private static final String LAST_EXTRACTED_DATA = "last_extracted_data";
    String basePath;
    String searchPath;
    String variant;
    String mainRegex;
    int maxGroups;
//    String[] fiedsToExtract;
//    String[] defaultFieldValues;
//    boolean[] copyLastValues;
//    String[] calculators;
    Configuration configuration;
    int parserId;
    JSONArray fieldsToExtract;
    JSONArray fieldsToCalculate;
    JSONObject constants;
    E lastParsed=null;
    
    @Override
    public void init(Configuration configuration, int parserId) {
        RegexConfiguration conf = (RegexConfiguration)  configuration;
        this.basePath = conf.getRegexBasePath();
        this.searchPath = conf.getFactModel()
                .concat("/").concat(conf.getNewspaper())
                .concat("/").concat(conf.getParseModel()[parserId]);
        this.variant = conf.getOcrEngineModel();
        this.configuration = conf;
        this.parserId = parserId;
    }
       
    @Override
    public void init(JSONObject jsonConfig) {
        if(jsonConfig.has("main_regex")){
            initJsonCOnfig(jsonConfig);
        }else{
            initJsonConstants(jsonConfig);
        }
    }

    private void initJsonConstants(JSONObject constants) {
        this.constants = constants;
    }
    
    private void initJsonCOnfig(JSONObject jsonConfig) {
        this.mainRegex = jsonConfig.getString("main_regex");
        this.maxGroups = jsonConfig.getInt("max_groups");
        this.fieldsToExtract = jsonConfig.getJSONArray("fields_to_extract");
        this.fieldsToCalculate = jsonConfig.optJSONArray("fields_to_calculate");
    }
    
    @Override
    public void setLastParsed(E parsedList){
        this.lastParsed = parsedList;
    }
    
    protected MutableNewsExtractedData parseDataFromMatcher(Matcher matcher, MutableNewsExtractedData partialExtracted, ImmutableNewsExtractedData lastExtracted){
        boolean found;
        MutableNewsExtractedData parseddata = partialExtracted.clone();

        found = false;
        for(int inc=0; !found && inc<maxGroups; inc+=fieldsToExtract.length()){
            for(int i=0; i<fieldsToExtract.length(); i++){
                if(matcher.group(i+1+inc)!=null){
                    parseddata.set(fieldsToExtract.getJSONObject(i).getString("key"), matcher.group(i+1+inc).trim());
                    found=true;
                }
            }
        }
        if(fieldsToCalculate!=null){
            for(int i=0; i<fieldsToCalculate.length(); i++){
                DataExtractorCalculatorProxyClass proxy = DataExtractorCalculatorProxyClass.getInstance();
                proxy.init(ExtraDataCalculatorEnum.CONFIGURATION.toString(), configuration);
                proxy.init(ExtraDataCalculatorEnum.PARSER_ID.toString(), parserId);
                proxy.init(ExtraDataCalculatorEnum.EXTRACTED_DATA.toString(), parseddata);
                proxy.init(ExtraDataCalculatorEnum.LAST_EXTRACTED_DATA, lastExtracted);
                proxy.init(ExtraDataCalculatorEnum.CONSTANTS.toString(), constants);
                Object value = proxy.calculate(fieldsToCalculate.getJSONObject(i));
                if(value instanceof String){
                    value = ((String) value).trim();
                }
                parseddata.setCalculateValue(fieldsToCalculate.getJSONObject(i).getString("key"), value);
            }        
        }
        return parseddata;
    }
    
    @Override
    public List<E> parseFromString(String bonText, MutableNewsExtractedData partialExtractedDataToCopy){
        List<E> ret = new ArrayList<>();
        int firstPos=0;
        int endPos;
        String textParsed = null;        
        String textUnparsed;        
        MutableNewsExtractedData parseddata = null;
        Pattern partialPattern = RegexBuilder.getInstance(basePath, searchPath, variant).buildRegex(mainRegex);
        if(configuration instanceof DevelopmentConfiguration && ((DevelopmentConfiguration)configuration).getRunForDebugging()){
            System.out.println(String.format("\n==============\n\nPattern (from %s): \"%s\"\n\n--------------\n", mainRegex, partialPattern.toString()));
            System.out.println(String.format("Raw text: \"%s\"\n\n--------------\n", bonText));
        }
        Matcher matcher = partialPattern.matcher(bonText);
        if(matcher.find()){
            parseddata = parseDataFromMatcher(matcher, partialExtractedDataToCopy, parseddata);
            textParsed = bonText.substring(matcher.start(), matcher.end()).trim();
            firstPos = matcher.end();            
        }
        while(matcher.find()){
            endPos = matcher.start();
            textUnparsed = bonText.substring(firstPos, endPos).trim();
            parseddata.setParsedText(textParsed);
            parseddata.setUnparsedText(textUnparsed);
            ret.add((E) parseddata);
            printForDebbuging(textParsed, textUnparsed, parseddata);
            for(int i=0; i<fieldsToExtract.length(); i++){
                if(!fieldsToExtract.getJSONObject(i).has("copy_last_value") || fieldsToExtract.getJSONObject(i).getBoolean("copy_last_value")){
                    partialExtractedDataToCopy.setCalculateValue(
                        fieldsToExtract.getJSONObject(i).getString("key"), 
                        parseddata.get(fieldsToExtract.getJSONObject(i).getString("key"))
                    );
                }
            }

            parseddata = parseDataFromMatcher(matcher, partialExtractedDataToCopy, parseddata);
            textParsed = bonText.substring(matcher.start(), matcher.end()).trim();
            firstPos = matcher.end();            
        }
        if(parseddata!=null){
            textUnparsed = bonText.substring(firstPos).trim();
            parseddata.setParsedText(textParsed);
            parseddata.setUnparsedText(textUnparsed);
            ret.add((E) parseddata);
            printForDebbuging(textParsed, textUnparsed, parseddata);
        }
        return ret;
    }

    private void printForDebbuging(String textParsed, String textUnparsed, MutableNewsExtractedData parseddata){
        if(configuration instanceof DevelopmentConfiguration && ((DevelopmentConfiguration)configuration).getRunForDebugging()){
            System.out.println(String.format("Parsed text: \"%s\"\n\n--------------\n", textParsed));
            System.out.println(String.format("Unparsed text: \"%s\"\n\n--------------\n", textUnparsed));
            System.out.println(parseddataToStringForDebugging(parseddata));
            System.out.println("\n\n--------------\n");
        }        
    }
    
    private String parseddataToStringForDebugging(MutableNewsExtractedData parseddata){
        StringBuilder strb = new StringBuilder();
        strb.append("Extracted data:\n");
        strb.append("\t -Original field values:\n");
        for(int i=0; i<fieldsToExtract.length(); i++){
            strb.append("\t\t --");
            strb.append(fieldsToExtract.getJSONObject(i).getString("key"));
            strb.append(": ");
            strb.append(parseddata.getOriginalValue(fieldsToExtract.getJSONObject(i).getString("key")));
            strb.append("\n");
        }
        strb.append("\t -Calculated field values:\n");
        for(int i=0; i<fieldsToCalculate.length(); i++){
            strb.append("\t\t --");
            strb.append(fieldsToCalculate.getJSONObject(i).getString("key"));
            strb.append(": ");
            strb.append(parseddata.getCalculatedValue(fieldsToCalculate.getJSONObject(i).getString("key")));
            strb.append("\n");
        }
        return strb.toString();
    }
    
    @Override
    public MutableNewsExtractedData getDefaultData(ImmutableNewsExtractedData defData) {
        MutableNewsExtractedData ret = new MutableNewsExtractedData(defData);
        for(int i=0; i<fieldsToExtract.length(); i++){
            ret.setDefaultValue(fieldsToExtract.getJSONObject(i).getString("key"), fieldsToExtract.getJSONObject(i).getString("default_value"));
        }
        return ret;
    }
    
    @Override
    public void updateDefaultData(MutableNewsExtractedData mutableExtractedData) {
        JSONObject jsonObject = mutableExtractedData.getExtractedData();
        for(int i=0; i<fieldsToExtract.length(); i++){
            if(jsonObject.has(fieldsToExtract.getJSONObject(i).getString("key"))){
                if(!fieldsToExtract.getJSONObject(i).getBoolean("copy_last_value")){
                    mutableExtractedData.setDefaultValue(fieldsToExtract.getJSONObject(i).getString("key"), fieldsToExtract.getJSONObject(i).getString("default_value"));
                }
            }else{
                mutableExtractedData.setDefaultValue(fieldsToExtract.getJSONObject(i).getString("key"), fieldsToExtract.getJSONObject(i).getString("default_value"));
            }
        }
    }
}
