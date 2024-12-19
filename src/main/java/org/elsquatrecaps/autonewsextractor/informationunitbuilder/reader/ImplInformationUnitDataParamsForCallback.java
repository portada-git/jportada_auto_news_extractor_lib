package org.elsquatrecaps.autonewsextractor.informationunitbuilder.reader;

import java.util.ArrayList;
import java.util.List;
import org.elsquatrecaps.autonewsextractor.informationunitbuilder.reader.InformationUnitBuilder.InformationUnitDataParamsFromErrorForCallback;
import org.elsquatrecaps.autonewsextractor.informationunitbuilder.reader.InformationUnitBuilder.InformationUnitDataParamsFromFilesForCallback;
import org.elsquatrecaps.autonewsextractor.model.PublicationInfo;

/**
 *
 * @author josep
 */
public class ImplInformationUnitDataParamsForCallback implements InformationUnitDataParamsFromFilesForCallback, InformationUnitDataParamsFromErrorForCallback {
    
    private boolean error = false;
    private PublicationInfo publicationInfo;
    private String text;
    private String informationUnitName;
    private List<String> files;
    private float completedRatio; 
    private Exception exception = null;

    public ImplInformationUnitDataParamsForCallback(
            PublicationInfo publicationInfo, 
            String text, 
            String name, 
            List<String> filesOrigin,
            float completedRatio
            /*, Configuration configuration*/ ) {
        this.publicationInfo = publicationInfo;
        this.files = filesOrigin;
        this.text = text;
        this.informationUnitName = name;
        this.completedRatio=completedRatio; 
    } /*, Configuration configuration*/

    public ImplInformationUnitDataParamsForCallback(PublicationInfo publicationInfo, String text, String name,  float completedRatio /*, Configuration configuration*/ ) {
        this(publicationInfo, text, name, new ArrayList<>(), completedRatio /*, configuration*/ );
    } /*, Configuration configuration*/ /*, configuration*/

    public ImplInformationUnitDataParamsForCallback(Exception ex) {
        error = true;
        exception = ex;
    }

    /**
     * @return the files
     */
    @Override
    public List<String> getFileNames() {
        return files;
    }

    /**
     * @return the error
     */
    @Override
    public boolean hasError() {
        return error;
    }

    /**
     * @return the exception
     */
    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public String getInformationUnitText() {
        return this.text;
    }

    @Override
    public PublicationInfo getPublicationInfo() {
        return publicationInfo;
    }

    /**
     * @return the informationUnitName
     */
    @Override
    public String getInfomationUnitName() {
        return informationUnitName;
    }

    /**
     * @return the completedRatio
     */
    public float getCompletedRatio() {
        return completedRatio;
    }
    
}
