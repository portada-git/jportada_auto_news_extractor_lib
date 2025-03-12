package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

import org.elsquatrecaps.utilities.tools.Callback;

/**
 *
 * @author josep
 */
public interface AutoNewsExtractorCalculator<R> extends Callback<Object[], R> {

    R calculate(Object[] param);
    
    public void init(Object conf);
    
}
