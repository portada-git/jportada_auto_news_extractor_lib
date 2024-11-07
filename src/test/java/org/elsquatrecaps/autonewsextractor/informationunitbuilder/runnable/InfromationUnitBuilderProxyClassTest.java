package org.elsquatrecaps.autonewsextractor.informationunitbuilder.runnable;

import org.elsquatrecaps.autonewsextractor.informationunitbuilder.reader.InfromationUnitBuilderProxyClass;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.elsquatrecaps.autonewsextractor.tools.configuration.AutoNewsExtractorConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author josep
 */
public class InfromationUnitBuilderProxyClassTest {
    static AutoNewsExtractorConfiguration configuration = new AutoNewsExtractorConfiguration();

    public InfromationUnitBuilderProxyClassTest() {
    }
    
    @BeforeAll
    public static void setUpClass() {
        String[] args ={
            "-r",
            "config/regex",
//            "/Users/josepcanellas/Dropbox/feinesJordi/github/autoNewsExtractorApp/regex",
            "-f",
            "boatfacts",
            "-n",
            "db",
            "-p",
            "boatdata.extractor,boatcosta.extractor",
            "-pcf",
            "config/conf_db/extractor_config.json",
//            "/Users/josepcanellas/Dropbox/feinesJordi/github/autoNewsExtractorApp/regex_config.json",
//            "-oe",
//            "a",
            "-iub_pck",
            "org.elsquatrecaps.autonewsextractor.informationunitbuilder.reader",
            "--origin_dir",
            "test/dades",
            "-c",
            "config/conf_db/init.properties"            
        };
        try {
            //        configuration.parseArguments(args);
            configuration.parseArgumentsAndConfigure(args);
        } catch (IOException ex) {
            Logger.getLogger(InfromationUnitBuilderProxyClassTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of run method, of class InfromationUnitBuilderProxyClass.
     */
    @Test
    public void testGetInstanceFromConfig() {
        System.out.println("getInstanceFromConfig");
        InfromationUnitBuilderProxyClass instance = InfromationUnitBuilderProxyClass.getInstance("file_name", "portada_file_name", configuration);
        
        assertNotNull(instance);
    }
    
    @Test
    public void testgetText() {
        System.out.println("testgetText");
        InfromationUnitBuilderProxyClass instance = InfromationUnitBuilderProxyClass.getInstance("file_name", "portada_file_name", configuration);
        instance.createAndProcessEachInformationUnitFiles((param) -> {
            System.out.println(param.getInformationUnitText());
            return null;
        }, "0000");
    }
    
}
