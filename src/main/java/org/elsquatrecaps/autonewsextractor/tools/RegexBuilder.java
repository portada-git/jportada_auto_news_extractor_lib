package org.elsquatrecaps.autonewsextractor.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.elsquatrecaps.autonewsextractor.error.AutoNewsRegexNotFoundRuntimeException;
import org.elsquatrecaps.autonewsextractor.tools.configuration.RegexConfiguration;
//import org.elsquatrecaps.autonewsextractor.AppArguments;

/**
 *
 * @author josep
 */
public class RegexBuilder {
    public static final String[] TYPES = {".regex", ".options"};
    public static final int REGEX_TYPE = 0;
    public static final int OPTIONS_TYPE = 1;
    String basePath;
    String searchPath;
    String variantRegex;

//    private RegexBuilder(String basePath) {
//        this(basePath, "");
//    }
//
//    private RegexBuilder(String basePath, String searchPath) {
//        this.basePath = basePath;
//        this.searchPath = searchPath;
//    }

    private RegexBuilder(String basePath, String searchPath, String variant) {
        this.basePath = basePath;
        this.searchPath = searchPath;
        this.variantRegex = variant;
    }

//    private RegexBuilder() {
//        this("regex", "");
//    }
    
    public static RegexBuilder getInstance(RegexConfiguration conf){
        return getInstance(conf, 0);
    }
    
    public static RegexBuilder getInstance(RegexConfiguration conf, int parserid){
        return new RegexBuilder(conf.getRegexBasePath(),
                conf.getFactModel()
                .concat("/").concat(conf.getNewspaper())
                .concat("/").concat(conf.getParseModel()[parserid]),
                conf.getOcrEngineModel()
        ); 
    }
    
//    public static RegexBuilder getInstance(String basePath) {
//        return new RegexBuilder(basePath);
//    }
    
    public static RegexBuilder getInstance(String basePath, String searchPath) {
        return new RegexBuilder(basePath, searchPath, null);
    }
    
    public static RegexBuilder getInstance(String basePath, String searchPath, String variant) {
        return new RegexBuilder(basePath, searchPath, variant);
    }
    
    public Pattern buildRegex(String cfgregex){
        Pattern ret=Pattern.compile(getStrPatternFromFile(cfgregex), getFlagsFromFile(cfgregex));                
        return ret;
    }
    
    public int getFlagsFromFile(String file){
        return getFlagsFromFile(getOptionsFileFromName(file, new File(basePath), searchPath, variantRegex));
//        return getFlagsFromPath(getOptionsPathFromName(file, basePath, searchPath, variantRegex));
    }
    
    public static int getFlagsFromFile(File file){
        return getFlagsFromUri(file.toURI());
    }
    
    public static int getFlagsFromUri(URI uri){
        return getFlagsFromPath(Paths.get(uri));
    }
    
