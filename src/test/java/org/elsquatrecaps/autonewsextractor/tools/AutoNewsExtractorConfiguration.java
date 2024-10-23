package org.elsquatrecaps.autonewsextractor.tools;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.annotation.Arg;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.elsquatrecaps.autonewsextractor.tools.configuration.DataExtractConfiguration;
import org.elsquatrecaps.autonewsextractor.tools.configuration.InformationUnitBuilderrConfiguration;
import org.elsquatrecaps.autonewsextractor.tools.configuration.RegexConfiguration;
import org.elsquatrecaps.autonewsextractor.tools.configuration.TargetFragmentBreakerConfiguration;
import org.elsquatrecaps.utilities.tools.configuration.AbstractConfiguration;



/**
 *
 * @author josep
 */
public class AutoNewsExtractorConfiguration extends AbstractConfiguration implements TargetFragmentBreakerConfiguration, 
                                                                                    InformationUnitBuilderrConfiguration,
                                                                                    RegexConfiguration,
                                                                                    DataExtractConfiguration{
    @Arg                        //-r
    private String regexBasePath;
    @Arg(dest="fact_model")   //-f
    private String factModel;
    @Arg(dest="newspaper")     //-n
    private String newspaper;
    @Arg(dest="ocr_engine_model")  //-oe
    private String ocrEngineModel;
    @Arg(dest="parse_model")        //-p
    private String s_parseModel;
    private String[] parseModel;
    @Arg(dest="file_extension")    //-x
    private String fileExtension;
    @Arg(dest="origin_dir")  //-d
    private String originDir;
    @Arg(dest="output_file")  //-o
    private String outputFile;
    @Arg(dest="appendOutputFile")   //-a
    private String append_output_file;
    private boolean appendOutputFile;
    @Arg(dest="parser_config_json_file")   //-pc
    private String parserConfigJsonFile;
//    @Arg(dest="counter_file")  //-c
//    private String counterFile;
//    @Arg(dest="layoutStructureType")   //-s
//    private String layoutStructureType;
//    @Arg(dest="layoutColumnNumber")     //-cn
//    private String s_layoutColumnNumber;
//    private Integer[] layoutColumnNumber;
//    @Arg(dest="layoutColumnPositions")    //-cc
//    private String s_layoutColumnPositions;
//    private Integer[][][] layoutColumnPositions;
//    
    /**
     *
     * @param dest
     * @param val
     */
    @Override
    protected void setDefaultArg(String dest, Object val){
        if(!this.getAttrs().contains(dest)){
            switch(dest){
                case "parser_config_json_file":
                    this.setParserConfigJsonFile((String) val);
                    break;
                case "regexBasePath":
                    this.setRegexBasePath((String) val);
                    break;
                case "fact_model":
                    setFactModel((String) val);
                    break;                    
                case "newspaper":
                    setNewspaper((String) val);
                    break;                    
                case "parse_model":
                    setParseModel((String) val);
                    break;                    
                case "ocr_engine_model":
                    setOcrEngineModel((String) val);
                    break;                    
                case "origin_dir":
                    originDir = (String) val;
                    this.getAttrs().add(dest);
                    break;
                case "output_file":
                    outputFile = (String) val;
                    this.getAttrs().add(dest);
                    break;
                case "appendOutputFile":
                    appendOutputFile = getBoolean((String) val);
                    this.getAttrs().add(dest);
                    break;                    
//                case "counter_file":
//                    counterFile = (String) val;
//                    this.getAttrs().add(dest);
//                    break;                    
                case "file_extension":
                    setFileExtension((String) val);
//                    this.getAttrs().add(dest);
                    break;                    
//                case "layoutStructureType":
//                    setLayoutStructureType((String) val);
//                    break;                    
//                case "layoutColumnNumber":
//                    setLayoutColumnNumber((String) val);
//                    break;                    
//                case "layoutColumnPositions":
//                    setLayoutColumnPositions((String) val);
//                    break;                    
            }
        }
    }
    
    @Override
    public<T extends Object> T getAttr(String key){
        T ret = null;
        if(this.getAttrs().contains(key)){
            switch(key){
                case "parser_config_json_file":
                    ret = (T) this.getParserConfigJsonFile();
                    break;
                case "regexBasePath":
                    ret = (T) this.getRegexBasePath();
                    break;
                case "fact_model":
                    ret = (T) getFactModel();
                    break;                    
                case "newspaper":
                    ret = (T) getNewspaper();
                    break;                    
                case "parse_model":
                    ret = (T) getParseModel();
                    break;                    
                case "ocr_engine_model":
                    ret = (T) getOcrEngineModel();
                    break;                    
                case "origin_dir":
                    ret = (T) getOriginDir();
                    break;
                case "output_file":
                    ret = (T) getOutputFile();
                    break;
                case "appendOutputFile":
                    ret = (T) Boolean.valueOf(appendOutputFile);
                    break;                    
//                case "counter_file":
//                    counterFile = (String) val;
//                    this.getAttrs().add(dest);
//                    break;                    
                case "file_extension":
                    ret = (T) getFileExtension();
//                    this.getAttrs().add(dest);
                    break;                    
//                case "layoutStructureType":
//                    setLayoutStructureType((String) val);
//                    break;                    
//                case "layoutColumnNumber":
//                    setLayoutColumnNumber((String) val);
//                    break;                    
//                case "layoutColumnPositions":
//                    setLayoutColumnPositions((String) val);
//                    break;                    
            }
        }
        return ret;
    }
    
    @Override
    public void configure(){
        super.configure("appExtractor.properties");
    }
    
    @Override
    public void parseArguments(String[] args){
        ArgumentParser parser = ArgumentParsers.newFor("AppReader").build()
                .defaultHelp(true)
                .description("Extreu informació a partir de notícies relacionades amb les embarcacions entrades al port de Barcelona, usant coma a font el Diari de Barcelona");
        parser.addArgument("-d", "--origin_dir").nargs("?").help("Directori d'on llegir els fitxers OCR amb les noticies");
        parser.addArgument("-o", "--output_file").nargs("?").help("Camí al fitxer de sortida. Per exemple: -o c:/directori/non_fitxer");
        parser.addArgument("-a", "--appendOutputFile").nargs("?").help("Indica si es vol afegir els vaixells extrets al final del fitxer de sortida o es crea un nou fitxer a cada extracció. Nomes accepta els valors 'si' o el valor 'no'");
//        parser.addArgument("-c", "--counter_file").nargs("?").help("Camí al fitxer comptador de vaixells. Per exemple: -c c:/directori/non_fitxer");
        parser.addArgument("-x", "--file_extension").nargs("?").help("Indica quina extyensió han de tenir els fitxers a llegir");
//        parser.addArgument("-s", "--layoutStructureType").choices("columnPositions", "columnNumber", "free").nargs("?").help("Indica el tipus de decripció de l'estructura de la maquetació. Són valors vàlids: 'columnNumber', 'columnPositions' o 'free'");
//        parser.addArgument("-cn", "--layoutColumnNumber").nargs("?").help("Indica quantes columnes i com es troben agrupades la publicació.");
//        parser.addArgument("-cc", "--layoutColumnPositions").nargs("?").help("Indica la posició de les columnes sobre l'eix de les abscises i com es troben agrupades.");
        parser.addArgument("-r", "--regexBasePath").nargs("?").help("Directori on es troben especificades les expressions regulars de l'anàlisi");
        parser.addArgument("-f", "--fact_model").nargs("?").help("Indica quin tipus de fet s'ha de llegir");
        parser.addArgument("-n", "--newspaper").nargs("?").help("Indica quin model de nitícies cal tractar");
        parser.addArgument("-oe", "--ocr_engine_model").nargs("?").help("Indica quins models d'expressions regulars cal aplicar");
        parser.addArgument("-p", "--parse_model").nargs("?").help("Indica quins models d'analitzador (parser) cal usar");
        parser.addArgument("-pc", "--parser_config_json_file").nargs("?").help("Indica quin es el fitxer de configuració del parser");
        try {
            parser.parseArgs(args, this);
            this.appendOutputFile = getBoolean(this.append_output_file);
//            this.layoutColumnNumber = getIntArray(this.s_layoutColumnNumber);
//            this.layoutColumnPositions = getInt3dArray(this.s_layoutColumnPositions);
            this.parseModel = getStringArray(this.s_parseModel);
            this.updateAttrs();
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
    }
    
    
    @Override
    protected void updateAttrs(){
        if(this.getParserConfigJsonFile()!=null){
            this.getAttrs().add("parser_config_json_file");
        }
        if(this.getFactModel()!=null){
            this.getAttrs().add("fact_model");
        }
        if(this.getNewspaper()!=null){
            this.getAttrs().add("newspaper");
        }
        if(this.getOcrEngineModel()!=null){
            this.getAttrs().add("ocr_engine_model");
        }
        if(this.getRegexBasePath()!=null){
            this.getAttrs().add("regexBasePath");
        }
        if(this.s_parseModel!=null){
            this.getAttrs().add("parse_model");
        }        
        if(this.append_output_file!=null){
            this.getAttrs().add("appendOutputFile");
        }
//        if(this.layoutStructureType!=null){
//            this.getAttrs().add("layoutStructureType");
//        }
//        if(this.s_layoutColumnNumber!=null){
//            this.setLayoutStructureType("columnNumber");
//            this.getAttrs().add("layoutColumnNumber");
//        }
//        if(this.s_layoutColumnPositions!=null){
//            this.setLayoutStructureType("columnPositions");
//            this.getAttrs().add("layoutColumnPositions");
//        }
//        if(this.counterFile!=null){
//            this.getAttrs().add("counter_file");
//        }
        if(this.originDir!=null){
            this.getAttrs().add("origin_dir");
        }
        if(this.outputFile!=null){
            this.getAttrs().add("output_file");
        }
        if(this.getFileExtension()!=null){
            this.getAttrs().add("file_extension");
        }
    }
    

    @Override
    public String getOriginDir() {
        return originDir;
    }

    @Override
    public void setOriginDir(String originDir) {
        this.originDir = originDir;
        this.getAttrs().add("origin_dir");
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
        this.getAttrs().add("output_file");
    }


    public void setAppend_output_file(String append_output_file) {
        this.append_output_file = append_output_file;
        this.getAttrs().add("appendOutputFile");
    }

    public boolean isAppendOutputFile() {
        return appendOutputFile;
    }

//    public String getCounterFile() {
//        return counterFile;
//    }
//
//    public void setCounterFile(String counterFile) {
//        this.counterFile = counterFile;
//        this.getAttrs().add("counter_file");
//    }


    /**
     * @return the fileExtension
     */
    @Override
    public String getFileExtension() {
        return fileExtension;
    }

    /**
     * @param fileExtension the fileExtension to set
     */
    @Override
    public void setFileExtension(String fileExtension) {
        this.fileExtension = fileExtension;
        this.getAttrs().add("file_extension");
    }

//    public String getLayoutStructureType() {
//        return layoutStructureType;
//    }
//
//    public void setLayoutStructureType(String layoutStructureType) {
//        if(!layoutStructureType.matches("columnNumber|free")){
//            layoutStructureType="free";
//        }
//        if(layoutStructureType.matches("free")){
//            this.layoutColumnNumber=null;            
//            this.getAttrs().remove("columnNumber");
//        }
//        this.layoutStructureType = layoutStructureType;
//        this.getAttrs().add("layoutStructureType");
//    }
//
//    public Integer[] getLayoutColumnNumber() {
//        return layoutColumnNumber;
//    }
//
//    public void setLayoutColumnNumber(String layoutColumnNumber) {
//        setLayoutColumnNumber(getIntArray(layoutColumnNumber));
//    }
//    
//    public void setLayoutColumnNumber(Integer[] layoutColumnNumber) {
//        this.layoutColumnNumber = layoutColumnNumber;
//        this.setLayoutStructureType("columnNumber");
//        this.getAttrs().add("layoutColumnNumber");
//    }
//    public Integer[][][] getLayoutColumnPositions() {
//        return layoutColumnPositions;
//    }
//
//    public void setLayoutColumnPositions(String layoutColumnPositions) {
//        setLayoutColumnPositions(getInt3dArray(layoutColumnPositions));
//    }
//    
//    public void setLayoutColumnPositions(Integer[][][] layoutColumnPositions) {
//        this.layoutColumnPositions = layoutColumnPositions;
//        this.setLayoutStructureType("columnPositions");
//        this.getAttrs().add("layoutColumnPositions");
//    }
    
    @Override
    public String getRegexBasePath() {
        return regexBasePath;
    }

    @Override
    public void setRegexBasePath(String regexBasePath) {
        this.regexBasePath = regexBasePath;
        this.getAttrs().add("regexBasePath");
    }

    @Override
    public String getFactModel() {
        return factModel;
    }

    @Override
    public void setFactModel(String val) {
        this.factModel = val;
        this.getAttrs().add("fact_model");
    }

    @Override
    public String getNewspaper() {
        return newspaper;
    }

    @Override
    public void setNewspaper(String val) {
        this.newspaper = val;
        this.getAttrs().add("newspaper");
    }


    @Override
    public String getOcrEngineModel() {
        return ocrEngineModel;
    }

    @Override
    public void setOcrEngineModel(String val) {
        this.ocrEngineModel = val;
        this.getAttrs().add("ocr_engine_model");
    }
    
    @Override
    public String[] getParseModel() {
        return parseModel;
    }


    @Override
    public void setParseModel(String[] parse_model) {
        this.parseModel = parse_model;
        this.getAttrs().add("parse_model");
    }
    
    public void setParseModel(String parse_model) {
        this.s_parseModel = parse_model;
        setParseModel(getStringArray(parse_model));
    }    

    @Override
    public String[] getTargetfragmentBreakerProxyPackagesToSearch() {
        return new String[] {"org.elsquatrecaps.autonewsextractor.targetfragmentbreaker.reader"};
    }

    @Override
    public void setTargetfragmentBreakerProxyPackagesToSearch(String[] packages) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getExtractorApproach() {
        return null;
    }


    @Override
    public String getFragmentBreakerApproach() {
        return null;
    }

    @Override
    public void setFragmentBreakerApproach(String originDir) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String[] getInformationUnitBuilderProxyPackagesToSearch() {
        return "org.elsquatrecaps.autonewsextractor.informationunitbuilder.runnable".split(",");
    }

    @Override
    public void setInformationUnitBuilderProxyPackagesToSearch(String[] packages) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String[] getDataExtractProxyPackagesToSearch() {
        return new String[] {"org.elsquatrecaps.autonewsextractor.dataextractor.parser"};
    }

    @Override
    public void setDataExtractProxyPackagesToSearch(String[] packages) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getJsonConfigFile() {
        return null;
    }

    @Override
    public void setJsonConfigFile(String file) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String[] getDataExtractCalculatorBuilderPackagesToSearch() {
        return new String[] {"org.elsquatrecaps.autonewsextractor.dataextractor.calculators"};
    }

    @Override
    public void setDataExtractCalculatorBuilderPackagesToSearch(String[] packages) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    /**
     * @return the parserConfigJsonFile
     */
    public String getParserConfigJsonFile() {
        return parserConfigJsonFile;
    }

    /**
     * @param parserConfigJsonFile the parserConfigJsonFile to set
     */
    public void setParserConfigJsonFile(String parserConfigJsonFile) {
        this.parserConfigJsonFile = parserConfigJsonFile;
        this.getAttrs().add("parser_config_json_file");        
    }
    
}
