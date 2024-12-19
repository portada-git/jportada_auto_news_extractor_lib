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
import org.elsquatrecaps.utilities.iterators.ConsumerIterator;
import org.elsquatrecaps.utilities.tools.Callback;

/**
 *
 * 
 * @author josepcanellas
 */

@InformationUnitBuilderMarkerAnnotation(joinerType = "file_name", metadataSource = "portada_file_name")
public class InformationUnitBuilderFromSdlFiles extends AbstractReader implements InformationUnitBuilder, Configurable<InformationUnitBuilderFromSdlFiles>{
    private static final Pattern patternIsPage = Pattern.compile("^\\s*(?:[\\d(IlOoSt»]){0,4}\\n(.*)", Pattern.DOTALL | Pattern.UNICODE_CASE | Pattern.UNICODE_CHARACTER_CLASS);             
    private int quantity_of_characters_to_compare = 20;
    private InformationUnitBuilderrConfiguration especificConfigurator;
    
    @Override
    public <Configuration> InformationUnitBuilderFromSdlFiles init(Configuration conf){
        especificConfigurator = (InformationUnitBuilderrConfiguration) conf;
        if(especificConfigurator.getAttr("quantity_of_characters_to_compare")!=null){
            quantity_of_characters_to_compare = especificConfigurator.getAttr("quantity_of_characters_to_compare");
        }
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
    public void createAndProcessEachInformationUnitFiles(
            Callback<InformationUnitDataParamsFromFilesForCallback, Void> callbak, 
            String originDir, 
            String modelVersion){
        _createAndProcessEachInformationUnit(
                originDir, 
                "txt", 
                modelVersion,
                callbak
        );
    }

    @Override
    public void createAndProcessEachInformationUnitFiles(
            Callback<InformationUnitDataParamsFromFilesForCallback, Void> callbak, 
            ConsumerIterator<DataToProcess> iterator,
            String modelVersion){
        _createAndProcessEachInformationUnit(
                iterator, 
                modelVersion,
                callbak
        );
    }

    @Override
    public void createAndProcessEachInformationUnitFiles(
            Callback<InformationUnitDataParamsFromFilesForCallback, Void> callbak, 
            String originDir, 
            String extension, 
            String modelVersion){
        _createAndProcessEachInformationUnit(
                originDir, 
                extension, 
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
                "",
                (List<String>) null,
                new PublicationInfo(modelVersion, publicationDate, publicationName, /*publicationPlace, */ publicationEdition, pageNumber), 
                0,//TODO
                callbak);
    }
    
    private <T extends InformationUnitDataParamsForCallback> void _createAndProcessEachInformationUnit(ConsumerIterator<DataToProcess> iterator,
            String modelVersion, 
            Callback<T, Void> callbak){
        List<String> allText;
        List<String> allNames;
        while(iterator.hasNext()){
            DataToProcess toProcess = iterator.next();
            if(toProcess.getName().matches("\\d{4}_\\d{2}_\\d{2}_[A-Z]{3}_[A-Z]{2}_[A-Z]_\\d{2}.*")){
                String name = toProcess.getName().substring(0, quantity_of_characters_to_compare);
                allText = new ArrayList<>();
                allNames = new ArrayList<>();
                allText.add(toProcess.getText());
                allNames.add(toProcess.getName());
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
                String dateInString = toProcess.getName().substring(0, 10);
                String place = toProcess.getName().substring(11, 14);
                String npid = toProcess.getName().substring(15, 17);
                String nped = toProcess.getName().substring(18, 19);
                String nppg = toProcess.getName().substring(20, 22);
                List<String> pages = new ArrayList<>();
                pages.add(nppg);
                while(iterator.hasNext() 
                        && iterator.examineNextValue() != null
                        && iterator.examineNextValue().getName().length()>quantity_of_characters_to_compare+2
                        && iterator.examineNextValue().getName().substring(0, quantity_of_characters_to_compare)
                                .equals(iterator.examineNextValue().getName().substring(0, quantity_of_characters_to_compare))){
                    DataToProcess iuMember = iterator.next();
                    allText.add(iuMember.getText());
                    allNames.add(iuMember.getName());
                    nppg = iuMember.getName().substring(20, 22);
                    if(!pages.get(pages.size()-1).equals(nppg)){
                        pages.add(nppg);
                    }
                }
                String[] pagesList = new String[pages.size()];
                pagesList = pages.toArray(pagesList);
                String text = getText(allText);
                _createAndProcessEachInformationUnit(text, name, allNames, 
                        new PublicationInfo(modelVersion, dateInString, npid, nped, pagesList), 
                        0,//TODO
                        (Callback<InformationUnitDataParamsForCallback, Void>) callbak);
            }
        }
    }

    private <T extends InformationUnitDataParamsForCallback> void _createAndProcessEachInformationUnit(String originDir, 
            String extension, String modelVersion, 
            Callback<T, Void> callbak){
        File dirBase;
        this.setOriginDir(originDir);
        dirBase = new File(originDir);
        String[] fDades = dirBase.list((File file, String string) -> 
                string.endsWith(extension));
        Arrays.sort(fDades);
        int i=0;
        List<String> files;
        while(i<fDades.length){
            if(fDades[i].matches("\\d{4}_\\d{2}_\\d{2}_[A-Z]{3}_[A-Z]{2}_[A-Z]_\\d{2}.*")){
                String name = fDades[i].substring(0, quantity_of_characters_to_compare);
                files = new ArrayList<>();
                files.add(fDades[i]);
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd");
                String dateInString = fDades[i].substring(0, 10);
                String place = fDades[i].substring(11, 14);
                String npid = fDades[i].substring(15, 17);
                String nped = fDades[i].substring(18, 19);
                String nppg = fDades[i].substring(20, 22);
                List<String> pages = new ArrayList<>();
                pages.add(nppg);
                int next=0;
                while(i+next+1<fDades.length 
                        && fDades[i+next+1].length()>quantity_of_characters_to_compare+2
                        && fDades[i+next+1].substring(0, quantity_of_characters_to_compare)
                                .equals(fDades[i].substring(0, quantity_of_characters_to_compare))){
                    next++;
                    files.add(fDades[i+next]);
                    nppg = fDades[i+next].substring(20, 22);
                    if(!pages.get(pages.size()-1).equals(nppg)){
                        pages.add(nppg);
                    }
                }
                i+=next;
                String[] pagesList = new String[pages.size()];
                pagesList = pages.toArray(pagesList);
                String text = readFileAndGetText(files);
                _createAndProcessEachInformationUnit(text, name, files, 
                        new PublicationInfo(modelVersion, dateInString, npid, nped, pagesList), 
                        (i+1)/(float)fDades.length,
                        (Callback<InformationUnitDataParamsForCallback, Void>) callbak);
            }
            i++;
        }
    } 
    
    

    private <T extends InformationUnitDataParamsForCallback> void _createAndProcessEachInformationUnit(
            String text, 
            String name,
            List<String> filesList, 
            PublicationInfo publicationInfo, 
            float completedRatio,
            Callback<T, Void> callbak){
        InformationUnitDataParamsFromFilesForCallback inf = new ImplInformationUnitDataParamsForCallback(publicationInfo, text, name, filesList, completedRatio);
        callbak.call((T) inf);
        
    }
    
    @Override
    public String getText(String text) {
        String bonText = __prepareTextFromFile(text);
        return bonText;
    }

    @Override
    public String getText(String[] text) {
        StringBuilder bonText = new StringBuilder();
        for(String t: text){
            appendText(bonText, __prepareTextFromFile(t));
        }
        return bonText.toString();
    }

    @Override
    public String getText(List<String> text) {
        StringBuilder bonText = new StringBuilder();
        for(String t: text){
            appendText(bonText, __prepareTextFromFile(t));
        }
        return bonText.toString();
    }
    
    @Override
    protected String prepareTextFromFile(String file, String originDir) {
        String rawText;
        String bonText;
        try{
            rawText = ReaderTools.textFileRawContent(originDir, file);
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
            Pattern pattern;
            RegexConfiguration regexConfiguration = especificConfigurator==null?null:(RegexConfiguration) especificConfigurator; 
            if(regexConfiguration!=null){
                File rb = new File(regexConfiguration.getRegexBasePath());
                if(rb.exists() && rb.isDirectory()){
                    pattern = RegexBuilder.getInstance(regexConfiguration).buildRegex(regex_name);                    
                }else{
                    pattern = patternIsPage;
                }
            }else{
                pattern = patternIsPage;
            }            
            rawText = rawText.replaceAll("\r\n", "\n").trim();   
            Matcher matcher = pattern.matcher(rawText);
            while (matcher.matches()) {
                rawText = matcher.group(1);
                matcher = pattern.matcher(rawText);
            }
            bonText = rawText;
//            if(false){
//                bonText = ReaderTools.singleLf2Space(rawText);
//            }
//            if(false){
//                bonText = ReaderTools.doubleLf2SingleLf(bonText);
//            }
        } catch (PatternSyntaxException ex) {
            throw new AutoNewsRuntimeException(String.format("The expression regular named '%s' is bad. Please, revise it", regex_name));
        }
        return bonText;
    }  
}
