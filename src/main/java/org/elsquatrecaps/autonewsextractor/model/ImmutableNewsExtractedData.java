package org.elsquatrecaps.autonewsextractor.model;

import java.util.Collection;
import java.util.Date;
import java.util.Map;
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

    private Object getDefaultObjectValue(JSONObject jsonStructuredFieldValue) {
        Object ret = "";
        if (jsonStructuredFieldValue.has(DEFAULT_VALUE_FIELD)) {
            ret = jsonStructuredFieldValue.get(DEFAULT_VALUE_FIELD);
        }
        return ret;
    }

    @Override
    public String getCalculatedValue(String field) {
        String ret = "";
        if (extractedData.has(field) && (extractedData.get(field) instanceof JSONObject) && extractedData.getJSONObject(field).has(CALCULATED_VALUE_FIELD)) {
            ret = extractedData.getJSONObject(field).get(CALCULATED_VALUE_FIELD).toString();
        }
        return ret;
    }

    private Object getCalculatedObjectValue(JSONObject jsonStructuredFieldValue) {
        Object ret = "";
        if (jsonStructuredFieldValue.has(CALCULATED_VALUE_FIELD)) {
            ret = jsonStructuredFieldValue.get(CALCULATED_VALUE_FIELD);
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
                ret = extractedData.getJSONObject(field).get(ORIGINAL_VALUE_FIELD).toString();
            }else{
                ret = extractedData.get(field).toString();
            }
        }
        return ret;
    }
    
    private Object getOriginalObjectValue(JSONObject jsonStructuredFieldValue) {
        Object ret = "";
        if (jsonStructuredFieldValue.has(ORIGINAL_VALUE_FIELD)) {
            ret = jsonStructuredFieldValue.get(ORIGINAL_VALUE_FIELD);
        }
        return ret;
    }

    @Override
    public String get(String field) {
        String ret= "";
        if(isJsonStructuredField(field)){
            ret = getCalculatedValue(field);
            if (isEmpty(ret)) {
                ret = getOriginalValue(field);
                if (isEmpty(ret)) {
                    ret = getDefaultValue(field);
                }
            }
        }else if(extractedData.has(field)){
            ret = extractedData.get(field).toString();
        }
        return ret;
    }
    
    private boolean isEmpty(Object value){
        boolean ret;
        ret = value==null;
        if (!ret && value instanceof String){
            ret = ((String) value).isEmpty();
        }else if(!ret && value instanceof JSONArray){
            ret = ((JSONArray) value).isEmpty();
        }else if(!ret && value instanceof JSONObject){
            ret = ((JSONObject) value).isEmpty();
        }else if(!ret && value instanceof Collection){
            ret = ((Collection) value).isEmpty();
        }else if(!ret && value instanceof Map){
            ret = ((Map) value).isEmpty();
        }
        return ret; 
    }
    
    public Object getAsObject(String field) {
        return getObjectValue(extractedData.get(field));
    }
    
    public Object getObjectValue(Object value) {
        Object ret = value;
        if(isJsonStructuredValue(value)){
            ret = getCalculatedObjectValue((JSONObject)value);
            if (isEmpty(ret)) {
                ret = getOriginalObjectValue((JSONObject)value);
                if (isEmpty(ret)) {
                    ret = getDefaultObjectValue((JSONObject)value);
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
        return (String) get(PARSED_TEXT_FIELD_NAME);
    }

    /**
     * @return the unparsedText
     */
    @Override
    public String getUnparsedText() {
        return (String) get(UNPARSED_TEXT_FIELD_NAME);
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
    
    private String getValueAsJsonString(Object value, String pre){
        String ret = "";
        Object objectValue = getObjectValue(value);
        if(objectValue instanceof JSONArray){
            ret = getOnlyOneValueForField((JSONArray) objectValue, pre);
        }else if(objectValue instanceof JSONObject){
            ret = getOnlyOneValueForFieldAsJson((JSONObject) objectValue, pre);
        }else if (objectValue instanceof Boolean
                || objectValue instanceof Number){
            ret =  objectValue.toString();
        }else{
            String v = (objectValue==null?"":objectValue.toString()).replaceAll("\n", "\\\\n").replaceAll("\\\"", "'");
            ret = ret.concat("\"").concat(v).concat("\"");
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
            strb.append(getValueAsJsonString(getObjectValue(jobject.get(key)), insidePre));            
//            if(isJsonStructuredValue(jobject.get(key))){                
//                strb.append("\"");
//                strb.append(getObjectValue(jobject.getJSONObject(key)).replaceAll("\n", "\\\\n").replaceAll("\\\"", "'"));
//                strb.append("\"");
//            }else if(jobject.get(key) instanceof JSONObject){
//                strb.append(getOnlyOneValueForFieldAsJson(jobject.getJSONObject(key), insidePre));
//            }else if(jobject.get(key) instanceof JSONArray){
//                strb.append(getOnlyOneValueForField(jobject.getJSONArray(key), insidePre));
//            }else{
//                strb.append("\"");
//                strb.append(getValue(jobject.get(key)).replaceAll("\n", "\\\\n").replaceAll("\\\"", "'"));
//                strb.append("\"");
//            }
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
            strb.append(getValueAsJsonString(jarray.get(i),insidePre));
//            if(isJsonStructuredValue(jarray.get(i))){
//                strb.append(getValueAsJsonString(jarray.getJSONObject(i),insidePre));
//            }else if(jarray.get(i) instanceof JSONObject){
//                strb.append(getOnlyOneValueForFieldAsJson(jarray.getJSONObject(i), insidePre));
//            }else if(jarray.get(i) instanceof JSONArray){
//                strb.append(getOnlyOneValueForField(jarray.getJSONArray(i), insidePre));
//            }else{
//                strb.append(getValueAsJsonString(jarray.get(i),insidePre));
//            }
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
    
    protected boolean isJsonStructuredField(String field){
        return extractedData.has(field) 
                && (extractedData.get(field) instanceof JSONObject) 
                && (extractedData.getJSONObject(field).has(DEFAULT_VALUE_FIELD)
                    || extractedData.getJSONObject(field).has(ORIGINAL_VALUE_FIELD)
                    || extractedData.getJSONObject(field).has(CALCULATED_VALUE_FIELD));
    }

}
