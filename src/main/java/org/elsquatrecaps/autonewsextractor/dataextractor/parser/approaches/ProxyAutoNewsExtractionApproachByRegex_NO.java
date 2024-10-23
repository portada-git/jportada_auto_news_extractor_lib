package org.elsquatrecaps.autonewsextractor.dataextractor.parser.approaches;


/**
 *
 * @author josepcanellas
 */
@SuppressWarnings("unchecked")
public class ProxyAutoNewsExtractionApproachByRegex_NO/*<T extends Fact> implements ExtractorParser<T>*/{
//    static {
//        classExtractorMap = new HashMap<>();  
//        classFormatterMap = new HashMap<>();
//        Reflections ref = new Reflections("org.elsquatrecaps.autonewsextractor.boatfacts"
//                ,"org.elsquatrecaps.autonewsextractor.boatfacts.*" );
//        Set<Class<?>> annotated = ref.getTypesAnnotatedWith(ParserMarkerAnnotation.class);
//        annotated.forEach(new Consumer<Class<?>>() {
//            @Override
//            public void accept(Class<?> clazz) {
//                ParserMarkerAnnotation annotation = clazz.getAnnotation(ParserMarkerAnnotation.class);
//                classExtractorMap.put(annotation.id(), (Class<ExtractorParser>) clazz);
//                classFormatterMap.put(annotation.id(), annotation.csvFormater());
//            }
//        });
//    }   
//    
//    private static HashMap<String, Class<? extends FileFormatter>> classFormatterMap;
//    private static HashMap<String, Class<ExtractorParser>> classExtractorMap;
//    private ExtractorParser<T> parser;
//    private String parserType;
//    private String country;
//    
//    public ProxyAutoNewsExtractionApproachByRegex_NO(String parseType, String regexBPath, String regexSPath, String country, Date date) {
//        parser = getParser(parseType, regexBPath, regexSPath, country, date, null);
//        parserType = parseType;
//        this.country = country;
//    }
//    
//    public ProxyAutoNewsExtractionApproachByRegex_NO(String parseType, String regexBPath, String regexSPath, String country, Date date, Integer elapsedDaysFromArrival) {
//        parser = getParser(parseType, regexBPath, regexSPath, country, date, elapsedDaysFromArrival);
//        parserType = parseType;
//        this.country = country;        
//    }
//
//    private ExtractorParser<T> getParser(String parserType, String regexBPath, String regexSPath, String country, Date date, Integer elapsedDaysFromArrival){
//        Class<ExtractorParser<T>> clazz = (Class<ExtractorParser<T>>)(Class<?>) ExtractorParser.class;
//        ExtractorParser<T> ret = null; 
//        if(classExtractorMap.containsKey(parserType)){
//            if(clazz.isAssignableFrom(classExtractorMap.get(parserType))){
//                try {
//                    if(elapsedDaysFromArrival==null){
//                        ret =  classExtractorMap.get(parserType).getConstructor(String.class, String.class, String.class, Date.class).newInstance(regexBPath, regexSPath, country, date);
//                    }else{
//                        ret = classExtractorMap.get(parserType).getConstructor(String.class, String.class, String.class, Date.class, Integer.class).newInstance(regexBPath, regexSPath, country, date, elapsedDaysFromArrival);
//                    }
//                } catch (InstantiationException | IllegalAccessException ex) {
//                    throw new RuntimeException(ex.getMessage(), ex);
//                } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
//                    Logger.getLogger(ProxyAutoNewsExtractionApproachByRegex_NO.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }else{
//               throw new RuntimeException(String.format("Class for model %s is not FactReader<BoatFact, BoatFactCounter>", parserType)); 
//            }
//        }else{
//            throw new RuntimeException(String.format("%s is an unknown model class for this proxy", parserType));
//        }
//        return ret;
//    }
//    
//    public FileFormatter<? extends Fact> getCsvFormatter(){
//        return getCsvFormatter(parserType);
//    }
//    
//    private FileFormatter<? extends Fact> getCsvFormatter(String parserType){
//        Class<? extends FileFormatter<? extends Fact>> clazz = (Class<? extends FileFormatter<? extends Fact>>)(Class<?>) FileFormatter.class;
//        FileFormatter<? extends Fact> ret = null; 
//        if(classFormatterMap.containsKey(parserType)){
//            if(clazz.isAssignableFrom(classFormatterMap.get(parserType))){
//                try {                    
//                    ret = classFormatterMap.get(parserType).getConstructor().newInstance();
//                } catch (InstantiationException | IllegalAccessException ex) {
//                    throw new RuntimeException(ex.getMessage(), ex);
//                } catch (NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException ex) {
//                    Logger.getLogger(ProxyAutoNewsExtractionApproachByRegex_NO.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }else{
//               throw new RuntimeException(String.format("Class for model %s is not FactReader<BoatFact, BoatFactCounter>", parserType)); 
//            }
//        }else{
//            throw new RuntimeException(String.format("%s is an unknown model class for this proxy", parserType));
//        }
//        return ret;
//    }
//    
//
//    @Override
//    public List<T> parseFromString(String bonText) {
//        return parser.parseFromString(bonText);
//    }
//    
//    public String getParserType(){
//        return parserType;
//    }
//
//    @Override
//    public T getLastParsedData() {
//        return parser.getLastParsedData();
//    }
//
//    @Override
//    public void setLastParsedData(T last) {
//        parser.setLastParsedData(last);
//    }
//    
//    public String getCountry(){
//        return country;
//    } 
}
