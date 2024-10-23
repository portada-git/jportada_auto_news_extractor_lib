package org.elsquatrecaps.autonewsextractor.tools.formatter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elsquatrecaps.autonewsextractor.error.AutoNewsReaderRuntimeException;
import org.elsquatrecaps.autonewsextractor.model.BoatFactData;
import org.elsquatrecaps.autonewsextractor.model.MutableNewsExtractedData;

/**
 *
 * @author josep
 */
public class BoatFactCsvFormatter extends GenericCsvFileFormatter<MutableNewsExtractedData>{
    private static final String CSV_HEADER = "\"Publication date\";\"Departure date\";\"Voyage duration\";\"Arrival date\";\"Origin port\";\"Port of calls\";\"Arrival port\";"
            + "\"Ship type\";\"Ship name\";\"Ship tonnage\";\"Ship flag\";\"Master name\";\"Master role\";\"Cargo\";\"Raw text\"";
//    private static String CSV_HEADER = "\"Data publicació\",\"Data sortida\",\"Durada Viatge\",\"Data arribada\",\"Port sortida\",\"Ports ruta\",\"Port destí\","
//            + "\"Tipus embarcació\",\"Nom embarcació\",\"Tonatge embarcació\",\"Bandera embarcació\",\"Nom del responsable\","
//            + "\"Càrrec del responsnable\",\"Bens transportants\",\"Text cru\"";
    private final String fieldSeparator = ";";
//    private String fieldSeparator = ",";

    public  String factToString(BoatFactData bf) {
        StringBuilder stb = new StringBuilder();
        stb.append(String.format("%1$tY-%1$tm-%1$td", bf.getPublicationDate()));
        stb.append(fieldSeparator);
        stb.append(bf.getDepartureDate());
        stb.append(fieldSeparator);
        stb.append("\"");
        stb.append(bf.getVoyageDuration());
        stb.append("\"");
        stb.append(fieldSeparator);
        stb.append(bf.getArrivalDate());
        stb.append(fieldSeparator);
        stb.append("\"");
        stb.append(bf.getOrigin().replaceAll("\"", "'"));
        stb.append("\"");
        stb.append(fieldSeparator);
        stb.append("\"");
        if(bf.hasPortOfCalls()){
            stb.append(bf.getPortOfCalls().replaceAll("\"", "'"));
        }
        stb.append("\"");
        stb.append(fieldSeparator);
        stb.append("\"");
        stb.append(bf.getArrivalHarbor().replaceAll("\"", "'"));
        stb.append("\"");
        stb.append(fieldSeparator);
        stb.append("\"");
        stb.append(bf.getShipType().replaceAll("\"", "'"));
        stb.append("\"");
        stb.append(fieldSeparator);
        stb.append("\"");
        stb.append(bf.getShipName().replaceAll("\"", "'"));
        stb.append("\"");
        stb.append(fieldSeparator);
        stb.append("\"");
        stb.append(bf.getShipTonnage().replaceAll("\"", "'"));
        stb.append("\"");
        stb.append(fieldSeparator);
        stb.append("\"");
        stb.append(bf.getShipFlag().replaceAll("\"", "'"));
        stb.append("\"");
        stb.append(fieldSeparator);
        stb.append("\"");
        stb.append(bf.getMasterName().replaceAll("\"", "'"));
        stb.append("\"");
        stb.append(fieldSeparator);
        stb.append(bf.getMasterRole().replaceAll("\"", "'"));
        stb.append(fieldSeparator);
        stb.append("\"");
        stb.append(bf.getOtherInformation().replaceAll("\"", "'"));
        stb.append("\"");
        stb.append(fieldSeparator);
        stb.append("\"");
        stb.append(bf.getParsedText().replaceAll("\"", "'"));
        stb.append("\"");
        return stb.toString();           
    }    

    public String getCsvHeader() {
        return CSV_HEADER;
    }
    
@Override
    public String toString() {
        StringBuilder stb = new StringBuilder();
        if(!isAppendFile()){
            stb.append(getCsvHeader());
        }
        for(MutableNewsExtractedData fact: this.getList()){
            BoatFactData bfd;
            if(fact instanceof BoatFactData){
                bfd = (BoatFactData) fact;
            }else{
                bfd = new BoatFactData(fact);
            }
            stb.append(this.factToString(bfd));
            stb.append("\n");
        }
        return stb.toString();
    }

    @Override
    public void toFile(String outputFileName) {
        File f = (new File(outputFileName)).getAbsoluteFile();
        if(f.getParentFile()!=null){
            f.getParentFile().mkdirs();
        }
        try(BufferedWriter fw = new BufferedWriter(new FileWriter(outputFileName, isAppendFile()))){
            if(!isAppendFile()){
                fw.write(getCsvHeader());
                fw.newLine();
            }
            for(MutableNewsExtractedData fact: this.getList()){
                BoatFactData bfd;
                if(fact instanceof BoatFactData){
                    bfd = (BoatFactData) fact;
                }else{
                    bfd = new BoatFactData(fact);
                }
                fw.write(this.factToString(bfd));
                fw.newLine();
            }            
        } catch (IOException ex) {
            Logger.getLogger(GenericCsvFileFormatter.class.getName()).log(Level.SEVERE, null, ex);
            throw new AutoNewsReaderRuntimeException(ex);
        }
    }    

    
}