/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.elsquatrecaps.autonewsextractor.dataextractor.calculators;

/**
 *
 * @author josep
 * @param <P>
 * @param <R>
 */
//public abstract class AbstractCalculator<P,R> implements ConfigurableAutoNewsExtractorCalculator<P, R>{
public abstract class AbstractCalculator<P,R> implements AutoNewsExtractorCalculator<P, R>{
    @Override
    public R call(P param) {
        return calculate(param);
    }    

    @Override
    public void init(Object conf) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
