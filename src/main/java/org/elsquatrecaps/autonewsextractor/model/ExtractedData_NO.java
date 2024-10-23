package org.elsquatrecaps.autonewsextractor.model;

import org.json.JSONObject;

/**
 *
 * @author josep
 * @param <M>
 */
public class ExtractedData_NO{
    private JSONObject extractedData;

    public ExtractedData_NO() {
    }
    
    
    /**
     * @return the extractedData
     */
    public JSONObject getExtractedData() {
        return extractedData;
    }

    /**
     * @param extractedData the extractedData to set
     */
    public void setExtractedData(JSONObject extractedData) {
        this.extractedData = extractedData;
    }

}
