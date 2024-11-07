/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.elsquatrecaps.autonewsextractor.targetfragmentbreaker.cutter;

import org.elsquatrecaps.autonewsextractor.tools.configuration.Configurable;

/**
 *
 * @author josep
 */
public interface TargetFragmentCutter extends Configurable<TargetFragmentCutter>{
    <Object> TargetFragmentCutter init(Object v);
    String getTargetTextFromText(String bonText);
}
