/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.dao;

import java.util.*;
import com.sg.VendingMachine.dto.Item;

/**
 *
 * @author calebdiaz
 */
public interface VendingMachineDao {
 
    
    /**
     * Returns a List of all items in inventory.
     * 
     * @return List<Item> of all items in inventory
     */
    List<Item> getAllItems() throws VendingMachinePersistenceException;
    
    /**
     * Returns the item associated with the given name. 
     * Returns null if no such item exists.
     * 
     * @param name of item to get
     * @return item object or null
     */
    Item getItem(int itemNumber) throws VendingMachinePersistenceException;
    
    /**
     * Removes the item associated with the given name from inventory.
     * Returns the Item object being removed, or null if no such item exists.
     * 
     * @param name of item
     * @return Item object or null
     */
    Item removeItem(int itemNumber) throws VendingMachinePersistenceException;
    
    
    
}
