package org.elsquatrecaps.autonewsextractor.informationunitbuilder.reader;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;
import org.elsquatrecaps.utilities.proxies.PartOfMarkAnnotationId;

/**
 *
 * @author josepcanellas
 */
@Target({ METHOD, TYPE })
@Retention(RUNTIME)
public @interface InformationUnitBuilderMarkerAnnotation {
    @PartOfMarkAnnotationId(orderNum = 0)
    String joinerType();
    @PartOfMarkAnnotationId(orderNum = 1)
    String metadataSource();
}
