package org.elsquatrecaps.autonewsextractor.tools.configuration;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.annotation.Arg;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import org.elsquatrecaps.utilities.tools.configuration.AbstractConfiguration;
import org.elsquatrecaps.utilities.tools.configuration.DevelopmentConfiguration;



/**
 *
 * @author josep
 */
public class AutoNewsExtractorConfiguration extends AbstractConfiguration implements TargetFragmentBreakerConfiguration, 
                                                                                    InformationUnitBuilderrConfiguration,
                                                                                    RegexConfiguration,
                                                                                    DataExtractConfiguration,
                                                                                    DevelopmentConfiguration{
    @Arg(dest="run_for_debugging") //-rd
    private String strRunForDebugging;
    private boolean runForDebugging;
    @Arg(dest="init_config_file") //-c
    private String strinitConfigFile;
    @Arg(dest="target_fragment_breaker_proxy_packages_to_search") //-tfb_pck
    private String strTargetFragmentBreakerProxyPackagesToSearch;
    private String[] targetFragmentBreakerProxyPackagesToSearch;
    @Arg(dest="information_unit_builder_proxy_packages_to_search") //-iub_pck
    private String strInformationUnitBuilderProxyPackagesToSearch;
    private String[] informationUnitBuilderProxyPackagesToSearch;
    @Arg(dest="data_extract_proxy_packages_to_search") //-dex_pck
    private String strDataExtractProxyPackagesToSearch;
    private String[] dataExtractProxyPackagesToSearch;
    @Arg(dest="data_extract_calculator_builder_packages_to_search") //-decb_pck
    private String strDataExtractCalculatorBuilderProxyPackagesToSearch;
    private String[] dataExtractCalculatorBuilderProxyPackagesToSearch;
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
    @Arg(dest="parser_config_json_file")   //-pcf
    private String parserConfigJsonFile;
    @Arg(dest="fragment_breaker_approach")   //-fbapp
    private String fragmentBreakerApproach;
    @Arg(dest="extractor_approach")   //-exapp
    private String extractorApproach;
    @Arg(dest="quantity_of_characters_to_compare")   //-comp
    private String strQuantityOfCharactersToCompare;
    private int quantityOfCharactersToCompare;

    /**
     *
     * @param dest
     * @param val
     * @return 
     */
    @Override
    protected boolean setDefaultArg(String dest, Object val){
        boolean ret=true;
        if(!this.getAttrs().contains(dest)){
            switch(dest){
                case "run_for_debugging":
                    this.setRunForDebugging((String) val);
                    break;
                case "init_config_file":
                    this.setInitConfigFile((String) val);
                    break;
                case "target_fragment_breaker_proxy_packages_to_search":
                    this.setTargetfragmentBreakerProxyPackagesToSearch((String) val);
                    break;
                case "information_unit_builder_proxy_packages_to_search":
                    this.setInformationUnitBuilderProxyPackagesToSearch((String) val);
                    break;
                case "data_extract_proxy_packages_to_search":
                    this.setDataExtractProxyPackagesToSearch((String) val);
                    break;
                case "data_extract_calculator_builder_packages_to_search":
                    this.setDataExtractCalculatorBuilderPackagesToSearch((String) val);
                    break;
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
                case "file_extension":
                    setFileExtension((String) val);
                    break;                    
                case "fragment_breaker_approach":
                    setFragmentBreakerApproach((String) val);
                    break;                    
                case "extractor_approach":
                    setExtractorApproach((String) val);
                    break;  
                case "quantity_of_characters_to_compare":
                    setQuantityOfCharactersToCompare((String) val);
                    break;  
                default:
                    ret=false;
            }
        }
        return ret;
    }
    
    @Override
    public<T extends Object> T getAttr(String key){
        T ret = null;
        if(this.getAttrs().contains(key)){
            switch(key){
                case "run_for_debugging":
                    ret = (T) this.getRunForDebugging();
                    break;
                case "init_config_file":
                    ret = (T) this.getInitConfigFile();
                    break;
                case "data_extract_calculator_builder_packages_to_search":
                    ret = (T) this.getDataExtractCalculatorBuilderPackagesToSearch();
                    break;
                case "data_extract_proxy_packages_to_search":
                    ret = (T) this.getDataExtractProxyPackagesToSearch();
                    break;
                case "information_unit_builder_proxy_packages_to_search":
                    ret = (T) this.getInformationUnitBuilderProxyPackagesToSearch();
                    break;
                case "target_fragment_breaker_proxy_packages_to_search":
                    ret = (T) this.getTargetfragmentBreakerProxyPackagesToSearch();
                    break;
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
                    Boolean b = appendOutputFile;
                    ret = (T) b;
                    break;                    
                case "file_extension":
                    ret = (T) getFileExtension();
                    break;                    
                case "fragment_breaker_approach":
                    ret = (T) getFragmentBreakerApproach();
                    break;                    
                case "extractor_approach":
                    ret = (T) getExtractorApproach();
                    break;                    
                case "quantity_of_characters_to_compare":
                    ret = (T) getQuantityOfCharactersToCompare();
                    break;                    
            }
        }
        return ret;
    }
    
    @Override
    public void parseArguments(String[] args){
        ArgumentParser parser = ArgumentParsers.newFor("AppReader").build()
                .defaultHelp(true)
                .description("Extreu informació a partir de notícies relacionades amb les embarcacions entrades al port de Barcelona, usant coma a font el Diari de Barcelona");
        parser.addArgument("-c", "--init_config_file").nargs("?").setDefault("config").help("Camí on es troba el fitxer de configuració");
        parser.addArgument("-d", "--origin_dir").nargs("?").help("Directori d'on llegir els fitxers OCR amb les noticies");
        parser.addArgument("-o", "--output_file").nargs("?").help("Camí al fitxer de sortida. Per exemple: -o c:/directori/non_fitxer");
        parser.addArgument("-a", "--appendOutputFile").nargs("?").help("Indica si es vol afegir els vaixells extrets al final del fitxer de sortida o es crea un nou fitxer a cada extracció. Nomes accepta els valors 'si' o el valor 'no'");
        parser.addArgument("-x", "--file_extension").nargs("?").help("Indica quina extyensió han de tenir els fitxers a llegir");
        parser.addArgument("-r", "--regexBasePath").nargs("?").help("Directori on es troben especificades les expressions regulars de l'anàlisi");
        parser.addArgument("-f", "--fact_model").nargs("?").help("Indica quin tipus de fet s'ha de llegir");
        parser.addArgument("-n", "--newspaper").nargs("?").help("Indica quin model de nitícies cal tractar");
        parser.addArgument("-oe", "--ocr_engine_model").nargs("?").help("Indica quins models d'expressions regulars cal aplicar");
        parser.addArgument("-p", "--parse_model").nargs("?").help("Indica quins models d'analitzador (parser) cal usar");
        parser.addArgument("-pcf", "--parser_config_json_file").nargs("?").help("Indica quin es el fitxer de configuració del parser");
        parser.addArgument("-tfb_pck", "--target_fragment_breaker_proxy_packages_to_search").nargs("?").help("Indica quin paquets de cerca pel proxy");
        parser.addArgument("-iub_pck", "--information_unit_builder_proxy_packages_to_search").nargs("?").help("Indica quin paquets de cerca pel proxy");
        parser.addArgument("-dex_pck", "--data_extract_proxy_packages_to_search").nargs("?").help("Indica quin paquets de cerca pel proxy");
        parser.addArgument("-decb_pck", "--data_extract_calculator_builder_packages_to_search").nargs("?").help("Indica quin paquets de cerca pel proxy");
        parser.addArgument("-fbapp", "--fragment_breaker_approach").nargs("?").help("Indica quin enfocament metodològic s'usa per separar els fragments útils");
        parser.addArgument("-exapp", "--extractor_approach").nargs("?").help("Indica quin enfocament metodològic s'usa per a fer l'extracció");
        parser.addArgument("-rd", "--run_for_debugging").nargs("?").help("Indica si cal executar el procès en mode depuració o en mode normal. Els valors: [s]i, [y]es, [c]ert, [t]rue, [v]ertader es prenen com a valors certs, qualsevol altre valors es considerarà fals.");
        parser.addArgument("-comp", "--quantity_of_characters_to_compare").nargs("?").help("Indica la quantitat de caracters a comparar si la metodologia de ensamblatge és joinerType = \"file_name\" y metadataSource = \"portada_file_name\".");
        try {
            parser.parseArgs(args, this);
            this.setInitConfigFile(getFile(this.strinitConfigFile));
            this.appendOutputFile = getBoolean(this.append_output_file);
            this.parseModel = getStringArray(this.s_parseModel);
            this.targetFragmentBreakerProxyPackagesToSearch = getStringArray(this.strTargetFragmentBreakerProxyPackagesToSearch);
            this.informationUnitBuilderProxyPackagesToSearch = getStringArray(this.strInformationUnitBuilderProxyPackagesToSearch);
            this.dataExtractProxyPackagesToSearch = getStringArray(this.strDataExtractProxyPackagesToSearch);
            this.dataExtractCalculatorBuilderProxyPackagesToSearch = getStringArray(this.strDataExtractCalculatorBuilderProxyPackagesToSearch);
            this.runForDebugging = getBoolean(this.strRunForDebugging);
            if(this.strQuantityOfCharactersToCompare!=null){
                this.quantityOfCharactersToCompare = Integer.parseInt(this.strQuantityOfCharactersToCompare);
            }
            this.updateAttrs();
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.exit(1);
        }
    }
    
    
    @Override
    protected void updateAttrs(){
        if(this.strQuantityOfCharactersToCompare!=null){
            this.getAttrs().add("quantity_of_characters_to_compare");
        }        
        if(this.strRunForDebugging!=null){
            this.strRunForDebugging = strRunForDebugging.trim();
            this.getAttrs().add("run_for_debugging");
        }
        if(this.strinitConfigFile!=null){
            this.strinitConfigFile = strinitConfigFile.trim();
            this.getAttrs().add("init_config_file");
        }
        if(this.getParserConfigJsonFile()!=null){
            this.setParserConfigJsonFile(this.getParserConfigJsonFile().trim());
        }
        if(this.getFactModel()!=null){
            this.setFactModel(this.getFactModel().trim());
        }
        if(this.getNewspaper()!=null){
            this.setNewspaper(this.getNewspaper().trim());
        }
        if(this.getOcrEngineModel()!=null){
            this.setOcrEngineModel(this.getOcrEngineModel().trim());
        }
        if(this.getRegexBasePath()!=null){
            this.setRegexBasePath(this.getRegexBasePath().trim());
        }
        if(this.s_parseModel!=null){
            this.s_parseModel = s_parseModel.trim();            
            this.getAttrs().add("parse_model");
        }        
        if(this.append_output_file!=null){
            this.append_output_file = append_output_file.trim();            
            this.getAttrs().add("appendOutputFile");
        }
        if(this.originDir!=null){
            this.originDir = originDir.trim();            
            this.getAttrs().add("origin_dir");
        }
        if(this.outputFile!=null){
            this.outputFile = outputFile.trim();            
            this.getAttrs().add("output_file");
        }
        if(this.getFileExtension()!=null){
            this.setFileExtension(this.getFileExtension().trim());
            this.getAttrs().add("file_extension");
        }
        if(this.strTargetFragmentBreakerProxyPackagesToSearch!=null){
            this.getAttrs().add("target_fragment_breaker_proxy_packages_to_search");
        }
        if(this.strInformationUnitBuilderProxyPackagesToSearch!=null){
            this.getAttrs().add("information_unit_builder_proxy_packages_to_search");
        }
        if(this.strDataExtractProxyPackagesToSearch!=null){
            this.getAttrs().add("data_extract_proxy_packages_to_search");
        }
        if(this.strDataExtractCalculatorBuilderProxyPackagesToSearch!=null){
            this.getAttrs().add("data_extract_calculator_builder_packages_to_search");
        }
        if(this.getFragmentBreakerApproach()!=null){
            this.getAttrs().add("fragment_breaker_approach");
        }
        if(this.getExtractorApproach()!=null){
            this.getAttrs().add("extractor_approach");
        }
    }
    

    @Override
    public String getOriginDir() {
        return originDir;
    }

    @Override
    public void setOriginDir(String originDir) {
        this.originDir = originDir.trim();
        this.getAttrs().add("origin_dir");
    }

    public String getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile.trim();
        this.getAttrs().add("output_file");
    }


    public void setAppend_output_file(String append_output_file) {
        this.append_output_file = append_output_file.trim();
        this.getAttrs().add("appendOutputFile");
    }

    public boolean isAppendOutputFile() {
        return appendOutputFile;
    }

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
        this.fileExtension = fileExtension.trim();
        this.getAttrs().add("file_extension");
    }
    
    @Override
    public String getRegexBasePath() {
        return regexBasePath;
    }

    @Override
    public void setRegexBasePath(String regexBasePath) {
        this.regexBasePath = regexBasePath.trim();
        this.getAttrs().add("regexBasePath");
    }

    @Override
    public String getFactModel() {
        return factModel;
    }

    @Override
    public void setFactModel(String val) {
        this.factModel = val.trim();
        this.getAttrs().add("fact_model");
    }

    @Override
    public String getNewspaper() {
        return newspaper;
    }

    @Override
    public void setNewspaper(String val) {
        this.newspaper = val.trim();
        this.getAttrs().add("newspaper");
    }


    @Override
    public String getOcrEngineModel() {
        return ocrEngineModel;
    }

    @Override
    public void setOcrEngineModel(String val) {
        this.ocrEngineModel = val.trim();
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
        this.s_parseModel = parse_model.trim();
        setParseModel(getStringArray(parse_model));
    }    

    @Override
    public String[] getTargetfragmentBreakerProxyPackagesToSearch() {
        return this.targetFragmentBreakerProxyPackagesToSearch;
    }

    public void setTargetfragmentBreakerProxyPackagesToSearch(String packages) {
        this.strTargetFragmentBreakerProxyPackagesToSearch=packages.trim();
        setTargetfragmentBreakerProxyPackagesToSearch(getStringArray(packages));
    }

    @Override
    public void setTargetfragmentBreakerProxyPackagesToSearch(String[] packages) {
        this.targetFragmentBreakerProxyPackagesToSearch = packages;
        this.getAttr("target_fragment_breaker_proxy_packages_to_search");
    }

    @Override
    public String getExtractorApproach() {
        return this.extractorApproach;
    }

    public void setExtractorApproach(String a) {
        this.extractorApproach=a.trim();
        this.getAttrs().add("extractor_approach");
    }


    @Override
    public String getFragmentBreakerApproach() {
        return this.fragmentBreakerApproach;
    }

    @Override
    public void setFragmentBreakerApproach(String approach) {
        this.fragmentBreakerApproach = approach.trim();
        this.getAttrs().add("fragment_breaker_approach");
    }

    @Override
    public String[] getInformationUnitBuilderProxyPackagesToSearch() {
        return this.informationUnitBuilderProxyPackagesToSearch;
    }

    @Override
    public void setInformationUnitBuilderProxyPackagesToSearch(String[] packages) {
        this.informationUnitBuilderProxyPackagesToSearch = packages;
        this.getAttr("information_unit_builder_proxy_packages_to_search");
    }

    public void setInformationUnitBuilderProxyPackagesToSearch(String packages) {
        this.strInformationUnitBuilderProxyPackagesToSearch = packages.trim();
        setInformationUnitBuilderProxyPackagesToSearch(getStringArray(packages));
    }

    @Override
    public String[] getDataExtractProxyPackagesToSearch() {
        return this.dataExtractProxyPackagesToSearch;
    }

    @Override
    public void setDataExtractProxyPackagesToSearch(String[] packages) {
        this.dataExtractProxyPackagesToSearch= packages;
        this.getAttrs().add("data_extract_proxy_packages_to_search");
    }

    public void setDataExtractProxyPackagesToSearch(String packages) {
        this.strDataExtractProxyPackagesToSearch = packages.trim();
        this.setDataExtractProxyPackagesToSearch(getStringArray(packages));
    }

    @Override
    public String[] getDataExtractCalculatorBuilderPackagesToSearch() {
        return this.dataExtractCalculatorBuilderProxyPackagesToSearch;
    }

    @Override
    public void setDataExtractCalculatorBuilderPackagesToSearch(String[] packages) {
        this.dataExtractCalculatorBuilderProxyPackagesToSearch=packages;
        this.getAttrs().add("data_extract_calculator_builder_packages_to_search");
    }

    public void setDataExtractCalculatorBuilderPackagesToSearch(String packages) {
        this.strDataExtractCalculatorBuilderProxyPackagesToSearch=packages.trim();
        this.setDataExtractCalculatorBuilderPackagesToSearch(getStringArray(packages));
    }

    /**
     * @return the parserConfigJsonFile
     */
    @Override
    public String getParserConfigJsonFile() {
        return parserConfigJsonFile;
    }

    /**
     * @param parserConfigJsonFile the parserConfigJsonFile to set
     */
    @Override
    public void setParserConfigJsonFile(String parserConfigJsonFile) {
        this.parserConfigJsonFile = parserConfigJsonFile.trim();
        this.getAttrs().add("parser_config_json_file");        
    }
    
    protected void setInitConfigFile(String iniConfigFile) {
        this.strinitConfigFile = iniConfigFile.trim();   
        this.setInitConfigFile(getFile(iniConfigFile));
        this.getAttrs().add("init_config_file");        
    }    

    @Override
    public Boolean getRunForDebugging() {
        return runForDebugging;
    }

    private void setRunForDebugging(String string) {
        setRunForDebugging(getBoolean(string.trim()));
    }
    
    private void setRunForDebugging(Boolean v) {
        this.runForDebugging=v;
        this.getAttrs().add("run_for_debugging");        

    }
    
    public Integer getQuantityOfCharactersToCompare() {
        return quantityOfCharactersToCompare;
    }

    private void setQuantityOfCharactersToCompare(String string) {
        if(string!=null){
            setQuantityOfCharactersToCompare(Integer.getInteger(string));
        }
    }
    
    private void setQuantityOfCharactersToCompare(Integer v) {
        this.quantityOfCharactersToCompare=v;
        this.getAttrs().add("quantity_of_characters_to_compare");        

    }
    
}
