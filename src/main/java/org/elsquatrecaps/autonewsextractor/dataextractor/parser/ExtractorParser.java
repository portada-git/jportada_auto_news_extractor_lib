package org.elsquatrecaps.autonewsextractor.dataextractor.parser;

import java.util.List;
import org.elsquatrecaps.autonewsextractor.model.ExtractedData;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author josep
 * @param <E>
 */
public interface ExtractorParser<E extends ExtractedData>{

//    void init(Configuration configuration, int parserId);
    
    void init(Configuration configuration);

    void init(JSONObject jsonConfig);
    
    JSONArray getFieldsProperties();

//    List<ExtractedData> parseFromString(String bonText, JSONObject partialExtractedDataToCopy);

    List<E> parseFromString(String bonText);

    List<E> parseFromString(String bonText, int parseid);
}
