package org.elsquatrecaps.autonewsextractor.dataextractor.parser;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elsquatrecaps.autonewsextractor.error.AutoNewsRuntimeException;
import org.elsquatrecaps.autonewsextractor.model.ExtractedData;
import org.elsquatrecaps.autonewsextractor.model.MutableNewsExtractedData;
import org.elsquatrecaps.autonewsextractor.tools.configuration.ParserConfiguration;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author josep
 * @param <E>
 */
public class MainExtractorParser<E extends ExtractedData> implements ExtractorParser<E>{
    Configuration configuration;
    JSONObject jsonConfig;
    int parserId=0;
    

    @Override
    public void init(Configuration configuration) {
        boolean error=false;
        String jsc=null;
        String message = null;
        this.configuration = configuration;
        ParserConfiguration conf = (ParserConfiguration) configuration;
        if(conf.getParserConfigJsonFile()!=null && jsonConfig==null){
            try{
                jsc = Files.readString(Paths.get(conf.getParserConfigJsonFile()));
                jsonConfig = new JSONObject(jsc);
            } catch (JSONException ex) {
                message = "Probably the json content of file %s is a bad json format";
                error = true;
            } catch (IOException ex) {
                //Logger.getLogger(MainExtractorParser.class.getName()).log(Level.SEVERE, null, ex);
                String filename = conf.getParserConfigJsonFile();
                if(filename ==null || filename.isEmpty() || filename.isBlank()){
                    message = "The parameter 'parser_config_json_file' in properties file, is not defined or its value doesn't referred and existing file";
                }else if(jsc==null){
                    message = "The value of 'parser_config_json_file' in properties file has a bad name, please revise it.";
                }else{
                    message = "";
                }
                error = true;
            }
            if(error){
                throw new AutoNewsRuntimeException(String.format("Ther are som problem reading the JSON configuration for the extractors defined. %s", message));
            }
        }
    }

//    @Override
//    public void init(Configuration configuration, int parserId) {
//        this.configuration = configuration;
//        this.parserId = parserId;
//    }

    @Override
    public void init(JSONObject jsonConfig) {
        this.jsonConfig = jsonConfig;       
    }

    @Override
    public List<E> parseFromString(String bonText, int parserId) {
        this.parserId = parserId;
        return parseFromString(bonText);
    }

    @Override
    public List<E> parseFromString(String bonText) {
        List<E> data = new ArrayList<>();
        JSONArray localJsonConfigParserModel = this.jsonConfig.getJSONObject(((ParserConfiguration)configuration).getParseModel()[parserId]).getJSONArray("config");
        JSONObject constants = this.jsonConfig.optJSONObject(((ParserConfiguration)configuration).getParseModel()[parserId]).getJSONObject("constants");
        String approach = localJsonConfigParserModel.getJSONObject(0).getString("approach_type");
        ProxyAutoNewsExtractorParser proxy;
        if(constants==null){
            proxy = ProxyAutoNewsExtractorParser.getInstance(approach, configuration, parserId, localJsonConfigParserModel.getJSONObject(0).getJSONObject("configuration"));
        }else{
            proxy = ProxyAutoNewsExtractorParser.getInstance(approach, configuration, parserId, localJsonConfigParserModel.getJSONObject(0).getJSONObject("configuration"), constants);
        }
        MutableNewsExtractedData defaultData = getDefaultExtractedDate(proxy);
        data = (List<E>) proxy.parseFromString(bonText, defaultData);
        return (List<E>) parseFromExtractedDataList(data, 1);
    }
    
    protected MutableNewsExtractedData getDefaultExtractedDate(ProxyAutoNewsExtractorParser proxy){
        MutableNewsExtractedData defaultData = proxy.getDefaultData();
        return defaultData;
    }
    
    private List<E> parseFromExtractedDataList(List<E> list, int parserDepth) {
        List<E> ret;
        JSONArray localJsonConfigParserModel = this.jsonConfig.getJSONObject(((ParserConfiguration)configuration).getParseModel()[parserId]).getJSONArray("config");

        if(parserDepth<localJsonConfigParserModel.length()){
            List<E> l = new ArrayList<>();
            String approach = localJsonConfigParserModel.getJSONObject(parserDepth).getString("approach_type");
            JSONObject constants = this.jsonConfig.optJSONObject(((ParserConfiguration)configuration).getParseModel()[parserId]).getJSONObject("constants");
            ProxyAutoNewsExtractorParser proxy;
            if(constants==null){
                proxy = ProxyAutoNewsExtractorParser.getInstance(approach, configuration, parserId, localJsonConfigParserModel.getJSONObject(parserDepth).getJSONObject("configuration"));
            }else{
                proxy = ProxyAutoNewsExtractorParser.getInstance(approach, configuration, parserId, localJsonConfigParserModel.getJSONObject(parserDepth).getJSONObject("configuration"), constants);
            }
            for(ExtractedData extractedData: list){
                MutableNewsExtractedData mutableExtractedData = (MutableNewsExtractedData) extractedData;
                proxy.updateDefaultData(mutableExtractedData);
                l.addAll((Collection<? extends E>) proxy.parseFromString(extractedData.getUnparsedText(), mutableExtractedData));
            }
            ret =  parseFromExtractedDataList(l, parserDepth+1);
        }else{
            ret =  list;
        }
        return ret;
    }

    @Override
    public JSONArray getFieldsProperties() {
        return this.jsonConfig.getJSONObject(((ParserConfiguration)configuration).getParseModel()[parserId]).getJSONArray("fields");
    }
}
