package org.elsquatrecaps.autonewsextractor.dataextractor.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.elsquatrecaps.autonewsextractor.error.AutoNewsRuntimeException;
import org.elsquatrecaps.autonewsextractor.model.MutableNewsExtractedData;
import org.elsquatrecaps.autonewsextractor.model.NewsExtractedData;

/**
 *
 * @author josep
 */
public class MainAutoNewsExtractorParser extends MainExtractorParser<NewsExtractedData>{
    private Date _publicationDate;
    
    public List<NewsExtractedData> parseFromString(String bonText, String publicationDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
        try {
            _publicationDate = formatter.parse(publicationDate);
        } catch (ParseException ex) {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                _publicationDate = formatter.parse(publicationDate);
            } catch (ParseException ex1) {
                formatter = new SimpleDateFormat("yyyy/MM/dd");
                try {
                    _publicationDate = formatter.parse(publicationDate);
                } catch (ParseException ex2) {
//                    _publicationDate=null;
                    throw new AutoNewsRuntimeException("The publication date is mandatory in this process, please use some existent methot to supply the publication date");
                }
            }
        }
        return parseFromString(bonText, _publicationDate);
    }
    
    public List<NewsExtractedData> parseFromString(String bonText, Date publicationDate) {
        _publicationDate = publicationDate;
        return parseFromString(bonText);
    }
    protected MutableNewsExtractedData getDefaultExtractedDate(ProxyAutoNewsExtractorParser proxy){
        MutableNewsExtractedData defaultData = proxy.getDefaultData();
        defaultData.setPublicationDate(_publicationDate);
        return defaultData;
    }
    

}
