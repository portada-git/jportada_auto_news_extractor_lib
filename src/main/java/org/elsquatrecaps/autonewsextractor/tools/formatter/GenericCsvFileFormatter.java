/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.elsquatrecaps.autonewsextractor.tools.formatter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.elsquatrecaps.autonewsextractor.error.AutoNewsRuntimeException;
import org.elsquatrecaps.autonewsextractor.model.MutableNewsExtractedData;
import org.elsquatrecaps.utilities.tools.Callback;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author josep
 * @param <T>
 */
public class GenericCsvFileFormatter<T extends MutableNewsExtractedData> implements FileFormatter{
    private boolean appendFile = false;
    private List<T> list = new ArrayList<>();
    private JSONArray headerFields;
    private MultiValuesType multipleValuesAS = MultiValuesType.AS_LIST;
    private String fieldSeparator = ",";
    private String singleListSeparator=",";
    
    public GenericCsvFileFormatter format(T... fact){
        getList().addAll((Collection<? extends T>) Arrays.asList(fact));
        return this;
    }
    
    public GenericCsvFileFormatter format(List<T> fact){
        getList().addAll((Collection<? extends T>) fact);
        return this;
    }
    
    public GenericCsvFileFormatter configMultipleValuesType(MultiValuesType mvt){
        this.setMultipleValuesAS(mvt);
        return this;
    }
    
    public GenericCsvFileFormatter configHeaderFields(JSONObject args){
        this.setHeaderFields(args.getJSONArray("fields"));
        this.fieldSeparator = args.optString("field_separator", fieldSeparator);
        return this;
    }
    
    private FactDataAsString factToFactDataAsString(T fact){
        return this.factToFactDataAsString(fact, headerFields);
    }
    
    private String getStructuredSingeObjectValue(Object value, MutableNewsExtractedData fact){
        String ret;
        value = fact.getObjectValue(value);
        if(value instanceof JSONArray || value instanceof  Collection){
            ret = getStructuredSingleListValue(value, fact);
        }else if(value instanceof JSONObject || value instanceof Map){
            ret = getStructuredSingleMapValue(value, fact);
            singleListSeparator="; ";
        }else{
            ret = (value==null?"":value).toString();
        }
        return ret;
    }
            
    private String getStructuredValue(Object value, MutableNewsExtractedData fact){
        String ret;
        value = fact.getObjectValue(value);
        if(value instanceof JSONArray || value instanceof  Collection){
            ret = getListValue(value, fact);
        }else if(value instanceof JSONObject || value instanceof Map){
            ret = getMapValue(value, fact);
            singleListSeparator="; ";
        }else{
            ret = (value==null?"":value).toString().replaceAll("[{}\\[\\]\"]", "");
        }
        return ret;
    }
            
    private String getStructuredSingleListValue(Object value, MutableNewsExtractedData fact){
        StringBuilder stringValue = new StringBuilder();
        value = fact.getObjectValue(value);
        if(value instanceof JSONArray){
            stringValue.append("[");
            for(int j=0; j<((JSONArray)value).length(); j++){
                if (j>0){
                    stringValue.append(singleListSeparator);
                }
                stringValue.append(getStructuredSingeObjectValue(((JSONArray) value).get(j), fact));                                
            }
            stringValue.append("]");
    }else if(value instanceof Collection){
            boolean notFirst=false;
            stringValue.append("[");
            for(Object item: (Collection)value){
                if (notFirst){
                    stringValue.append(", ");
                }else{
                    notFirst=true;
                }
                stringValue.append(getStructuredSingeObjectValue(item, fact));                                
            }            
            stringValue.append("]");
        }else{
            stringValue.append(value==null?"":value.toString());
        }   
        return stringValue.toString();
    }
    
    private String getListValue(Object value, MutableNewsExtractedData fact){
        StringBuilder stringValue = new StringBuilder();
        value = fact.getObjectValue(value);
        if(value instanceof JSONArray){
            for(int j=0; j<((JSONArray)value).length(); j++){
                if (j>0){
                    stringValue.append(singleListSeparator);
                }
                stringValue.append(getStructuredValue(((JSONArray) value).get(j), fact));                                
            }
        }else if(value instanceof Collection){
            boolean notFirst=false;
            for(Object item: (Collection)value){
                if (notFirst){
                    stringValue.append(", ");
                }else{
                    notFirst=true;
                }
                stringValue.append(getStructuredValue(item, fact));                                
            }                            
        }else{
            stringValue.append(value==null?"":value.toString().replaceAll("[\\[\\]\"]", ""));
        }   
        return stringValue.toString();
    }
    
