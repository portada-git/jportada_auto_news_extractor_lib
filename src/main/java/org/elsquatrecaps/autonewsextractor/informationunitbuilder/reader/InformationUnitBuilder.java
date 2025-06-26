package org.elsquatrecaps.autonewsextractor.informationunitbuilder.reader;

import java.util.List;
import org.elsquatrecaps.autonewsextractor.model.PublicationInfo;
import org.elsquatrecaps.utilities.iterators.ConsumerIterator;
import org.elsquatrecaps.utilities.tools.Callback;

/**
 *
 * @author josep
 */
public interface InformationUnitBuilder extends FactReader{
    void createAndProcessEachInformationUnitFiles(Callback<InformationUnitDataParamsFromFilesForCallback, Void> callbak, String modelVersion);
    void createAndProcessEachInformationUnitFiles(Callback<InformationUnitDataParamsFromFilesForCallback, Void> callbak, 
            ConsumerIterator<DataToProcess> iterator, String modelVersion);
    void createAndProcessEachInformationUnitFiles(Callback<InformationUnitDataParamsFromFilesForCallback, Void> callbak, String originDir, String modelVarsion);
    void createAndProcessEachInformationUnitFiles(Callback<InformationUnitDataParamsFromFilesForCallback, Void> callbak, String originDir, String extension, String modelVarsion);
    void createAndProcessEachInformationUnitText(Callback<InformationUnitDataParamsFromTextsForCallback, Void> callbak, PublicationInfo publicationInfo, String... text);
    void createAndProcessEachInformationUnitFiles(Callback<InformationUnitDataParamsFromFilesForCallback, Void> callbak, PublicationInfo publicationInfo);
//    JSONArray getAllInformationUnitAsJson();
   
    interface InformationUnitDataParamsForCallback{
        PublicationInfo getPublicationInfo();
        String getInfomationUnitName();
    }

    interface InformationUnitDataParamsFromErrorForCallback extends InformationUnitDataParamsForCallback{ 
        boolean hasError();
        Exception getException();
    }

    interface InformationUnitDataParamsFromTextsForCallback extends InformationUnitDataParamsForCallback{
        String getInformationUnitText();
    }

    interface InformationUnitDataParamsFromFilesForCallback extends InformationUnitDataParamsFromTextsForCallback{
        List<String> getFileNames();
        float getCompletedRatio();
    }
    
}
