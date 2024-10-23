package org.elsquatrecaps.autonewsextractor.model;

import java.util.Date;
import org.json.JSONObject;

/**
 *
 * @author josep
 */
public class ImmutableNewsExtractedData implements NewsExtractedData {    
    protected JSONObject extractedData;
    
    public ImmutableNewsExtractedData(){
        extractedData = new JSONObject();
    }
    
    public ImmutableNewsExtractedData(JSONObject extractedData){
        this.extractedData = extractedData;
    }
    
    protected ImmutableNewsExtractedData(ImmutableNewsExtractedData toClone){
        extractedData = new JSONObject(toClone.extractedData.toString());
    }

    @Override
    public String getDefaultValue(String field) {
        String ret = "";
        if (extractedData.has(field) && (extractedData.get(field) instanceof JSONObject) && extractedData.getJSONObject(field).has(DEFAULT_VALUE_FIELD)) {
            ret = extractedData.getJSONObject(field).getString(DEFAULT_VALUE_FIELD);
        }
        return ret;
    }

    @Override
    public String getCalculatedValue(String field) {
        String ret = "";
        if (extractedData.has(field) && (extractedData.get(field) instanceof JSONObject) && extractedData.getJSONObject(field).has(CALCULATED_VALUE_FIELD)) {
            ret = extractedData.getJSONObject(field).getString(CALCULATED_VALUE_FIELD);
        }
        return ret;
    }

    @Override
    public String getOriginalValue(String field) {
        String ret = "";
        if (extractedData.has(field)) {
            if (extractedData.get(field) instanceof String) {
                ret = extractedData.getString(field);
            } else if ((extractedData.get(field) instanceof JSONObject) && extractedData.getJSONObject(field).has(ORIGINAL_VALUE_FIELD)) {
                ret = extractedData.getJSONObject(field).getString(ORIGINAL_VALUE_FIELD);
            }
        }
        return ret;
    }

    @Override
    public String get(String field) {
        String ret;
        ret = getCalculatedValue(field);
        if (ret.isEmpty()) {
            ret = getOriginalValue(field);
            if (ret.isEmpty()) {
                ret = getDefaultValue(field);
            }
        }
        return ret;
    }

    /**
     * @return the parsedText
     */
    @Override
    public String getParsedText() {
        return get("parsedText");
    }

    /**
     * @return the unparsedText
     */
    @Override
    public String getUnparsedText() {
        return get("unparsedText");
    }
    
    /**
     *
     * @return
     */
    @Override
    public ImmutableNewsExtractedData clone(){
        return new ImmutableNewsExtractedData(this);
    }

    @Override
    public Date getPublicationDate() {
        return new Date(Long.parseLong(get("publicationDate")));
    }

    @Override
    public String getAllDataAsJson() {
       return this.extractedData.toString(4);
    }

    @Override
    public String getRawText() {
        return get("rawText");
    }
    
}
