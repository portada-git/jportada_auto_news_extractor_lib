package org.elsquatrecaps.autonewsextractor.informationunitbuilder.reader;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author josepcanellas
 */
public abstract class AbstractReader implements FactReader{
    private String originDir;

    public String getOriginDir() {
        return originDir;
    }

    protected void setOriginDir(String originDir) {
        this.originDir = originDir;
    }
    
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

    public void appendText(StringBuilder bonText, String pt){
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
    
    public String file2Text(String file){
        String ret = this.prepareTextFromFile(file, getOriginDir());
        return ret;
    }    
    
    public String file2Text(String file, String originDir){
        String ret = this.prepareTextFromFile(file, originDir);
        return ret;
    }    
    
    abstract protected String prepareTextFromFile(String file, String originDir);
    
}
