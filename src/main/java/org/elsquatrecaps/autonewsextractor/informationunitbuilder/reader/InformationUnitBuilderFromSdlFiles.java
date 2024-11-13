package org.elsquatrecaps.autonewsextractor.informationunitbuilder.reader;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import org.elsquatrecaps.autonewsextractor.error.AutoNewsRuntimeException;
import org.elsquatrecaps.autonewsextractor.model.PublicationInfo;
import org.elsquatrecaps.autonewsextractor.tools.ReaderTools;
import org.elsquatrecaps.autonewsextractor.tools.RegexBuilder;
import org.elsquatrecaps.autonewsextractor.tools.configuration.Configurable;
import org.elsquatrecaps.autonewsextractor.tools.configuration.InformationUnitBuilderrConfiguration;
import org.elsquatrecaps.autonewsextractor.tools.configuration.RegexConfiguration;
import org.elsquatrecaps.utilities.tools.Callback;

/**
 *
 * 
 * @author josepcanellas
 */

@InformationUnitBuilderMarkerAnnotation(joinerType = "file_name", metadataSource = "portada_file_name")
public class InformationUnitBuilderFromSdlFiles extends AbstractReader implements InformationUnitBuilder, Configurable<InformationUnitBuilderFromSdlFiles>{
    static final int QUANTITY_OF_CHARACTERS_TO_COMPARE = 26;
    private InformationUnitBuilderrConfiguration especificConfigurator;
    
    @Override
    public <Configuration> InformationUnitBuilderFromSdlFiles init(Configuration conf){
        especificConfigurator = (InformationUnitBuilderrConfiguration) conf;
        return this;
    }
    
    @Override
    public void createAndProcessEachInformationUnitText(Callback<InformationUnitDataParamsFromTextsForCallback, Void> callbak, 
            PublicationInfo publicationInfo, 
            String... text){
        _createAndProcessEachInformationUnit(text, 
                publicationInfo.getModelVersion(),
                publicationInfo.getPublicationDate(),
                publicationInfo.getPublicationName(),
                /*publicationInfo.getPublicationPlace(),*/
                publicationInfo.getPublicationEdition(),
                null, 
                callbak);   
    }
    
    @Override
    public void createAndProcessEachInformationUnitFiles(Callback<InformationUnitDataParamsFromFilesForCallback, Void> callbak, String modelVersion){
        _createAndProcessEachInformationUnit(
                especificConfigurator.getOriginDir(), 
                especificConfigurator.getFileExtension(), 
                modelVersion,
                callbak
        );
    }
    
    @Override
    public void createAndProcessEachInformationUnitFiles(Callback<InformationUnitDataParamsFromFilesForCallback, Void> callbak, 
            PublicationInfo publicationInfo){        
        _createAndProcessEachInformationUnit(
                especificConfigurator.getOriginDir(), 
                especificConfigurator.getFileExtension(), 
                publicationInfo.getModelVersion(),
                callbak
        );
    }
    
    private void _createAndProcessEachInformationUnit(
            String[] textArray, 
            String modelVersion, 
            Date publicationDate, 
            String publicationName, 
            /*String publicationPlace, */
            String publicationEdition, 
            String[] pageNumber, 
            Callback<? extends InformationUnitDataParamsForCallback, Void> callbak){
        StringBuilder bonText = new StringBuilder();
        for(String text: textArray){
            appendText(bonText, __prepareTextFromFile(text));
        }
        _createAndProcessEachInformationUnit(bonText.toString(), 
                (List<String>) null,
                new PublicationInfo(modelVersion, publicationDate, publicationName, /*publicationPlace, */ publicationEdition, pageNumber), 
                callbak);
    }
    
