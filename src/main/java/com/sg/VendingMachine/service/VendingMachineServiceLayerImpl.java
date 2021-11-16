/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.service;

import com.sg.VendingMachine.dao.VendingMachineAuditDao;
import com.sg.VendingMachine.dao.VendingMachineDao;
import com.sg.VendingMachine.dao.VendingMachinePersistenceException;
import com.sg.VendingMachine.dto.Item;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;


/**
 *
 * @author calebdiaz
 */
public class VendingMachineServiceLayerImpl implements VendingMachineServiceLayer{
    
    private VendingMachineDao dao;
    private VendingMachineAuditDao auditDao;
    
    public VendingMachineServiceLayerImpl(VendingMachineDao dao, VendingMachineAuditDao auditDao){
        this.dao = dao;
        this.auditDao = auditDao;
    }
    
    private void validateVend(String money, Item item)
        throws VendingMachineInsufficientFundsException,
            VendingMachineNoItemInventoryException {
        
        if(item.getInventory() == 0){
            throw new VendingMachineNoItemInventoryException("Item is out of stock.");
        }
        
        BigDecimal input = new BigDecimal(money);
        input.setScale(2, RoundingMode.HALF_UP);
        
        BigDecimal cost = new BigDecimal(item.getCost());
        cost.setScale(2, RoundingMode.HALF_UP);
        
        int comp = input.compareTo(cost);
        
        if(comp == -1){
            throw new VendingMachineInsufficientFundsException("Insufficient funds. You entered: $"+money);
        }
        
    }
    
    @Override
    public String dispenseChange(String money, String cost){
        Change change = new Change();
        return change.getChange(money, cost);
    }
    
    @Override
    public List<Item> getAllItems() throws
            VendingMachinePersistenceException{
        return dao.getAllItems();
    }
    
    @Override
    public Item getItem(int itemNumber) throws
            VendingMachinePersistenceException{
        return dao.getItem(itemNumber);
    };
    
    @Override
    public Item removeItem(String money, int itemNumber) throws
            VendingMachinePersistenceException,
            VendingMachineNoItemInventoryException,
            VendingMachineInsufficientFundsException {
        
        Item item = dao.getItem(itemNumber);
        
        validateVend(money, item);
        
        Item toReturn = dao.removeItem(itemNumber);
        
        Item audit = dao.getItem(itemNumber);
        
        auditDao.writeAuditEntry(audit.getName()+" VENDED. INVENTORY: "+audit.getInventory());
        
        return toReturn;
        
    };
}
