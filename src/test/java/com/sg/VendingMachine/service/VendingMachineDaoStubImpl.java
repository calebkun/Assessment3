/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.service;

import com.sg.VendingMachine.dao.VendingMachineDao;
import com.sg.VendingMachine.dao.VendingMachinePersistenceException;
import com.sg.VendingMachine.dto.Item;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author calebdiaz
 */
public class VendingMachineDaoStubImpl implements VendingMachineDao {
    
    public Item onlyItem;

    public VendingMachineDaoStubImpl() {
        onlyItem = new Item(1);
        onlyItem.setName("Item 1");
        onlyItem.setCost("1.00");
        onlyItem.setInventory(1);
    }

    public VendingMachineDaoStubImpl(Item testItem){
         this.onlyItem = testItem;
     }
    
    @Override
    public List<Item> getAllItems()
        throws VendingMachinePersistenceException{
        List<Item> itemList = new ArrayList<>();
        itemList.add(onlyItem);
        return itemList;
    };
    
    @Override
    public Item getItem(int itemNumber)
        throws VendingMachinePersistenceException{
        if(itemNumber == onlyItem.getItemNum()){
            return onlyItem;
        } else {
            return null;
        }
    };
    
    @Override
    public Item removeItem(int itemNumber)
        throws VendingMachinePersistenceException{
        if(itemNumber == onlyItem.getItemNum()){
            onlyItem.setInventory(onlyItem.getInventory() -1);
            return onlyItem;
        } else {
            return null;
        }
    };
}
