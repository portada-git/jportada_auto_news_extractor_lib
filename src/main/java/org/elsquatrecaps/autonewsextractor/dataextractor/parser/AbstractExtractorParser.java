/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.elsquatrecaps.autonewsextractor.dataextractor.parser;

import org.elsquatrecaps.autonewsextractor.model.ExtractedData;
import org.elsquatrecaps.autonewsextractor.model.MutableNewsExtractedData;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;
import org.elsquatrecaps.utilities.tools.configuration.DevelopmentConfiguration;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author josepcanellas
 * @param <E>
 */
public abstract class AbstractExtractorParser<E extends ExtractedData> implements ExtractorParserApproach<E>{
    
    Configuration configuration;
    int parserId;
    JSONObject constants;

    @Override
    public void init(Configuration configuration, int parserId) {
        this.configuration = configuration;
        this.parserId = parserId;
    }
    
    @Override
    public void init(JSONObject jsonConfig) {
        if(jsonConfig.has("ai_instructions")){
            initJsonCOnfig(jsonConfig);
        }else{
            initJsonConstants(jsonConfig);
        }        
    }

    @Override
    public void init(String challenge, String signedData){        
    }

    
    protected void initJsonConstants(JSONObject constants) {
        this.constants = constants;
    }

    protected abstract void initJsonCOnfig(JSONObject jsonConfig);
    
    @Override
    public boolean needSecurityConfig(){
        return false;
    }
    
    protected void printForDebbuging(String textParsed, String textUnparsed, MutableNewsExtractedData parseddata, JSONArray fieldsToExtract, JSONArray fieldsToCalculate){
        if(configuration instanceof DevelopmentConfiguration && ((DevelopmentConfiguration)configuration).getRunForDebugging()){
            System.out.println(String.format("Parsed text: \"%s\"\n\n--------------\n", textParsed));
            System.out.println(String.format("Unparsed text: \"%s\"\n\n--------------\n", textUnparsed));
            System.out.println(parseddataToStringForDebugging(parseddata, fieldsToExtract, fieldsToCalculate));
            System.out.println("\n\n--------------\n");
        }        
    }
    
    protected String parseddataToStringForDebugging(MutableNewsExtractedData parseddata, JSONArray fieldsToExtract, JSONArray fieldsToCalculate){
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
}
