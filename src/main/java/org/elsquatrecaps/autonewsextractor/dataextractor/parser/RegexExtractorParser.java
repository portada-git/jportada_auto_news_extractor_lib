package org.elsquatrecaps.autonewsextractor.dataextractor.parser;

import java.util.ArrayList;
import java.util.Date;
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
    
    protected MutableNewsExtractedData parseDataFromMatcher(Matcher matcher, MutableNewsExtractedData partialExtracted, ImmutableNewsExtractedData lastExtracted){
        boolean found;
        MutableNewsExtractedData parseddata = partialExtracted.clone();

        found = false;
        for(int inc=0; !found && inc<maxGroups; inc+=fieldsToExtract.length()){
            for(int i=0; i<fieldsToExtract.length(); i++){
                if(matcher.group(i+1+inc)!=null){
                    parseddata.set(fieldsToExtract.getJSONObject(i).getString("key"), matcher.group(i+1+inc));
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
                proxy.init(ExtraDataCalculatorEnum.LAST_EXTRACTED_DATA.toString(), lastExtracted);
                proxy.init(ExtraDataCalculatorEnum.CONSTANTS.toString(), constants);
                String value = proxy.calculate(fieldsToCalculate.getJSONObject(i));
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
        Matcher matcher = partialPattern.matcher(bonText);
        if(matcher.find()){
            parseddata = parseDataFromMatcher(matcher, partialExtractedDataToCopy, parseddata);
            textParsed = bonText.substring(matcher.start(), matcher.end());
            firstPos = matcher.end();            
        }
        while(matcher.find()){
            endPos = matcher.start();
            textUnparsed = bonText.substring(firstPos, endPos);
            parseddata.setParsedText(textParsed);
            parseddata.setUnparsedText(textUnparsed);
            ret.add((E) parseddata);
            for(int i=0; i<fieldsToExtract.length(); i++){
                if(!fieldsToExtract.getJSONObject(i).has("copy_last_value") || fieldsToExtract.getJSONObject(i).getBoolean("copy_last_value")){
                    partialExtractedDataToCopy.setCalculateValue(
                        fieldsToExtract.getJSONObject(i).getString("key"), 
                        parseddata.get(fieldsToExtract.getJSONObject(i).getString("key"))
                    );
                }
            }

            parseddata = parseDataFromMatcher(matcher, partialExtractedDataToCopy, parseddata);
            textParsed = bonText.substring(matcher.start(), matcher.end());
            firstPos = matcher.end();            
        }
        textUnparsed = bonText.substring(firstPos);
        parseddata.setParsedText(textParsed);
        parseddata.setUnparsedText(textUnparsed);
        ret.add((E) parseddata);
        return ret;
    }

    @Override
    public MutableNewsExtractedData getDefaultData(Date publicationDate) {
        MutableNewsExtractedData ret = getDefaultData();
        ret.setPublicationDate(publicationDate);
        return ret;
    }
    
    @Override
    public MutableNewsExtractedData getDefaultData() {
        MutableNewsExtractedData ret = new MutableNewsExtractedData();
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
