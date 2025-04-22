package org.elsquatrecaps.autonewsextractor.dataextractor.parser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elsquatrecaps.autonewsextractor.dataextractor.calculators.DataExtractorCalculatorProxyClass;
import org.elsquatrecaps.autonewsextractor.dataextractor.calculators.ExtraDataCalculatorEnum;
import org.elsquatrecaps.autonewsextractor.model.ExtractedData;
import org.elsquatrecaps.autonewsextractor.model.ImmutableNewsExtractedData;
import org.elsquatrecaps.autonewsextractor.model.MutableNewsExtractedData;
import org.elsquatrecaps.portada.jportadamscaller.PortadaMicroservicesCaller;
import org.elsquatrecaps.portada.jportadamscaller.exceptions.PortadaMicroserviceCallException;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author josep
 * @param <E>
 */
@ParserMarkerAnnotation(approach = "openai")
public class OpenaiExtractorParser<E extends ExtractedData> extends AbstractExtractorParser<E>{
    String costCenter;
    JSONObject configJson=new JSONObject();
    JSONArray fieldsToAssign;
    JSONArray fieldsToCalculate;
    boolean parseByParagraphs;
    boolean saveParsedData;
    E lastParsed=null;
    String challenge;
    String signedData;
    PortadaMicroservicesCaller caller = new PortadaMicroservicesCaller();

    
    @Override
    public void init(Configuration configuration, int parserId) {
        this.configuration = configuration;
        this.parserId = parserId;
        costCenter = configuration.getAttr("cost_center");
    }

    @Override
    protected void initJsonCOnfig(JSONObject jsonConfig) {
        this.fieldsToAssign = jsonConfig.getJSONArray("fields_to_assign");
        this.fieldsToCalculate = jsonConfig.getJSONArray("fields_to_calculate");
        this.configJson.put("ai_instructions", jsonConfig.getJSONObject("ai_instructions"));
        this.configJson.put("model", jsonConfig.getString("model"));
        this.configJson.put("model_config", jsonConfig.getJSONObject("model_config"));
        this.parseByParagraphs = jsonConfig.optBoolean("parse_by_paragraphs", false);
        this.saveParsedData = jsonConfig.optBoolean("save_parsed_data", false);
        Properties ms_properties = new Properties();
        try (final FileReader fr = new FileReader(jsonConfig.getString("microservice_initializer_file"))) {
            ms_properties.load(fr);
        } catch (IOException ex) {
            throw new RuntimeException("Can't load the config file to call to microservice.");
        }
        caller.init(ms_properties);
    }
    
    @Override
    public void init(String challenge, String signedData){ 
        this.challenge = challenge;
        this.signedData = signedData;
    }       

    @Override
    public void setLastParsed(E parsedList){
        this.lastParsed = parsedList;
    }   
    
    @Override
    public boolean needSecurityConfig(){
        return true;
    }
    
    protected MutableNewsExtractedData parseDataFromAiJson(JSONObject aiJson, MutableNewsExtractedData partialExtracted, ImmutableNewsExtractedData lastExtracted){
        MutableNewsExtractedData parseddata = partialExtracted.clone();
        for(int i=0; i<fieldsToAssign.length(); i++){
            Object value = aiJson.get(fieldsToAssign.getJSONObject(i).getString("source"));
            if(value instanceof JSONObject){
                parseddata.set(fieldsToAssign.getJSONObject(i).getString("target"), (JSONObject) value);
            }else if(value instanceof JSONArray){
                parseddata.set(fieldsToAssign.getJSONObject(i).getString("target"), (JSONArray) value);
            }else{
                parseddata.set(fieldsToAssign.getJSONObject(i).getString("target"), value.toString());
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
    public List<E> parseFromString(String bonText, MutableNewsExtractedData partialExtractedDataToCopy) {
        List<E> ret = new ArrayList<>();
        JSONObject params = new JSONObject();
        ImmutableNewsExtractedData lastData=null;
        String[]  aTextToParse;
        if(parseByParagraphs){
            aTextToParse = bonText.split("\n");
        }else{
            aTextToParse = new String[] {bonText};
        }
        lastData = (ImmutableNewsExtractedData) this.lastParsed;
        for(String text: aTextToParse){
            params.put("text", text);
            params.put("team", costCenter);
            params.put("config_json", configJson);
            MutableNewsExtractedData parseddata = null;
            try {
                boolean rep = challenge==null || signedData==null;
                JSONObject resp = new JSONObject(caller.sendPostAsFormatParams("pr/extract_with_openai", "python", params, challenge, signedData, String.class, rep));
                if(resp.getInt("status")==0){
                    parseddata = parseDataFromAiJson(resp.getJSONObject("content"), partialExtractedDataToCopy, lastData);
                }else{
                    JSONObject error = new JSONObject(String.format("{'error':'%s'}", resp.getString("error_message")));
                    parseddata = parseDataFromAiJson(error, partialExtractedDataToCopy, lastData);
                }
                if(this.saveParsedData){
                    parseddata.setParsedText(text);
                }
            } catch (PortadaMicroserviceCallException ex) {
                Logger.getLogger(OpenaiExtractorParser.class.getName()).log(Level.SEVERE, null, ex);
                //Generate error
            }
            lastData = parseddata;
            ret.add((E) parseddata);
        }
        return ret;
    }

    @Override
    public MutableNewsExtractedData getDefaultData(ImmutableNewsExtractedData defData) {
        MutableNewsExtractedData ret = new MutableNewsExtractedData(defData);
        for(Object key: configJson.getJSONObject("ai_instructions").getJSONObject("json_template").keySet()){
            Object value = configJson.getJSONObject("ai_instructions").getJSONObject("json_template").opt(key.toString());
            if(value instanceof JSONArray){
                ret.setDefaultValue(key.toString(), (JSONArray)value);
            }else if(value instanceof JSONObject){
                ret.setDefaultValue(key.toString(), (JSONObject)value);
            }else{
                ret.setDefaultValue(key.toString(), value==null?"?":value.toString());
            }
        }
        return ret;
    }
    
    @Override
    public void updateDefaultData(MutableNewsExtractedData mutableExtractedData) {
    }    
}
