package org.elsquatrecaps.autonewsextractor.model;

import java.util.Date;
import java.util.Set;
import org.json.JSONArray;
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

    public ImmutableNewsExtractedData(ExtractedData toClone){
        if(toClone instanceof ImmutableNewsExtractedData){
            extractedData = new JSONObject(((ImmutableNewsExtractedData)toClone).extractedData.toString());
        }else{
            extractedData = new JSONObject(new JSONObject(toClone.getAllDataAsJson()));
        }
    }

    @Override
    public String getDefaultValue(String field) {
        String ret = "";
        if (extractedData.has(field) && (extractedData.get(field) instanceof JSONObject) && extractedData.getJSONObject(field).has(DEFAULT_VALUE_FIELD)) {
            ret = extractedData.getJSONObject(field).getString(DEFAULT_VALUE_FIELD);
        }
        return ret;
    }

    private String getDefaultValue(JSONObject jsonStructuredFieldValue) {
        String ret = "";
        if (jsonStructuredFieldValue.has(DEFAULT_VALUE_FIELD)) {
            ret = jsonStructuredFieldValue.getString(DEFAULT_VALUE_FIELD);
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

    private String getCalculatedValue(JSONObject jsonStructuredFieldValue) {
        String ret = "";
        if (jsonStructuredFieldValue.has(CALCULATED_VALUE_FIELD)) {
            ret = jsonStructuredFieldValue.getString(CALCULATED_VALUE_FIELD);
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

    private String getOriginalValue(JSONObject jsonStructuredFieldValue) {
        String ret = "";
        if (jsonStructuredFieldValue.has(ORIGINAL_VALUE_FIELD)) {
            ret = jsonStructuredFieldValue.getString(ORIGINAL_VALUE_FIELD);
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

    private String getValue(Object value) {
        String ret = value.toString();
        if(isJsonStructuredValue(value)){
            ret = getCalculatedValue((JSONObject)value);
            if (ret.isEmpty()) {
                ret = getOriginalValue((JSONObject)value);
                if (ret.isEmpty()) {
                    ret = getDefaultValue((JSONObject)value);
                }
            }
        }
        return ret;
    }

    /**
     * @return the parsedText
     */
    @Override
    public String getParsedText() {
        return get(PARSED_TEXT_FIELD_NAME);
    }

    /**
     * @return the unparsedText
     */
    @Override
    public String getUnparsedText() {
        return get(UNPARSED_TEXT_FIELD_NAME);
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
        return new Date(Long.parseLong(get(PUBLICATION_DATE_FIELD_NAME)));
    }

    @Override
    public String getAllDataAsJson() {
       return this.extractedData.toString(4);
    }

    @Override
    public String getAllDataAsJson(boolean onlyOneValueForField) {
        String ret;
        if(onlyOneValueForField){
           ret = getOnlyOneValueForFieldAsJson(extractedData, "");
        }else{
            ret = getAllDataAsJson();
        }
        return ret;
    }
    
    private String getOnlyOneValueForFieldAsJson(JSONObject jobject, String pre){
        String post = "";
        StringBuilder strb = new StringBuilder();
        Set<String> keySet = jobject.keySet();
        strb.append("{\n");
        String insidePre = pre.concat("  ");
        for(String key: keySet){
            strb.append(post);
            post = ",\n";
            strb.append(insidePre);
            strb.append("\"");
            strb.append(key);
            strb.append("\": ");
            if(isJsonStructuredValue(jobject.get(key))){
                strb.append("\"");
                strb.append(getValue(jobject.getJSONObject(key)).replaceAll("\n", "\\\\n"));
                strb.append("\"");
            }else if(jobject.get(key) instanceof JSONObject){
                strb.append(getOnlyOneValueForFieldAsJson(jobject.getJSONObject(key), insidePre));
            }else if(jobject.get(key) instanceof JSONArray){
                strb.append(getOnlyOneValueForField(jobject.getJSONArray(key), insidePre));
            }else{
                strb.append("\"");
                strb.append(getValue(jobject.get(key)).replaceAll("\n", "\\\\n"));
                strb.append("\"");
            }
        }
        strb.append("\n");
        strb.append(pre);
        strb.append("}\n");
        return strb.toString();
    }

    private String getOnlyOneValueForField(JSONArray jarray, String pre){
        String post = "";
        StringBuilder strb = new StringBuilder();
        strb.append("[\n");
        String insidePre = pre.concat("  ");
        for(int i=0; i< jarray.length(); i++){
            strb.append(post);
            post = ",\n";
            strb.append(insidePre);
            if(isJsonStructuredValue(jarray.get(i))){
                strb.append(getValue(jarray.getJSONObject(i)));
            }else if(jarray.get(i) instanceof JSONObject){
                strb.append(getOnlyOneValueForFieldAsJson(jarray.getJSONObject(i), insidePre));
            }else if(jarray.get(i) instanceof JSONArray){
                strb.append(getOnlyOneValueForField(jarray.getJSONArray(i), insidePre));
            }else{
                strb.append(getValue(jarray.get(i)));
            }
        }
        strb.append("\n");
        strb.append(pre);
        strb.append("]\n");
        return strb.toString();
    }

    @Override
    public String getRawText() {
        return get(RAW_TEXT_FIELD_NAME);
    }

    @Override
    public String getPublicationName() {
        return get(PUBLICATION_NAME_FIELD_NAME);
    }

//    @Override
//    public String getPublicationPlace() {
//        return get(PUBLICATION_PLACE_FIELD_NAME);
//    }

    @Override
    public String getPublicationEdition() {
        return get(PUBLICATION_EDITION_FIELD_NAME);
    }

    @Override
    public String getPublicationPageNumbers() {
        return get(PUBLICATION_PAGE_NUMBER_FIELD_NAME);
    }

    @Override
    public String[] getPublicationPageNumberList() {
        return get(PUBLICATION_PAGE_NUMBER_FIELD_NAME).split(",");
    }

    @Override
    public String getModelVersion() {
        return get(MODEL_VERSION_FIELD_NAME);
    }
    
    protected boolean isJsonStructuredValue(Object value){
        boolean ret = false;
        if(value instanceof JSONObject){
            JSONObject jo = (JSONObject) value;
            ret = (jo.has(DEFAULT_VALUE_FIELD) || jo.has(ORIGINAL_VALUE_FIELD) || jo.has(CALCULATED_VALUE_FIELD));
        }
        return ret;
    }
    
    protected boolean isJsonStructuredValue(String field){
        return extractedData.has(field) 
                && (extractedData.get(field) instanceof JSONObject) 
                && (extractedData.getJSONObject(field).has(DEFAULT_VALUE_FIELD)
                    || extractedData.getJSONObject(field).has(ORIGINAL_VALUE_FIELD)
                    || extractedData.getJSONObject(field).has(CALCULATED_VALUE_FIELD));
    }

}
