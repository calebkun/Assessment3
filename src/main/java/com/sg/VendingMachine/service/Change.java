/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
        

/**
 * Change construct with methods to convert BigDecimal values ($) into change.
 *
 * @author calebdiaz
 */
public class Change {
    
    private Coins coins;
    
    private BigDecimal pennies;
    private BigDecimal nickels;
    private BigDecimal dimes;
    private BigDecimal quarters;
    
    /**
     * Takes in user's money and cost of item as strings, converts them to BigDecimal objects
     * and
     * @param input
     * @param cost
     * @return 
     */
    public String getChange(String input, String cost){
        BigDecimal inputBD = new BigDecimal(input);
        BigDecimal costBD = new BigDecimal(cost);
        
        inputBD.setScale(2, RoundingMode.HALF_UP);
        costBD.setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal inputPennies = toPennies(inputBD);
        BigDecimal costPennies = toPennies(costBD);
        
        makeChange(inputPennies, costPennies);
        
        String change = quarters.toString()+" quarters, "+dimes.toString()+" dimes, "+nickels.toString()+" nickels, and "+pennies.toString()+" pennies is your change.";
        
        return change;
    }
    
    public void makeChange(BigDecimal one, BigDecimal two){
        BigDecimal diff = one.subtract(two, new MathContext(4));
        
        BigDecimal q[] = diff.divideAndRemainder(coins.QUARTER.getValue());
        quarters = q[0];
        quarters.setScale(0);
        
        BigDecimal d[] = q[1].divideAndRemainder(coins.DIME.getValue());
        dimes = d[0];
        dimes.setScale(0);
        
        BigDecimal n[] = d[1].divideAndRemainder(coins.NICKEL.getValue());
        nickels = n[0];
        nickels.setScale(0);
        
        BigDecimal p[] = n[1].divideAndRemainder(coins.PENNY.getValue());
        pennies = p[0];
        pennies.setScale(0);
        
    }
    
    public BigDecimal toPennies(BigDecimal bd){
        BigDecimal multiplicand = new BigDecimal("100");
        multiplicand.setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal inPennies = bd.multiply(multiplicand, new MathContext(4));
        
        inPennies.setScale(0);
        
        return inPennies;
    }
    
    public BigDecimal getPennies(){
        return pennies;
    }
    
    public BigDecimal getNickels(){
        return nickels;
    }
    
    public BigDecimal getDimes(){
        return dimes;
    }
    
    public BigDecimal getQuarters(){
        return quarters;
    }
}
