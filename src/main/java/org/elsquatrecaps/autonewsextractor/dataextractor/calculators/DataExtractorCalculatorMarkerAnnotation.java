package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import org.elsquatrecaps.utilities.proxies.PartOfMarkAnnotationId;

/**
 *
 * @author josep
 */
@Target({ METHOD, TYPE })
@Retention(RUNTIME)
public @interface DataExtractorCalculatorMarkerAnnotation {
    @PartOfMarkAnnotationId()
    String id();
}