    public static int getFlagsFromPath(Path path){
        int ret=0;
        try {
            List<String> flags;
            flags = Files.readAllLines(path);
            for(String flag: flags){
                for(int i=0; i<flag.length(); i++){
                    if(Flag.isTag(flag.charAt(i))){
                        ret |= Flag.getFlagFromTag(flag.charAt(i)).patternEq;
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(RegexBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    public String getStrPatternFromFile(String file){
        return getStrPatternFromFile(file, new File(basePath), searchPath, variantRegex);
    }
    
//    public static String getStrPatternFromFile(File file){
//        return getStrPatternFromFile(file, file.getParent(), 0);
//    }
    
    public static String getStrPatternFromFile(String[] filename){
        String ret;
        if(filename.length==3){
            ret = getStrPatternFromFile(filename[0], new File(filename[1]), filename[2], null);
        }else{
            ret = getStrPatternFromFile(filename[0], new File(filename[1]), filename[2], filename[3]);
        }
        return ret;
    }
    
    public static String getStrPatternFromFile(String filename, File basePath, String searchPath, String variant){
        Matcher matcher;
        String ret = "";
        StringBuilder contentBuilder = new StringBuilder();
//        StringBuffer replaced = new StringBuffer();
        try{
            File file = getRegexFileFromName(filename, basePath, searchPath, variant);
            try (BufferedReader br = new BufferedReader(new FileReader(file))){
                String currentLine;
                String prevLine=null;
                int lines = 0;
                while ((currentLine = br.readLine()) != null){
                    if(lines>1){
                        contentBuilder.append("|");
                    }
                    if(lines>0){
                        contentBuilder.append("(?:").append(prevLine).append(")");
                    }
                    ++lines;
                    prevLine = currentLine;
                }
                if(lines==1){
                     contentBuilder.append(prevLine);
                     ret = contentBuilder.toString();
                }else if(lines>1){
                    contentBuilder.append("|").append("(?:").append(prevLine).append(")");
                    ret = "(?:".concat(contentBuilder.toString()).concat(")");
                }            
                matcher = Pattern.compile("\\{##(\\w+)##\\}").matcher(ret);
                ret = RegexBuilder.replaceAll(matcher, (m) -> {      
                    return Matcher.quoteReplacement(getStrPatternFromFile(m.group(1), basePath, searchPath, variant));
                });
            } catch (FileNotFoundException ex) { 
                Logger.getLogger(RegexBuilder.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException | IllegalArgumentException ex) {
                Logger.getLogger(RegexBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }catch(AutoNewsRegexNotFoundRuntimeException ex){
            Logger.getLogger(RegexBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
    
    public static File getFileFromName(String name, int type, File basePath, String searchPath, String variant){
        File ret;
        File dir = new File(basePath, searchPath);
        String fileName = name.concat(TYPES[type]);
//        ret = new File(dir, fileName);
        ret = getVariantFileFromFiledir(dir, fileName, variant);
        while(!dir.equals(basePath) && !ret.exists()){
            dir = dir.getParentFile();
            ret = getVariantFileFromFiledir(dir, fileName, variant);
        }
        if(!ret.exists()){
            throw new AutoNewsRegexNotFoundRuntimeException();
        }
        return ret;        
    }
    
    private static File getVariantFileFromFiledir(File dir, String filename, String variant){
        File ret;
        if(variant==null || variant.isEmpty() || variant.isBlank()){
            ret = new File(dir, filename);
        }else{
            ret = new File(new File(dir, variant), filename);
            if(!ret.exists()){
                ret = new File(dir, filename);
            }
        }
        return ret;
    }

    public static Path getPathFromName(String name, int type, String basePath, String searchPath, String variant){
        Path ret;
        String fileName = name.concat(TYPES[type]);
        if(searchPath.isEmpty()){
//            ret = Paths.get(basePath, fileName);
            ret = getVariantFileFromPath(fileName, variant, basePath);
        }else{
//            ret = Paths.get(basePath, searchPath, fileName);
            ret = getVariantFileFromPath(fileName, variant, basePath, searchPath);
        }
        while (!ret.toString().equals(basePath) && !Files.exists(ret)){
//            ret = ret.subpath(0, ret.getNameCount()-2).resolve(fileName);       
//            Path parent = ret.subpath(0, ret.getNameCount()-2);
//            if(!ret.startsWith(ret)){
//                
//            }
            ret = getVariantFileFromPath(fileName, variant, "/".concat(ret.toAbsolutePath().subpath(0, ret.getNameCount()-2).toString()));    
        }        
        return ret;        
    }
    
    private static Path getVariantFileFromPath(String filename, String variant, String dir, String... dirs){
        Path ret;
        if(variant==null || variant.isEmpty() || variant.isBlank()){
            ret = Paths.get(Paths.get(dir,dirs).toString(), filename);
        }else{
            ret = Paths.get(Paths.get(dir, dirs).toString(), variant, filename);
            if(!Files.exists(ret)){
                ret = Paths.get(Paths.get(dir,dirs).toString(), filename);
            }
        }
        
        return ret;
    }

    public static Path getRegexPathFromName(String name, String basePath, String searchPath, String variant){
        return getPathFromName(name, REGEX_TYPE, basePath, searchPath, variant);
    }
    
    public static Path getRegexPathFromName(String name, String basePath, String variant){
        return getPathFromName(name, REGEX_TYPE, basePath, "", variant);
    }
    
    public static File getRegexFileFromName(String name, File basePath, String searchPath, String variant){
        return getFileFromName(name, REGEX_TYPE, basePath, searchPath, variant);      
    }
    
    public static File getRegexFileFromName(String name, File basePath, String variant){
        return getFileFromName(name, REGEX_TYPE, basePath, "", variant);      
    }

    public static Path getOptionsPathFromName(String name, String basePath, String variant){
        return getOptionsPathFromName(name, basePath, "", variant);
    }
    
    public static Path getOptionsPathFromName(String name, String basePath, String searchPath, String variant){
        return getPathFromName(name, OPTIONS_TYPE, basePath, searchPath, variant);
    }
    
    public static File getOptionsFileFromName(String name, File basePath, String searchPath, String variant){
        return getFileFromName(name, OPTIONS_TYPE, basePath, searchPath, variant);
    }
    
    public static File getOptionsFileFromName(String name, File basePath, String variant){
        return getFileFromName(name, OPTIONS_TYPE, basePath, "", variant);        
    }
    
    private static String replaceAll(Matcher matcher, 
                                                Function<Matcher, String> replacer){
        StringBuffer result = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(result, replacer.apply(matcher));
        }
        matcher.appendTail(result);
        return result.toString();
    }
    
    static private enum Flag{
        GLOBAL('g', 0),
        INSENSITIVE('i', Pattern.CASE_INSENSITIVE),
        MULTILINE('m', Pattern.MULTILINE),
        SINGLE_LINE('s', Pattern.DOTALL),
        EXTENDED('x', Pattern.COMMENTS),
        UNICODE_CASE('u', Pattern.UNICODE_CASE),
        UNICODE_MATCH('U', Pattern.UNICODE_CHARACTER_CLASS),
        UNIX_LINES('X', Pattern.UNIX_LINES);
        
        private static final Map<Character, Flag> BY_TAG = new HashMap<>();
        
        static {
            for (Flag e : values()) {
                BY_TAG.put(e.tag, e);
            }
        }

        private char tag;
        private int patternEq;
        
        private Flag(char charValue, int patternEq) {
            this.tag = charValue;
            this.patternEq = patternEq;
        }

        public char getCharValue() {
            return tag;
        }

        public int getPatternEq() {
            return patternEq;
        }
        
        public static Flag getFlagFromTag(char tag){
            return BY_TAG.get(tag);
        }    
        
        public static boolean isTag(char tag){
            return BY_TAG.containsKey(tag);
        }
    }    
}