    private String getStructuredSingleMapValue(Object value, MutableNewsExtractedData fact){
        StringBuilder stringValue = new StringBuilder();
        value = fact.getObjectValue(value);
        if(value instanceof JSONObject){            
            boolean notFirst=false;
            stringValue.append("{");
            for(String key: ((JSONObject) value).keySet()){
                if (notFirst){
                    stringValue.append(", ");
                }else{
                    notFirst=true;
                }
                stringValue.append(getStructuredSingeObjectValue(((JSONObject) value).get(key), fact));                                
            }
            stringValue.append("}");
        }else if(value instanceof Map){
            boolean notFirst=false;
            stringValue.append("{");
            for(Object key: ((Map) value).keySet()){
                if (notFirst){
                    stringValue.append(", ");
                }else{
                    notFirst=true;
                }
                stringValue.append(getStructuredSingeObjectValue(((Map) value).get(key), fact));                                
            }
            stringValue.append("}");
        }else{
            stringValue.append((value==null?"":value).toString());
        }
        return stringValue.toString();
    }
            
    private String getMapValue(Object value, MutableNewsExtractedData fact){
        StringBuilder stringValue = new StringBuilder();
        value = fact.getObjectValue(value);
        if(value instanceof JSONObject){            
            boolean notFirst=false;
            for(String key: ((JSONObject) value).keySet()){
                if (notFirst){
                    stringValue.append(", ");
                }else{
                    notFirst=true;
                }
                stringValue.append(getStructuredValue(((JSONObject) value).get(key), fact));                                
            }
        }else if(value instanceof Map){
            boolean notFirst=false;
            for(Object key: ((Map) value).keySet()){
                if (notFirst){
                    stringValue.append(", ");
                }else{
                    notFirst=true;
                }
                stringValue.append(getStructuredValue(((Map) value).get(key), fact));                                
            }
        }else{
            stringValue.append((value==null?"":value).toString().replaceAll("[{}\"]", ""));
        }
        return stringValue.toString();
    }
            
    private FactDataAsString factToFactDataAsString(MutableNewsExtractedData fact, JSONArray headerFields){
        FactDataAsString ret = new FactDataAsString();
        StringBuilder row = new StringBuilder();
        for(int i=0; i<headerFields.length(); i++){
            Object obj = headerFields.get(i);
            if(obj instanceof JSONObject){
                JSONObject jobj = (JSONObject) obj;
                String type = jobj.optString("type", "");
                row.append(getPrePost(type));
                if(type.equals("joined_field")){
                    StringBuilder strb= new StringBuilder();
                    for(int fi=0; fi<jobj.getJSONArray("fields").length(); fi++){
                        strb.append(getFormatedValue(jobj.getJSONArray("fields").optString(fi), fact));
                        if(fi<jobj.getJSONArray("fields").length()-1){
                            strb.append(jobj.optString("separator", ""));
                        }
                    }
                    row.append(strb.toString());
                }else if(type.equals("list")){
                    if(multipleValuesAS.equals(multipleValuesAS.AS_LIST)){
                        if(jobj.optString("style", "").equals("single")){
                            singleListSeparator = ", ";
                            row.append(getListValue(fact.getAsObject(jobj.getString("field")), fact));
                        }else if(jobj.optString("style", "").equals("single_struct")){
                            row.append(getStructuredSingleListValue(fact.getAsObject(jobj.getString("field")), fact));
                        }else{
                            row.append(getFormatedValue(jobj.getString("field"), fact));
                        }                    
                    }else{
//                        String id = String.format("%s%02d", jobj.getString("field"), i);
//                        row.append(id);
//                        ret.putOtherTables(jobj.getString("field"), factToListOfFactDataAsString(id, fact.getExtractedData().getJSONArray(jobj.getString("field")), headerFields));
                    }
                }else if(type.equals("composed")){
                    if(multipleValuesAS.equals(multipleValuesAS.AS_LIST)){
                        if(jobj.optString("style", "").equals("single")){
                            Object o = fact.getAsObject(jobj.getString("field"));
                            row.append(getMapValue(fact.getAsObject(jobj.getString("field")), fact));
                        }else{
                            row.append(getFormatedValue(jobj.getString("field"), fact));
                        }
                    }else{
//                        String id = String.format("%s%02d", jobj.getString("field"), i);
//                        row.append(id);
//                        ret.putOtherTables(jobj.getString("field"), factToListOfFactDataAsString(id, fact.getExtractedData().getJSONArray(jobj.getString("field")), headerFields));
                    }                                        
                }else{
//                    stringValue.append(fact.getString(jobj.getString("field")));
                    row.append(getFormatedValue(jobj.getString("field"), fact));
                }
                row.append(getPrePost(jobj.optString("type", "")));
            }else if(obj!=null){
                row.append(getPrePost(""));
//                stringValue.append(fact.getString(obj.toString()));
                row.append(getFormatedValue(obj.toString(), fact));
                row.append(getPrePost(""));
            }else{
                row.append(getPrePost(""));
                row.append("--");
                row.append(getPrePost(""));
            }
            if(i+1<headerFields.length()){
                row.append(fieldSeparator);
            }
        }
        ret.setRow(row.toString());
        return ret;
    }
    
    private String getPrePost(String type){
        String ret;
        switch (type.toLowerCase()) {
            case "s":
            case "str":
            case "string":
                ret="\"";
                break;
            case "n":
            case "num":
            case "number":
            case "b":
            case "bool":
            case "boolean":
            case "i":
            case "int":
            case "integer":
            case "d":
            case "date":
            case "date_as_int":
            case "f":
            case "float":
                ret="";
                break;
            default:
                ret="\"";
        }
        return ret;
    }

//    public abstract String getCsvHeader();    
    protected String getCsvHeader(){
        return getCsvHeaderAsTables(headerFields);
    } 
    
