/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.elsquatrecaps.autonewsextractor.tools.configuration;

import java.util.Optional;

/**
 *
 * @author josep
 */
public interface Configurable<T> {
    <C> T init(C conf);
}
