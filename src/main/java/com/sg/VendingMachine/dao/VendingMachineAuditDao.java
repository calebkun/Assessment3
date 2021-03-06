/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.dao;

/**
 *
 * @author calebdiaz
 */
public interface VendingMachineAuditDao {
    
    /**
     * Takes in entry and writes to audit log.
     * 
     * @param entry
     * @throws VendingMachinePersistenceException 
     */
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException;
    
}
