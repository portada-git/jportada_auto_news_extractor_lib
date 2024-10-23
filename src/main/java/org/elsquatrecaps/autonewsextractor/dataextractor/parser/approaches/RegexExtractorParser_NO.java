package org.elsquatrecaps.autonewsextractor.dataextractor.parser.approaches;

import java.util.List;
import org.elsquatrecaps.autonewsextractor.tools.configuration.Configurable;
import org.elsquatrecaps.autonewsextractor.tools.configuration.RegexConfiguration;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;

/**
 *
 * @author josep
 * @param <M>
 */
public abstract class RegexExtractorParser_NO<M extends Object> implements ExtractorParser_NO<M>, Configurable{
   private M lastParsedData=null;
   private RegexConfiguration configurator;


    @Override
    public void init(Configuration conf){
        configurator = (RegexConfiguration) conf;
    }        
   
    @Override
    public abstract List<M> parseFromString(String bonText);

    public M getLastParsedData(){
        return lastParsedData;
    }
    
    public void setLastParsedData(M last){
        lastParsedData = last;
    }
}
