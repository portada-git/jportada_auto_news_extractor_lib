/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.elsquatrecaps.autonewsextractor.informationunitbuilder.reader;

import org.json.JSONObject;

/**
 *
 * @author josep
 */
public class DataToProcess {
    private String name;
    private String text;

    public DataToProcess(String name, String text) {
        this.name = name;
        this.text = text;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }
    
}
