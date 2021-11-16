/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.service;

import com.sg.VendingMachine.dao.VendingMachinePersistenceException;
import com.sg.VendingMachine.dto.Item;
import java.util.List;

/**
 *
 * @author calebdiaz
 */
public interface VendingMachineServiceLayer {
    
    String dispenseChange(String money, String cost);
    
    List<Item> getAllItems() throws
            VendingMachinePersistenceException;
    
    Item getItem(int itemNumber) throws
            VendingMachinePersistenceException;
    
    Item removeItem(String money, int itemNumber) throws
            VendingMachinePersistenceException,
            VendingMachineNoItemInventoryException,
            VendingMachineInsufficientFundsException;

}
