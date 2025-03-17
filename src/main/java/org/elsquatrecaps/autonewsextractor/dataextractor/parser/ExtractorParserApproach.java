package org.elsquatrecaps.autonewsextractor.dataextractor.parser;

import java.util.List;
import org.elsquatrecaps.autonewsextractor.model.ExtractedData;
import org.elsquatrecaps.autonewsextractor.model.ImmutableNewsExtractedData;
import org.elsquatrecaps.autonewsextractor.model.MutableNewsExtractedData;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;
import org.json.JSONObject;

/**
 *
 * @author josep
 * @param <E>
 */
public interface ExtractorParserApproach<E extends ExtractedData>/*extends Configurable*/{

    void init(Configuration configuration, int parserId);

    void init(JSONObject jsonConfig);
    
    void init(String challenge, String signedData);

    void setLastParsed(E lastParsed);

    List<E> parseFromString(String bonText, MutableNewsExtractedData partialExtractedDataToCopy);

//    MutableNewsExtractedData getDefaultData();

    //MutableNewsExtractedData getDefaultData(Date publicationDate);
    MutableNewsExtractedData getDefaultData(ImmutableNewsExtractedData baseData);

    void updateDefaultData(MutableNewsExtractedData extractedData);
    
    boolean needSecurityConfig();    
}
