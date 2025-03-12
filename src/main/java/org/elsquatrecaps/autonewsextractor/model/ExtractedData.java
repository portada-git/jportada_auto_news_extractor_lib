package org.elsquatrecaps.autonewsextractor.model;

import java.io.Serializable;

/**
 *
 * @author josep
 */
public interface ExtractedData extends Cloneable, Serializable{
    String MODEL_VERSION_FIELD_NAME = "model_version";
    String RAW_TEXT_FIELD_NAME = "raw_text";
    String PARSED_TEXT_FIELD_NAME = "parsed_text";
    String UNPARSED_TEXT_FIELD_NAME = "unparsed_text";

    String CALCULATED_VALUE_FIELD = "calculated_value";
    String DEFAULT_VALUE_FIELD = "default_value";
    String ORIGINAL_VALUE_FIELD = "original_value";

    String get(String field);
    
    Object getAsObject(String field);

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
    
    String getAllDataAsJson(boolean onlyOneValueForField);
    
    String getRawText();
    
    String getModelVersion();    
}
