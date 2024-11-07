/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.elsquatrecaps.autonewsextractor.tools.formatter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.elsquatrecaps.autonewsextractor.error.AutoNewsRuntimeException;
import org.elsquatrecaps.autonewsextractor.model.ExtractedData;
import org.elsquatrecaps.autonewsextractor.model.MutableNewsExtractedData;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author josepcanellas
 * @param <T>
 */
public class JsonFileFormatterForExtractedData<T extends ExtractedData> implements FileFormatter{
    List<T> data;
    boolean printOnlyOneValueForField = false;

    public JsonFileFormatterForExtractedData(List<T> data) {
        this.data = data;
    }
    
    public JsonFileFormatterForExtractedData(List<T> data, boolean printOnlyOneValueForField) {
        this.data = data;
        this.printOnlyOneValueForField = printOnlyOneValueForField;
    }
    
    @Override
    public void toFile(String outputFileName) {
        if(!outputFileName.endsWith(".json")){
            outputFileName = outputFileName.concat(".json");
        }
        File f = (new File(outputFileName)).getAbsoluteFile();
        if(f.getParentFile()!=null){
            f.getParentFile().mkdirs();
        }        
        try (FileWriter fw = new FileWriter(f)){
            fw.append(toString());
        } catch (IOException ex) {
//            Logger.getLogger(JsonFileFormatterForExtractedData.class.getName()).log(Level.SEVERE, null, ex);
            throw new AutoNewsRuntimeException(String.format("File %s can't be written. Please revise the permissions", outputFileName), ex);
        }
    }
    
    @Override
    public String toString() {
        JSONArray list = new JSONArray();
        for(int i=0; i<data.size(); i++){
            if(data.get(i) instanceof MutableNewsExtractedData){
                //list.put(i, ((MutableNewsExtractedData)(data.get(i))).getExtractedData());
                if(printOnlyOneValueForField){
                    JSONObject jobj = new JSONObject(data.get(i).getAllDataAsJson(printOnlyOneValueForField));
                    list.put(i, jobj);
                }else{
                    list.put(i, ((MutableNewsExtractedData)(data.get(i))).getExtractedData());
                }
            }else{
                JSONObject jobj = new JSONObject(data.get(i).getAllDataAsJson(printOnlyOneValueForField));
                list.put(i, jobj);
            }
        }        
        return list.toString(4);
    }
}
