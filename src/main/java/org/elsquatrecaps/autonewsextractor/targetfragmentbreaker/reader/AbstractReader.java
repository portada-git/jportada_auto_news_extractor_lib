package org.elsquatrecaps.autonewsextractor.targetfragmentbreaker.reader;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author josepcanellas
 */
public abstract class AbstractReader implements FactReader{
    
    @Override
    public String readFileAndGetText(String file) {
        String bonText = file2Text(file);
        return bonText;
    }

    @Override
    public String readFileAndGetText(String[] files) {
        StringBuilder bonText = new StringBuilder();
        for(String f: files){
            appendText(bonText, file2Text(f));
        }
        return bonText.toString();
    }

    @Override
    public String readFileAndGetText(List<String> files) {
        StringBuilder bonText = new StringBuilder();
        for(String f: files){
            appendText(bonText, file2Text(f));
        }
        return bonText.toString();
    }

    
//    @Override
//    public void readFileAndSaveData(String file, Date date) {
//        String bonText = readFileAndGetText(file);
//        this.saveDataFromText(bonText, date);   
//    }
//
//    @Override
//    public void readFileAndSaveData(String file) {
//        String bonText = readFileAndGetText(file);
//        this.saveDataFromText(bonText, new  Date());    
//    }
//
//    @Override
//    public void readFileAndSaveData(String[] files) {
//        String bonText = readFileAndGetText(files);
//        this.saveDataFromText(bonText, new Date());    
//    }
//
//    @Override
//    public void readFileAndSaveData(String[] files, Date date) {
//        String bonText = readFileAndGetText(files);
//        this.saveDataFromText(bonText, date);    
//    }
//
//    @Override
//    public void readFileAndSaveData(List<String> files) {
//        String bonText = readFileAndGetText(files);
//        this.saveDataFromText(bonText, new Date());    
//    }
//
//    @Override
//    public void readFileAndSaveData(List<String> files, Date date) {
//        String bonText = readFileAndGetText(files);
//        this.saveDataFromText(bonText, date);    
//    }
    
    protected void appendText(StringBuilder bonText, String pt){
        Pattern pattern = Pattern.compile("^.*\\.\\s*$", Pattern.DOTALL);
        Matcher matcherBt = pattern.matcher(bonText.toString());
        pattern = Pattern.compile("^\\s*[A-ZÁÀÉÈÍÌÓÒÚÙÄËÏÖÜÑÇ](.*)$", Pattern.DOTALL);
        Matcher matcherPt = pattern.matcher(pt);
        if(bonText.toString().length()>0){ 
            if(matcherBt.matches()
                    ||  matcherPt.matches()){
                bonText.append("\n");
            }else if(!bonText.toString().matches("^.+[-¬]\\s*$")){
                bonText.append(" ");
            }
        }
        bonText.append(pt);
    }
    
    
//    protected abstract void saveDataFromText(String bonText, Date date);
    
    protected String file2Text(String file){
        String ret = this.prepareTextFromFile(file);
        return ret;
    }    
    
    abstract protected String prepareTextFromFile(String file);
    
}
