/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.elsquatrecaps.autonewsextractor.tools.configuration;

import org.elsquatrecaps.utilities.tools.configuration.Configuration;

/**
 *
 * @author josepcanellas
 */
public interface ParserConfiguration extends Configuration{
    String getParserConfigJsonFile();
    void setParserConfigJsonFile(String parserConfigJsonFile);
    String[] getParseModel();
    void setParseModel(String[] parse_model);
}
