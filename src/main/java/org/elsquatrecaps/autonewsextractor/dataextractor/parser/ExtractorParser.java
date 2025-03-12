package org.elsquatrecaps.autonewsextractor.dataextractor.parser;

import java.util.List;
import org.elsquatrecaps.autonewsextractor.model.ExtractedData;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;
import org.json.JSONObject;

/**
 *
 * @author josep
 * @param <E>
 */
public interface ExtractorParser<E extends ExtractedData>{

//    void init(Configuration configuration, int parserId);
    
    ExtractorParser init(Configuration configuration);

    ExtractorParser init(JSONObject jsonConfig);
        
    JSONObject getCsvProperties();

    JSONObject getCsvProperties(int parserId);

//    List<ExtractedData> parseFromString(String bonText, ExtractedData defaultData);

    List<E> parseFromString(String bonText, int parseid, ExtractedData defaultData);

//    List<E> parseFromString(String bonText);
//
//    List<E> parseFromString(String bonText, int parseid);
}
