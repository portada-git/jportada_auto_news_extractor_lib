package org.elsquatrecaps.autonewsextractor.model;

import java.util.Date;
import org.json.JSONObject;

/**
 *
 * @author josep
 */
public class PublicationInfo extends MutableNewsExtractedData{
    
    public PublicationInfo(String jsondata) {
        super(new JSONObject(jsondata));
    }
    
    public PublicationInfo(String modelVersion, String publicationDate) {
        this(modelVersion, publicationDate, null, null,  (String[]) null);
    }
    
    public PublicationInfo(String modelVersion, String publicationDate, String publicationName) {
        this(modelVersion, publicationDate, publicationName, null,  (String[]) null);
    }
    
    public PublicationInfo(String modelVersion, String publicationDate, String publicationName, String publicationEdition) {
        this(modelVersion, publicationDate, publicationName, publicationEdition, (String[]) null);
    }

    public PublicationInfo(String modelVersion, String publicationDate, String publicationName, String publicationEdition, String... pageNumber) {
        this.setModelVersion(modelVersion);
        this.setPublicationDate(publicationDate);
        
        if(publicationName!=null){
            setPublicationName(publicationName);
        }

//        if(publicationPlace!=null){
//            setPublicationPlace(publicationPlace);
//        }
        
        if(publicationEdition!=null){
            setPublicationEdition(publicationEdition);
        }

        if(pageNumber!=null){
            setPublicationPageNumberList(pageNumber);
        }        
    }
    
    public PublicationInfo(String modelVersion, Date publicationDate) {
        this(modelVersion, publicationDate, null, null,  (String[]) null);
    }
    
    public PublicationInfo(String modelVersion, Date publicationDate, String publicationName) {
        this(modelVersion, publicationDate, publicationName, null,  (String[]) null);
    }
    
    public PublicationInfo(String modelVersion, Date publicationDate, String publicationName, String publicationEdition) {
        this(modelVersion, publicationDate, publicationName, publicationEdition, (String[]) null);
    }

    public PublicationInfo(String modelVersion, Date publicationDate, String publicationName, String publicationEdition, String... pageNumber) {
        this.setModelVersion(modelVersion);
        this.setPublicationDate(publicationDate);
        
        if(publicationName!=null){
            setPublicationName(publicationName);
        }

//        if(publicationPlace!=null){
//            setPublicationPlace(publicationPlace);
//        }
        
        if(publicationEdition!=null){
            setPublicationEdition(publicationEdition);
        }

        if(pageNumber!=null){
            setPublicationPageNumberList(pageNumber);
        }
    }

    
}
