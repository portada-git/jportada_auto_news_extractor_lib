package org.elsquatrecaps.autonewsextractor.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONObject;

/**
 *
 * @author josep
 */
public class MutableNewsExtractedData extends ImmutableNewsExtractedData implements NewsExtractedData {
    
    
    public MutableNewsExtractedData(){
        super();
    }
    
    public MutableNewsExtractedData(JSONObject extractedData){
        super(extractedData);
    }
    
    protected MutableNewsExtractedData(MutableNewsExtractedData toClone){
        super(toClone);
    }

    public void set(String field, String value){
        if(extractedData.has(field) && (extractedData.get(field) instanceof JSONObject)){
            setOriginalValue(field, value);
        }else{
            extractedData.put(field, value);
        }
    }

    public void setOriginalValue(String field, String value){
        JSONObject obj;
        if(value!=null && !value.isEmpty()){
            if(extractedData.has(field) && (extractedData.get(field) instanceof JSONObject)){
                obj = extractedData.getJSONObject(field);
            }else{
                obj = new JSONObject();
            }
            obj.put(ORIGINAL_VALUE_FIELD, value);
            if(obj.has(CALCULATED_VALUE_FIELD)){
                obj.remove(CALCULATED_VALUE_FIELD);
            }
            extractedData.put(field, obj);
        }else if(extractedData.has(field)){
            if(extractedData.get(field) instanceof JSONObject){
                obj = extractedData.getJSONObject(field);
                obj.remove(ORIGINAL_VALUE_FIELD);
                if(obj.has(CALCULATED_VALUE_FIELD)){
                    obj.remove(CALCULATED_VALUE_FIELD);
                }
            }else{
                extractedData.remove(field);
            }
        }
    }

    public void setDefaultValue(String field, String value){
        JSONObject obj;
        if(value!=null && !value.isEmpty()){
            if(extractedData.has(field) && (extractedData.get(field) instanceof JSONObject)){
                obj = extractedData.getJSONObject(field);
            }else{
                if(extractedData.has(field) && (extractedData.get(field) instanceof String)){
                    setOriginalValue(field, extractedData.getString(field));
                    obj = extractedData.getJSONObject(field);
                }else{
                    obj = new JSONObject();
                }
            }
            obj.put(DEFAULT_VALUE_FIELD, value);
            extractedData.put(field, obj);
        }else if(extractedData.has(field) && (extractedData.get(field) instanceof JSONObject)){
            obj = extractedData.getJSONObject(field);
            obj.remove(DEFAULT_VALUE_FIELD);
        }
    }
    
    public void setCalculateValue(String field, String value){
        JSONObject obj;
        if(value!=null && !value.isEmpty()){
            if(extractedData.has(field) && (extractedData.get(field) instanceof JSONObject)){
                obj = extractedData.getJSONObject(field);
            }else{
                if(extractedData.has(field) && (extractedData.get(field) instanceof String)){
                    setOriginalValue(field, extractedData.getString(field));
                    obj = extractedData.getJSONObject(field);
                }else{
                    obj = new JSONObject();
                }
            }
            obj.put(CALCULATED_VALUE_FIELD, value);
            extractedData.put(field, obj);
        }else if(extractedData.has(field) && (extractedData.get(field) instanceof JSONObject)){
            obj = extractedData.getJSONObject(field);
            obj.remove(CALCULATED_VALUE_FIELD);
        }
    }


    /**
     * @return 
     */
    public JSONObject getExtractedData() {
        return this.extractedData;
    }

    /**
     * @param extractedData the extractedData to set
     */
    public void setExtractedData(JSONObject extractedData) {
        this.extractedData = extractedData;
    }


    /**
     * @param parsedText the parsedText to set
     */
    public void setParsedText(String parsedText) {
        this.setOriginalValue("parsedText", parsedText);
    }
    

    /**
     * @param unparsedText the unparsedText to set
     */
    public void setUnparsedText(String unparsedText) {
        this.setOriginalValue("unparsedText", unparsedText);
    }
    
    public MutableNewsExtractedData clone() {
        return new MutableNewsExtractedData(this);
    }
    
    public void setPublicationDate(Date date){
        this.setOriginalValue("publicationDate", String.valueOf(date.getTime()));
    }

    public void setPublicationDate(String date){
        Date publicationDate;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
        try {
            publicationDate = formatter.parse(date);
        } catch (ParseException ex) {
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            try {
                publicationDate = formatter.parse(date);
            } catch (ParseException ex1) {
                formatter = new SimpleDateFormat("yyyy/MM/dd");
                try {
                    publicationDate = formatter.parse(date);
                } catch (ParseException ex2) {
                    publicationDate=null;
                }
            }
        }
        if(publicationDate==null){
            this.setOriginalValue("publicationDate", date);
        }else{
            setPublicationDate(publicationDate);
        }
    }
    
    public void setRawText(String text){
        this.setOriginalValue("rawText", text);
    }
}
