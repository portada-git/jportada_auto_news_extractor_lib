package org.elsquatrecaps.autonewsextractor.informationunitbuilder.runnable;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elsquatrecaps.autonewsextractor.targetfragmentbreaker.reader.TargetFragmentBreakerProxyClass;
import org.elsquatrecaps.autonewsextractor.tools.configuration.Configurable;
import org.elsquatrecaps.autonewsextractor.tools.configuration.InformationUnitBuilderrConfiguration;
import org.elsquatrecaps.utilities.tools.Callback;
import org.elsquatrecaps.utilities.tools.configuration.Configuration;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * 
 * @author josepcanellas
 */

@InformationUnitBuilderMarkerAnnotation(joinerType = "file_name", metadataSource = "sdl_file_name")
public class InformationUnitBuilderFromSdlFiles implements InformationUnitBuilder, Configurable{
    static final int QUANTITY_OF_CHARACTERS_TO_COMPARE = 30;
    private InformationUnitBuilderrConfiguration especificConfigurator;
    
    @Override
    public void init(Configuration conf){
        especificConfigurator = (InformationUnitBuilderrConfiguration) conf;
    }
    
    @Override
    public void run(){
        createAndProcessEachInformationUnitText((param) -> {
            System.out.println(param.getInformationUnitText());
            return null;
        });        
    } 
    
    @Override
    public void createAndProcessEachInformationUnitText(Callback<? extends TextInformationUnitDataParamsForCallback, Void> callbak){
        _createAndProcessEachInformationUnit(
                especificConfigurator.getOriginDir(), 
                especificConfigurator.getFileExtension(), 
                (Callback<ImplInformationUnitDataParamsForCallback, Void>) callbak);   
    }
    
    @Override
    public void createAndProcessEachInformationUnitFiles(Callback<? extends FileInformationUnitDataParamsForCallback, Void> callbak){
        _createAndProcessEachInformationUnit(
                especificConfigurator.getOriginDir(), 
                especificConfigurator.getFileExtension(), 
                (Callback<ImplInformationUnitDataParamsForCallback, Void>) callbak
        );
    }
    
    public  static void _createAndProcessEachInformationUnit(String originDir, String extension, Callback<ImplInformationUnitDataParamsForCallback, Void> callbak){
        File dirBase;

        dirBase = new File(originDir);
        String[] fDades = dirBase.list((File file, String string) -> 
                string.endsWith(extension));
        Arrays.sort(fDades);
        int i=0;
        ArrayList<String> files;
        while(i<fDades.length){
            if(fDades[i].matches("\\d{4}_\\d{4}_\\d{2}_\\d{2}.*")){
                String oldFileName = fDades[i];
                fDades[i] = oldFileName.substring(5);
                File f = new File(dirBase, oldFileName);
                f.renameTo(new File(dirBase, fDades[i]));                
            }
            if(fDades[i].matches("\\d{4}_\\d{2}_\\d{2}.*")){
                files = new ArrayList<>();
                files.add(fDades[i]);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
                String dateInString = files.get(0).substring(0, 10);
                int next=0;
                while(i+next+1<fDades.length 
                        && fDades[i+next+1].substring(0, QUANTITY_OF_CHARACTERS_TO_COMPARE)
                                .equals(fDades[i].substring(0, QUANTITY_OF_CHARACTERS_TO_COMPARE))){
                    next++;
                    files.add(fDades[i+next]);
                }
                i+=next;
                try{
                    Date date = formatter.parse(dateInString);
                    callbak.call(new ImplInformationUnitDataParamsForCallback(date, files));
                }catch (ParseException e) {
                    callbak.call(new ImplInformationUnitDataParamsForCallback(e));
                    Logger.getLogger(InformationUnitBuilderFromSdlFiles.class.getName()).log(Level.SEVERE, e.getMessage(), e);
                }
            }
            i++;
        }
    } 

    @Override
    public JSONArray getAllInformationUnitAsJson() {
        final JSONArray ret = new JSONArray();
        createAndProcessEachInformationUnitText((param) -> {
            JSONObject obj = new JSONObject();
            obj.put("date", param.getDate());
            obj.put("text", param.getInformationUnitText());
            ret.put(obj);
            return null; 
        });
        return ret;
    }

//    @Override
//    public List<String> getAllInformationUnitAsJson() {
//        createAndProcessEachInformationUnit(callbak);
//    }
    
    protected static interface BaseInformationUnitDataParamsForCallback{
        Date getDate();
        
        boolean hasError();

        Exception getException();
    }

    public static interface FileInformationUnitDataParamsForCallback extends BaseInformationUnitDataParamsForCallback{
        ArrayList<String> getFiles();
    }
    
    public static interface TextInformationUnitDataParamsForCallback extends BaseInformationUnitDataParamsForCallback{
        String getInformationUnitText();
    }
    
    public static final class ImplInformationUnitDataParamsForCallback implements FileInformationUnitDataParamsForCallback, TextInformationUnitDataParamsForCallback{
        private boolean error=false;
        private Date date;
        private String text;
        private ArrayList<String> files;
        private Configuration configuration;
        private Exception exception=null;

        public ImplInformationUnitDataParamsForCallback(Date date, String text, Configuration configuration) {
            this(date, text);
            this.configuration = configuration;
        }

        public ImplInformationUnitDataParamsForCallback(Date date, String text) {
            this.date = date;
            this.text = text;
        }

        public ImplInformationUnitDataParamsForCallback(Date date, ArrayList<String> files, Configuration configuration) {
            this(date, files);
            this.configuration = configuration;
        }

        public ImplInformationUnitDataParamsForCallback(Date date, ArrayList<String> files) {
            this.date = date;
            this.files = files;
        }

        public ImplInformationUnitDataParamsForCallback(Exception ex) {
            error=true;
            exception=ex;
        }

        /**
         * @return the date
         */
        public Date getDate() {
            return date;
        }

        /**
         * @return the files
         */
        public ArrayList<String> getFiles() {
            return files;
        }

        /**
         * @return the configuration
         */
        public Configuration getConfiguration() {
            return configuration;
        }

        /**
         * @return the error
         */
        public boolean hasError() {
            return error;
        }

        /**
         * @return the exception
         */
        public Exception getException() {
            return exception;
        }

        @Override
        public String getInformationUnitText() {
            return text;
        }
    }
}
