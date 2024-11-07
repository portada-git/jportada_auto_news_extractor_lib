package org.elsquatrecaps.autonewsextractor.dataextractor.parser;

import org.elsquatrecaps.autonewsextractor.model.MutableNewsExtractedData;
import org.elsquatrecaps.autonewsextractor.model.NewsExtractedData;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;
import org.json.JSONObject;

/**
 *
 * @author josep
 */
public class MainAutoNewsExtractorParser extends MainExtractorParser<NewsExtractedData>{
    public static MainAutoNewsExtractorParser getInstance(){
        return new MainAutoNewsExtractorParser();
    }
    
    public static MainAutoNewsExtractorParser getInstance(Configuration conf){
        MainAutoNewsExtractorParser ret = new MainAutoNewsExtractorParser();
        ret.init(conf);
        return ret;
    }
    
    public static MainAutoNewsExtractorParser getInstance(Configuration conf, JSONObject jsonConfig){
        MainAutoNewsExtractorParser ret = new MainAutoNewsExtractorParser();
        ret.init(conf).init(jsonConfig);
        return ret;
    }
    
    protected MutableNewsExtractedData getDefaultExtractedDate(ProxyAutoNewsExtractorParser proxy){
        MutableNewsExtractedData defaultData = new MutableNewsExtractedData(this.defaultData);
        return defaultData;
    }
    

}
