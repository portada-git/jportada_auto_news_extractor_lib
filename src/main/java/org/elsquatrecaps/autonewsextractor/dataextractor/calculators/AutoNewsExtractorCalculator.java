package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

import org.elsquatrecaps.utilities.tools.Callback;

/**
 *
 * @author josep
 */
public interface AutoNewsExtractorCalculator<P, R> extends Callback<P, R> {

    R calculate(P param);
    
    public void init(Object conf);
    
}
