/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.service;

import com.sg.VendingMachine.dao.VendingMachineAuditDao;
import com.sg.VendingMachine.dao.VendingMachinePersistenceException;

/**
 *
 * @author calebdiaz
 */
public class VendingMachineAuditDaoStubImpl implements VendingMachineAuditDao {
    
    @Override
    public void writeAuditEntry(String entry) throws VendingMachinePersistenceException {
        //do nothing . . .
    }

}
