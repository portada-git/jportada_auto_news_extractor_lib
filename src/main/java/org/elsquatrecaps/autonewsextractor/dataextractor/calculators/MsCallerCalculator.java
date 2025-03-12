package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elsquatrecaps.autonewsextractor.dataextractor.parser.OpenaiExtractorParser;
import org.elsquatrecaps.portada.jportadamscaller.ConnectionMs;
import org.elsquatrecaps.portada.jportadamscaller.PortadaMicroservicesCaller;
import org.elsquatrecaps.portada.jportadamscaller.exceptions.PortadaMicroserviceCallException;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author josep
 */
@DataExtractorCalculatorMarkerAnnotation(id = "MsCallerCalculator")
public class MsCallerCalculator extends AbstractCalculator<Object> {
    PortadaMicroservicesCaller caller = new PortadaMicroservicesCaller();
    String context;
    String entryPoint;
    
 @Override
    public void init(Object obj){
        if(obj instanceof JSONObject && ((JSONObject)obj).has("context")){
            String propertiesPath = ((JSONObject)obj).getString("microservice_initializer_file");
            Properties ms_properties = new Properties();
            try(FileReader fr = new FileReader(propertiesPath)){
                ms_properties.load(fr);
            } catch (IOException ex) {
                throw new RuntimeException("Can't load the config file to call to microservice.");
            }
            caller.init(ms_properties);        
            context = ((JSONObject) obj).getString("context");
            entryPoint = ((JSONObject) obj).getString("entry_point");
        }else if(obj instanceof JSONObject && ((JSONObject)obj).has("host")){
            Map<String, ConnectionMs> conDataList = new HashMap<>();
            conDataList.put("MsCallerCalculator", new ConnectionMs(
                    ((JSONObject)obj).getString("protocol"), 
                    ((JSONObject)obj).getString("port"), 
                    ((JSONObject)obj).getString("host"), 
                    ((JSONObject)obj).getString("pref")));
            caller.init(conDataList);
            context = "MsCallerCalculator";
            entryPoint = ((JSONObject) obj).getString("entry_point");
        }else{
            super.init(obj);
        }
    }
    
    @Override
    public Object calculate(Object[] param) {
        Object ret = null;
        JSONObject p = new JSONObject();
        p.put("parameters_by_position", new JSONArray(param));
        try {
            JSONObject resp = new JSONObject(caller.sendPostAsFormatParams(entryPoint, context, p, String.class));
            if(resp.getInt("status")==0){
                ret = resp.get("value");
            }else{
                Logger.getLogger(OpenaiExtractorParser.class.getName()).log(Level.WARNING, resp.getString("message"));
            }
        } catch (PortadaMicroserviceCallException ex) {
            Logger.getLogger(OpenaiExtractorParser.class.getName()).log(Level.SEVERE, null, ex);
            //Generate error
        }        
        return ret;
    }    
}
