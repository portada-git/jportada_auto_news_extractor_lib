package org.elsquatrecaps.autonewsextractor.informationunitbuilder.runnable;

import org.elsquatrecaps.utilities.tools.Callback;
import org.json.JSONArray;

/**
 *
 * @author josep
 */
public interface InformationUnitBuilder extends Runnable{
    void createAndProcessEachInformationUnitText(Callback<? extends InformationUnitBuilderFromSdlFiles.TextInformationUnitDataParamsForCallback, Void> callbak);
    void createAndProcessEachInformationUnitFiles(Callback<? extends InformationUnitBuilderFromSdlFiles.FileInformationUnitDataParamsForCallback, Void> callbak);
    JSONArray getAllInformationUnitAsJson();
}
