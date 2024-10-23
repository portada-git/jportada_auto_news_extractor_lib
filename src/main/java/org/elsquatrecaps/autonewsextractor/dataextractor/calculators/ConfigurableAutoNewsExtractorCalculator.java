package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

import org.elsquatrecaps.autonewsextractor.model.ExtractedData;
import org.elsquatrecaps.utilities.tools.Callback;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;

/**
 *
 * @author josep
 * @param <P>
 * @param <R>
 */
public interface ConfigurableAutoNewsExtractorCalculator<P,R> extends Callback<P, R>, AutoNewsExtractorCalculator<P, R>{
    
//    void init(Object... params);

    void init(Configuration conf);
    
    void init(Configuration conf, Integer parserId);
    
    void init(ExtractedData extractedData);
}
