package org.elsquatrecaps.autonewsextractor.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 * @author josep
 */
public class ReaderTools {
    
    public static String singleLf2Space(String toRead){
        return toRead.replaceAll("\r?\n(?!\r?\n)", " ");
    }
    
    public static String doubleLf2SingleLf(String toRead){
        return toRead.replaceAll("\r?\n\r?\n", "\n");
    }
    
    public static String textFileRawContent(String dir, String... name) throws IOException{
        File f;
        String ret = "";
        ret =  new String(Files.readAllBytes(Paths.get(dir, name)));
        
        return ret;
    }
    
    Pattern getFromPrePattern(String prePattern) throws IOException{
        Pattern pattern = null;
        List<String> orRegex;
        orRegex = Files.readAllLines(Paths.get("regex/".concat(prePattern).concat(".regex")));
        
        return pattern;
    }
    
}
