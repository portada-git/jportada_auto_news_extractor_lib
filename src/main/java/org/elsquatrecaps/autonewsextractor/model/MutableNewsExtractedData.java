package org.elsquatrecaps.autonewsextractor.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.JSONArray;
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
    
    public MutableNewsExtractedData(ImmutableNewsExtractedData toClone){
        super(toClone);
    }

    public void set(String field, JSONObject value){
        _set(field, value);
    }
    
    public void set(String field, JSONArray value){
        _set(field, value);
    }
    
    public void set(String field, String value){
        _set(field, value);
    }
    
    private void _set(String field, Object value){
        if(isJsonStructuredField(field)){
            _setOriginalValue(field, value);
        }else{
            extractedData.put(field, value);
        }
    }

    public void setOriginalValue(String field, String value){
        _setOriginalValue(field, value);
    }
    
    public void setOriginalValue(String field, JSONObject value){
        _setOriginalValue(field, value);
    }
    
    public void setOriginalValue(String field, JSONArray value){
        _setOriginalValue(field, value);
    }
    
    private void _setOriginalValue(String field, Object value){
        JSONObject obj;
        if(hasValue(value)){
            if(isJsonStructuredField(field)){
                obj = extractedData.getJSONObject(field);
            }else{
                obj = new JSONObject();
            }
            obj.put(ORIGINAL_VALUE_FIELD, value);
            if(obj.has(CALCULATED_VALUE_FIELD)){
                obj.remove(CALCULATED_VALUE_FIELD);
            }
            extractedData.put(field, obj);
        }else if(isJsonStructuredField(field)){
            obj = extractedData.getJSONObject(field);
            obj.remove(ORIGINAL_VALUE_FIELD);
            if(obj.has(CALCULATED_VALUE_FIELD)){
                obj.remove(CALCULATED_VALUE_FIELD);
            }
            if(!obj.has(DEFAULT_VALUE_FIELD)){
                extractedData.remove(field);
            }
        }else if(extractedData.has(field)){
                extractedData.remove(field);
        }
    }

    public void setDefaultValue(String field, String value){
        _setDefaultValue(field, value);
    }
    
    public void setDefaultValue(String field, JSONArray value){
        _setDefaultValue(field, value);
    }
    
    public void setDefaultValue(String field, JSONObject value){
        _setDefaultValue(field, value);
    }
    
    private void _setDefaultValue(String field, Object value){
        JSONObject obj;
        if(hasValue(value)){
            if(isJsonStructuredField(field)){
                obj = extractedData.getJSONObject(field);
            }else{
                if(extractedData.has(field)){
                    _setOriginalValue(field, extractedData.get(field));
                    obj = extractedData.getJSONObject(field);
                }else{
                    obj = new JSONObject();
                }
            }
            obj.put(DEFAULT_VALUE_FIELD, value);
            extractedData.put(field, obj);
        }else if(isJsonStructuredField(field)){
            obj = extractedData.getJSONObject(field);
            obj.remove(DEFAULT_VALUE_FIELD);
        }
    }
    
    public void setCalculateValue(String field, Object value){
        setCalculateValue(field, value, false);
    }
    
    public void setCalculateValue(String field, Object value, boolean removeIfEmpty){
        JSONObject obj;
        if(hasValue(value)){
            if(isJsonStructuredField(field)){
                obj = extractedData.getJSONObject(field);
            }else{
                if(extractedData.has(field)){
                    _setOriginalValue(field, extractedData.get(field));
                    obj = extractedData.getJSONObject(field);
                }else{
                    obj = new JSONObject();
                }
            }
            obj.put(CALCULATED_VALUE_FIELD, value);
            extractedData.put(field, obj);
        }else if(removeIfEmpty && isJsonStructuredField(field)){
            obj = extractedData.getJSONObject(field);
            obj.remove(CALCULATED_VALUE_FIELD);
        }
    }
    
    private boolean hasValue(Object val){
        return  val!=null && !((val instanceof String) && (((String)val).isEmpty() || ((String)val).isBlank()));
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
        this.setOriginalValue(PARSED_TEXT_FIELD_NAME, parsedText);
    }
    

    /**
     * @param unparsedText the unparsedText to set
     */
    public void setUnparsedText(String unparsedText) {
        this.setOriginalValue(UNPARSED_TEXT_FIELD_NAME, unparsedText);
    }
    
    @Override
    public MutableNewsExtractedData clone() {
        return new MutableNewsExtractedData(this);
    }
    
    public void setPublicationDate(Date date){
        this.setOriginalValue(PUBLICATION_DATE_FIELD_NAME, String.valueOf(date.getTime()));
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
//                    throw new AutoNewsRuntimeException(
//                            String.format(
//                                    "There are problems with the publication date ('%s'). It doesn't seem to follow the expected format", date), ex2);
                }
            }
        }
        if(publicationDate==null){
            this.setOriginalValue(PUBLICATION_DATE_FIELD_NAME, date);
        }else{
            setPublicationDate(publicationDate);
        }
    }
    
    public void setRawText(String text){
        this.setOriginalValue(RAW_TEXT_FIELD_NAME, text);
    }

    public void setPublicationName(String v) {
        this.setOriginalValue(PUBLICATION_NAME_FIELD_NAME, v);
    }

//    public void setPublicationPlace(String v) {
//        this.setOriginalValue(PUBLICATION_PLACE_FIELD_NAME, v);
//    }

    public void setPublicationEdition(String v) {
        this.setOriginalValue(PUBLICATION_EDITION_FIELD_NAME, v);
    }

    public void setPublicationPageNumbers(String v) {
        this.setOriginalValue(PUBLICATION_PAGE_NUMBER_FIELD_NAME, v);
    }

    public void setPublicationPageNumberList(String[] v) {
        this.setOriginalValue(PUBLICATION_PAGE_NUMBER_FIELD_NAME, String.join(",", v));
    }

    public void setModelVersion(String v) {
        this.setOriginalValue(MODEL_VERSION_FIELD_NAME, v);
    }

}
