/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
    private List<String> files;
    private Exception exception = null;

    public ImplInformationUnitDataParamsForCallback(PublicationInfo publicationInfo, String text, List<String> filesOrigin /*, Configuration configuration*/ ) {
        this.publicationInfo = publicationInfo;
        this.files = filesOrigin;
        this.text = text;
    } /*, Configuration configuration*/

    public ImplInformationUnitDataParamsForCallback(PublicationInfo publicationInfo, String text /*, Configuration configuration*/ ) {
        this(publicationInfo, text, new ArrayList<>() /*, configuration*/ );
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
    
}
