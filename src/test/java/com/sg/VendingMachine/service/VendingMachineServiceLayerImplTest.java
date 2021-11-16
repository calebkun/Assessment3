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
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author calebdiaz
 */
public class VendingMachineServiceLayerImplTest {
    
    private VendingMachineServiceLayer service;
    
    public VendingMachineServiceLayerImplTest() {
        VendingMachineDao dao = new VendingMachineDaoStubImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();
        
        service = new VendingMachineServiceLayerImpl(dao, auditDao);
    }
    
    @BeforeEach
    public void setUp() {
        VendingMachineDao dao = new VendingMachineDaoStubImpl();
        VendingMachineAuditDao auditDao = new VendingMachineAuditDaoStubImpl();
        
        service = new VendingMachineServiceLayerImpl(dao, auditDao);
    }
    
    @Test
    public void testGetAllItems() throws Exception {
        // ARRANGE
        
        Item testClone = new Item(1);
        testClone.setName("Item 1");
        testClone.setCost("1.00");
        testClone.setInventory(1);
        
        // ACT
        
        List<Item> allItems = service.getAllItems();
        
        // ASSERT 
       
        assertEquals( 1, service.getAllItems().size(), "Should only have one item.");
        
        assertEquals(testClone.getItemNum(), allItems.get(0).getItemNum(), "Checking that item number is the same.");
        assertEquals(testClone.getName(), allItems.get(0).getName(), "Checking that name is the same.");
        assertEquals(testClone.getCost(), allItems.get(0).getCost(), "Checking that cost is the same.");
        assertEquals(testClone.getInventory(), allItems.get(0).getInventory(), "Checking that inventory is the same.");
    }
    
    @Test
    public void testGetItem() throws Exception {
        // ARRANGE
        
        Item testClone = new Item(1);
        testClone.setName("Item 1");
        testClone.setCost("1.00");
        testClone.setInventory(1);
        
        // ACT 
        
        Item shouldBe1 = service.getItem(1);
        Item shouldBeNull = service.getItem(2);
        
        // ASSERT
        
        assertEquals(testClone.getItemNum(), shouldBe1.getItemNum(), "Checking that item number is the same.");
        assertEquals(testClone.getName(), shouldBe1.getName(), "Checking that name is the same.");
        assertEquals(testClone.getCost(), shouldBe1.getCost(), "Checking that cost is the same.");
        assertEquals(testClone.getInventory(), shouldBe1.getInventory(), "Checking that inventory is the same.");
        
        assertNull(shouldBeNull, "Getting 2 should be null.");
    }
    
    @Test
    public void testValidRemoveItem() throws Exception {
         // ARRANGE
        
        Item testClone = new Item(1);
        testClone.setName("Item 1");
        testClone.setCost("1.00");
        testClone.setInventory(1);
        
        // ACT
        
        Item shouldBe1 = service.removeItem("2.00",1); // sufficient funds, remove while inventory is nonzero
        
        // ASSERT
        
        assertEquals(testClone.getItemNum(), shouldBe1.getItemNum(), "Checking that item number is the same.");
        assertEquals(testClone.getName(), shouldBe1.getName(), "Checking that name is the same.");
        assertEquals(testClone.getCost(), shouldBe1.getCost(), "Checking that cost is the same.");
        assertEquals(testClone.getInventory()-1, shouldBe1.getInventory(), "Checking that inventory is 1 less.");
        
    }
    
    @Test
    public void testRemoveItemInsufficientFunds() throws Exception {
        
        // ACT
        
        try {
            Item shouldFail = service.removeItem("0.00",1);
            fail("Expected InsufficientFunds Exception");
        } catch (VendingMachineNoItemInventoryException | VendingMachinePersistenceException e){
            fail("Incorrect exception thrown.");
        } catch (VendingMachineInsufficientFundsException e){
            return;
        }
        
    }
    
    @Test
    public void testRemoveItemOutOfStock() throws Exception {
       // ARRANGE
        
        Item testClone = new Item(1);
        testClone.setName("Item 1");
        testClone.setCost("1.00");
        testClone.setInventory(1);
        
        // ACT
        
        Item removed = service.removeItem("3.00", 1);
        
        try {
            Item shouldFail = service.removeItem("3.00",1);
            fail("Expected InsufficientFunds Exception");
        } catch (VendingMachineInsufficientFundsException | VendingMachinePersistenceException e){
            fail("Incorrect exception thrown.");
        } catch (VendingMachineNoItemInventoryException e){
            return;
        }
    }
    
}
