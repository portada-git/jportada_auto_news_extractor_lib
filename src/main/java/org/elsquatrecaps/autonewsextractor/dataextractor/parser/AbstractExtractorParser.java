/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.elsquatrecaps.autonewsextractor.dataextractor.parser;

import org.elsquatrecaps.autonewsextractor.model.ExtractedData;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;
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
    
    public boolean needSecurityConfig(){
        return false;
    }
    
}
