package org.elsquatrecaps.autonewsextractor.dataextractor.parser;

import java.util.Date;
import java.util.List;
import org.elsquatrecaps.autonewsextractor.model.ExtractedData;
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

    List<E> parseFromString(String bonText, MutableNewsExtractedData partialExtractedDataToCopy);

    MutableNewsExtractedData getDefaultData();

    MutableNewsExtractedData getDefaultData(Date publicationDate);

    void updateDefaultData(MutableNewsExtractedData extractedData);
    
}
