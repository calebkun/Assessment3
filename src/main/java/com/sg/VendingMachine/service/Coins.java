/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.service;

import java.math.BigDecimal;

/**
 *
 * @author calebdiaz
 */
public enum Coins {
    
    QUARTER(new BigDecimal("25")), DIME(new BigDecimal("10")), NICKEL(new BigDecimal("5")), PENNY(new BigDecimal("1"));
    
    private BigDecimal bd;
    
    public BigDecimal getValue(){
        return bd;
    }
    
    private Coins(BigDecimal bd){
        this.bd = bd;
    }
    
}
