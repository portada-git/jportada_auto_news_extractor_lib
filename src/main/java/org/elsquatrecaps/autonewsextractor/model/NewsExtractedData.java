package org.elsquatrecaps.autonewsextractor.model;

import java.util.Date;

/**
 *
 * @author josep
 */
public interface NewsExtractedData extends ExtractedData{
    String PUBLICATION_DATE_FIELD_NAME = "publication_date";
    String PUBLICATION_NAME_FIELD_NAME = "publication_name";
    //String PUBLICATION_PLACE_FIELD_NAME = "publication_place";
    String PUBLICATION_EDITION_FIELD_NAME = "publication_edition";
    String PUBLICATION_PAGE_NUMBER_FIELD_NAME = "publication_page_number";

    
    Date getPublicationDate(); 
    String getPublicationName();
    //String getPublicationPlace();
    String getPublicationEdition();
    String[] getPublicationPageNumberList();
    String getPublicationPageNumbers();
}
