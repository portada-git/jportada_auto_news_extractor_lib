package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

import org.elsquatrecaps.autonewsextractor.tools.configuration.RegexConfiguration;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;

/**
 *
 * @author josep
 * @param <R>
 */
public abstract class RegexCalculator<R> extends AbstractCalculator<R>{
    private String basePath;
    private String searchPath;
    private String variant;

    
    @Override
    public void init(Object obj){
        super.init(obj);
        if(this.getParserId()!=null && getConfiguration()!=null){
            __init();
        }
    }
    
    private void __init(){
        RegexConfiguration configuration = (RegexConfiguration) getConfiguration();
        this.basePath = configuration.getRegexBasePath();
        this.searchPath = configuration.getFactModel()
                .concat("/").concat(configuration.getNewspaper())
                .concat("/").concat(configuration.getParseModel()[getParserId()]);
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
    
    protected Integer getParserId(){
        return super.getInitData(PARSER_ID);
    }

    protected Configuration getConfiguration(){
        return super.getInitData(CONFIGURATION);
    }
}
