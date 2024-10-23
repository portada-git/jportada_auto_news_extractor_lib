/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

import org.elsquatrecaps.autonewsextractor.tools.configuration.RegexConfiguration;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;

/**
 *
 * @author josep
 * @param <P>
 * @param <R>
 */
public abstract class RegexCalculator<P,R> extends AbstractCalculator<P, R>{
    private String basePath;
    private String searchPath;
    private String variant;
    private Configuration conf;
    private Integer parserId;
//    private ExtractedData extractedData;
    
    @Override
    public void init(Object obj){
        if(obj instanceof Configuration){
            Configuration conf = (Configuration) obj;
            init(conf);
        }else if(obj instanceof Integer){
            Integer parserId = (Integer) obj;
            init(parserId);
        }
    }
    
    public void init(Configuration conf){
        this.conf=conf;
        if(parserId!=null){
            __init();
        }
    }
    
    public void init(Integer parserId){
        this.parserId=parserId;
        if(conf!=null){
            __init();
        }
    }
    
    private void __init(){
        RegexConfiguration configuration = (RegexConfiguration) conf;
        this.basePath = configuration.getRegexBasePath();
        this.searchPath = configuration.getFactModel()
                .concat("/").concat(configuration.getNewspaper())
                .concat("/").concat(configuration.getParseModel()[parserId]);
        this.variant = configuration.getOcrEngineModel();
    }
    
    /**
     * @return the basePath
     */
    public String getBasePath() {
        return basePath;
    }

    /**
     * @return the searchPath
     */
    public String getSearchPath() {
        return searchPath;
    }

    /**
     * @return the variant
     */
    public String getVariant() {
        return variant;
    }

//    /**
//     * @return the extractedData
//     */
//    public ExtractedData getExtractedData() {
//        return extractedData;
//    }
}
