package org.elsquatrecaps.autonewsextractor.model;

import java.util.Date;

/**
 *
 * @author josepcanellas
 */
public class BaseFactData implements FactData {
    
    protected String newsText;
    protected Date publicationDate;

    public BaseFactData() {
    }

    public BaseFactData(String newsText, Date publicationDate) {
        while(newsText.startsWith("\n")){
            newsText = newsText.substring(1);
        }
        while(newsText.endsWith("\n")){
            newsText = newsText.substring(0, newsText.length()-1);
        }
        this.newsText = newsText.trim();
        this.publicationDate = publicationDate;        
    }
    
    

    @Override
    public String getNewsRawText() {
        return this.newsText;
    }

    /**
     * @return the publicationDate
     */
    @Override
    public Date getPublicationDate() {
        return publicationDate;
    }

    /**
     * @param publicationDate the publicationDate to set
     */
    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
    
}
