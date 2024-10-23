/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.elsquatrecaps.autonewsextractor.model;

/**
 *
 * @author josep
 */
public interface ExtractedData extends Cloneable{

    String CALCULATED_VALUE_FIELD = "calculated_value";
    String DEFAULT_VALUE_FIELD = "default_value";
    String ORIGINAL_VALUE_FIELD = "original_value";

    String get(String field);

    String getCalculatedValue(String field);

    String getDefaultValue(String field);

    String getOriginalValue(String field);

    /**
     * @return the parsedText
     */
    String getParsedText();

    /**
     * @return the unparsedText
     */
    String getUnparsedText();
    
    String getAllDataAsJson();
    
    String getRawText();
    
}
