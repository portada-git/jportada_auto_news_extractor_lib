/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package org.elsquatrecaps.autonewsextractor.informationunitbuilder.runnable;

import org.elsquatrecaps.autonewsextractor.tools.AutoNewsExtractorConfiguration;
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
            "/home/josep/Dropbox/feinesJordi/github/autoNewsExtractorApp/regex",
            "-f",
            "sdl.boatfacts",
            "-n",
            "db.arca.txt",
            "-p",
            "db.boatfact.parser,db.boatcounter.parser",
            "-oe",
            "a"
        };
        configuration.parseArguments(args);        
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
        InfromationUnitBuilderProxyClass instance = InfromationUnitBuilderProxyClass.getInstance("file_name", "sdl_file_name", configuration);
        
        assertNotNull(instance);
    }
    
    @Test
    public void testRun() {
        System.out.println("run");
//        InfromationUnitBuilderProxyClass instance = InfromationUnitBuilderProxyClass.getInstance("file_name", "sdl_file_name", configuration);
        
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
