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
public /*abstract*/ class GenericCsvFileFormatter<T extends MutableNewsExtractedData> implements FileFormatter{
    private boolean appendFile = false;
    private List<T> list = new ArrayList<>();
    private JSONArray headerFields;
    private MultiValuesType multipleValuesAS = MultiValuesType.AS_LIST;
    
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
    
    public GenericCsvFileFormatter configHeaderFileds(JSONArray headerFields){
        this.setHeaderFields(headerFields);
        return this;
    }
    
//    public abstract String factToFactDataAsString(T fact);
    private FactDataAsString factToFactDataAsString(T fact){
        return GenericCsvFileFormatter.this.factToFactDataAsString(fact.getExtractedData(), headerFields);
    }
    
    private String factToString(JSONObject dataObject, JSONArray headerFields){
        StringBuilder listOfFacts = new StringBuilder();
        listOfFacts.append("{");
        for(int i=0; i<headerFields.length(); i++){
            listOfFacts.append(getFieldName(headerFields.getString(i), "?"));
            listOfFacts.append(": ");
            FactDataAsString f = factToFactDataAsString(dataObject, headerFields);
            listOfFacts.append(f.getRow());
        }
        listOfFacts.append("}");
        return listOfFacts.toString();
    }
    
    private String factToString(JSONArray dataList, JSONArray headerFields){
        StringBuilder listOfFacts = new StringBuilder();
        listOfFacts.append("[");
        for(int i=0; i<dataList.length(); i++){
            FactDataAsString f = GenericCsvFileFormatter.this.factToFactDataAsString(dataList.getJSONObject(i), headerFields);
            listOfFacts.append(f.getRow());
        }
        listOfFacts.append("]");
        return listOfFacts.toString();
    }
    
    private List<FactDataAsString> factToListOfFactDataAsString(String referId, JSONArray dataList, JSONArray headerFields){
        List<FactDataAsString> listOfFacts = new ArrayList<>();
        for(int i=0; i<dataList.length(); i++){
            FactDataAsString f = GenericCsvFileFormatter.this.factToFactDataAsString(dataList.getJSONObject(i), headerFields);
            f.setRow(String.format("\"%s\";%s", referId, f.getRow()));
            listOfFacts.add(f);
        }
        return listOfFacts;
    }
    
    private FactDataAsString factToFactDataAsString(JSONObject fact, JSONArray headerFields){
        FactDataAsString ret = new FactDataAsString();
        StringBuilder row = new StringBuilder();
        for(int i=0; i<headerFields.length(); i++){
            Object obj = headerFields.get(i);
            if(obj instanceof JSONObject){
                JSONObject jobj = (JSONObject) obj;
                row.append(getPrePost(jobj.optString("type", "")));
                if(jobj.has("composed_by")){
                    if(multipleValuesAS.equals(multipleValuesAS.AS_LIST)){
                        row.append(factToString(fact.getJSONArray(jobj.getString("field")), jobj.getJSONArray("composed_by")));
                    }else{
                        String id = String.format("%s%02d", jobj.getString("field"), i);
                        row.append(id);
                        ret.putOtherTables(jobj.getString("field"), factToListOfFactDataAsString(id, fact.getJSONArray(jobj.getString("field")), headerFields));
                    }
                }else{
                    row.append(fact.getString(jobj.getString("field")));
                }
                row.append(getPrePost(jobj.optString("type", "")));
            }else if(obj!=null){
                row.append(getPrePost(""));
                row.append(obj.toString());
                row.append(getPrePost(""));
            }else{
                row.append(getPrePost(""));
                row.append("--");
                row.append(getPrePost(""));
            }
            if(i+1<headerFields.length()){
                row.append(";");
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
