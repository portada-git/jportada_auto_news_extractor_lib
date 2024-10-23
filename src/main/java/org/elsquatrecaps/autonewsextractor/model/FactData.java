package org.elsquatrecaps.autonewsextractor.model;

import java.util.Date;

/**
 *
 * @author josep
 */
public interface FactData {

    /**
     * @return the publicationDate
     */
    Date getPublicationDate();

    String getNewsRawText();
    
}