    private <T extends InformationUnitDataParamsForCallback> void _createAndProcessEachInformationUnit(String originDir, 
            String extension, String modelVersion, 
            Callback<T, Void> callbak){
        File dirBase;
        dirBase = new File(originDir);
        String[] fDades = dirBase.list((File file, String string) -> 
                string.endsWith(extension));
        Arrays.sort(fDades);
        int i=0;
        List<String> files;
        while(i<fDades.length){
            if(fDades[i].matches("\\d{4}_\\d{2}_\\d{2}_[A-Z]{3}_[A-Z]{2}_.*")){
                files = new ArrayList<>();
                files.add(fDades[i]);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
                String dateInString = fDades[i].substring(0, 10);
                String place = fDades[i].substring(11, 14);
                String npid = fDades[i].substring(15, 17);
                String gnum = fDades[i].substring(18, 23);
                String nped = fDades[i].substring(24, 25);
                String nppg = fDades[i].substring(26, 28);
                List<String> pages = new ArrayList<>();
                pages.add(nppg);
                int next=0;
                while(i+next+1<fDades.length 
                        && fDades[i+next+1].substring(0, QUANTITY_OF_CHARACTERS_TO_COMPARE)
                                .equals(fDades[i].substring(0, QUANTITY_OF_CHARACTERS_TO_COMPARE))){
                    next++;
                    files.add(fDades[i+next]);
                    nppg = fDades[i+next].substring(26, 28);
                    if(!pages.get(pages.size()-1).equals(nppg)){
                        pages.add(nppg);
                    }
                }
                i+=next;
//                try{
                    String[] pagesList = new String[pages.size()];
                    pagesList = pages.toArray(pagesList);
                    String text = readFileAndGetText(files);
                    _createAndProcessEachInformationUnit(text, files, 
                            new PublicationInfo(modelVersion, dateInString, npid, nped, pagesList), 
                            (Callback<InformationUnitDataParamsForCallback, Void>) callbak);
//                }catch (ParseException e) {
//                    InformationUnitDataParamsFromErrorForCallback inf = new ImplInformationUnitDataParamsForCallback(e);
//                    callbak.call((T) inf);
//                    throw new AutoNewsRuntimeException(e);
//                }
            }
            i++;
        }
    } 
    
    

    private <T extends InformationUnitDataParamsForCallback> void _createAndProcessEachInformationUnit(String text, List<String> filesList, 
            PublicationInfo publicationInfo, Callback<T, Void> callbak){
                    InformationUnitDataParamsFromFilesForCallback inf = new ImplInformationUnitDataParamsForCallback(publicationInfo, text, filesList);
                    callbak.call((T) inf);
        
    }
    
    @Override
    protected String prepareTextFromFile(String file) {
        String rawText;
        String bonText;
        try{
            rawText = ReaderTools.textFileRawContent(especificConfigurator.getOriginDir(), file);
            bonText = __prepareTextFromFile(rawText);
        } catch (IOException ex) {
//            Logger.getLogger(TragetFragmentBreakerByRegex.class.getName()).log(Level.SEVERE, null, ex);
            throw new AutoNewsRuntimeException(String.format("The file %s doesn't exist or can't be read", file), ex);
        }
        return bonText;
    }

        
    private String __prepareTextFromFile(String rawText) {
        String bonText;
        String regex_name = "is_page_number";
        try {
            RegexConfiguration regexConfiguration = (RegexConfiguration) especificConfigurator;        
            Pattern pattern = RegexBuilder.getInstance(regexConfiguration).buildRegex(regex_name);
            rawText = rawText.replaceAll("\r\n", "\n").trim();   
            Matcher matcher = pattern.matcher(rawText);
            while (matcher.matches()) {
                //rawText = rawText.substring(rawText.indexOf("\n") + 1);
                rawText = matcher.group(1);
                matcher = pattern.matcher(rawText);
            }
            bonText = rawText;
            if(false){
                bonText = ReaderTools.singleLf2Space(rawText);
            }
            if(false){
                bonText = ReaderTools.doubleLf2SingleLf(bonText);
            }
        } catch (PatternSyntaxException ex) {
            throw new AutoNewsRuntimeException(String.format("The expression regular named '%s' is bad. Please, revise it", regex_name));
        }
        return bonText;
    }
  
  
//    @Override
//    public JSONArray getAllInformationUnitAsJson() {
//        final JSONArray ret = new JSONArray();
//        createAndProcessEachInformationUnitText((param) -> {
//            JSONObject obj = new JSONObject();
//            obj.put("publication_info", param.getPublicationInfo());
//            obj.put("text", param.getInformationUnitText());
//            ret.put(obj);
//            return null; 
//        });
//        return ret;
//    }

//    @Override
//    public List<String> getAllInformationUnitAsJson() {
//        createAndProcessEachInformationUnit(callbak);
//    }

}