    private String getCsvHeaderAsTables(JSONArray headerFields){
        StringBuilder ret = new StringBuilder();
        for(int i=0; i<headerFields.length(); i++){
            ret.append("\"");
            ret.append(getFieldName(headerFields.get(i), String.format("F%02d", i)));
            if(i+1<headerFields.length()){
                ret.append("\";");
            }else{
                ret.append("\"");
            }
        }
        return ret.toString();
    }    
    
    protected String getFormatedValue(String field, MutableNewsExtractedData fact){
        return fact.get(field).replaceAll("\\\"", "'").replaceAll("\n", "\\\\n");
    }
    
    private String getFieldName(Object headerField, String def){
        String ret;
        if(headerField instanceof JSONObject){
            JSONObject jobj = (JSONObject) headerField;
            if(jobj.has("name")){
                ret=jobj.getString("name");
            }else{
                ret=jobj.getString("field");
            }
        }else if(headerField!=null){
            ret=headerField.toString();
        }else{
            ret=def;
        }
        return ret;
    }
    
    @Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        if(!isAppendFile()){
            stb.append(getCsvHeader());
        }
        for(T fact: this.getList()){
            stb.append(this.factToFactDataAsString(fact).getRow());
            stb.append("\n");
        }
        return stb.toString();
    }

    @Override
    public void toFile(String outputFileName) {
        toFile(outputFileName, (param) -> {
            return  factToFactDataAsString(param).getRow();
        });
    }    
    
    protected void toFile(String outputFileName, Callback<T, String> rowTransformer) {        
        if(!outputFileName.endsWith(".csv")){
            outputFileName = outputFileName.concat(".csv");
        }
        File f = (new File(outputFileName)).getAbsoluteFile();
        if(f.getParentFile()!=null){
            f.getParentFile().mkdirs();
        }
        try(BufferedWriter fw = new BufferedWriter(new FileWriter(f, isAppendFile()))){
            if(!isAppendFile()){
                fw.write(getCsvHeader());
                fw.newLine();
            }
            for(T fact: this.getList()){
                fw.write(rowTransformer.call(fact));
                fw.newLine();
            }            
        } catch (IOException ex) {
//            Logger.getLogger(GenericCsvFileFormatter.class.getName()).log(Level.SEVERE, null, ex);
            throw new AutoNewsRuntimeException(String.format("File %s doesn't exist or can't be read. Pleas revise you", f), ex);
        }
    }    


    public boolean isAppendFile() {
        return appendFile;
    }

    public void setAppendFile(boolean appendFile) {
        this.appendFile = appendFile;
    }
    
    public static enum MultiValuesType{
        AS_LIST,
        AS_TABLES
    }

    /**
     * @return the listOfFacts
     */
    public List<T> getList() {
        return list;
    }

    /**
     * @param list the listOfFacts to set
     */
    public void setList(List<T> list) {
        this.list = list;
    }

    /**
     * @return the headerFields
     */
    public JSONArray getHeaderFields() {
        return headerFields;
    }

    /**
     * @param headerFields the headerFields to set
     */
    public void setHeaderFields(JSONArray headerFields) {
        this.headerFields = headerFields;
    }

    /**
     * @return the multipleValuesAS
     */
    public MultiValuesType getMultipleValuesAS() {
        return multipleValuesAS;
    }

    /**
     * @param multipleValuesAS the multipleValuesAS to set
     */
    public void setMultipleValuesAS(MultiValuesType multipleValuesAS) {
        this.multipleValuesAS = multipleValuesAS;
    }
    
    private static class FactDataAsString{
        String row;
        Map<String, List<FactDataAsString>> otherTables;

        public FactDataAsString() {
            otherTables = new HashMap<>();
        }

        public FactDataAsString(String row) {
            this.row = row;
            otherTables = new HashMap<>();
        }
        
        
        public FactDataAsString(String row, Map<String, List<FactDataAsString>> otherTables) {
            this.row = row;
            this.otherTables = otherTables; 
        }
        
        public void putOtherTables(String field, List<FactDataAsString> factDataAsString){
            List<FactDataAsString> l;
            if(otherTables.containsKey(field)){
                l = otherTables.get(field);
            }else{
                l = new ArrayList<>();
                otherTables.put(field, l);
            }
            l.addAll(factDataAsString);
        }
        
        public void addOtherTables(String field, FactDataAsString factDataAsString){
            List<FactDataAsString> l;
            if(otherTables.containsKey(field)){
                l = otherTables.get(field);
            }else{
                l = new ArrayList<>();
                otherTables.put(field, l);
            }
            l.add(factDataAsString);
        }
        
        public void setRow(String r){
            row = r;
        }

        public String getRow(){
            return row;
        }

        public Map<String, List<FactDataAsString>> getOtherTables(){
            return otherTables;
        }
    }
}
