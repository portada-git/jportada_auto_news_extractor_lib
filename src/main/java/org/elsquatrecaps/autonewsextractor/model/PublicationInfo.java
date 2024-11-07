package org.elsquatrecaps.autonewsextractor.model;

/**
 *
 * @author josep
 */
public class PublicationInfo extends MutableNewsExtractedData{
    
    public PublicationInfo(String modelVersion, String publicationDate) {
        this(modelVersion, publicationDate, null, null,  null, (String[]) null);
    }
    
    public PublicationInfo(String modelVersion, String publicationDate, String publicationName) {
        this(modelVersion, publicationDate, publicationName, null,  null, (String[]) null);
    }
    
    public PublicationInfo(String modelVersion, String publicationDate, String publicationName, String publicationPlace) {
        this(modelVersion, publicationDate, publicationName, publicationPlace,  null, (String[]) null);
    }
    
    public PublicationInfo(String modelVersion, String publicationDate, String publicationName, String publicationPlace, String publicationEdition) {
        this(modelVersion, publicationDate, publicationName, publicationPlace, publicationEdition, (String[]) null);
    }

    public PublicationInfo(String modelVersion, String publicationDate, String publicationName, String publicationPlace, String publicationEdition, String... pageNumber) {
        this.setModelVersion(modelVersion);
        this.setPublicationDate(publicationDate);
        
        if(publicationName!=null){
            setPublicationName(publicationName);
        }

        if(publicationPlace!=null){
            setPublicationPlace(publicationPlace);
        }
        
        if(publicationEdition!=null){
            setPublicationEdition(publicationEdition);
        }

        if(pageNumber!=null){
            setPublicationPageNumberList(pageNumber);
        }
    }

    
}
