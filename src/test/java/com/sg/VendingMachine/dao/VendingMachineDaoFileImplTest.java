/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.dao;

import com.sg.VendingMachine.dto.Item;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author calebdiaz
 */
public class VendingMachineDaoFileImplTest {
    
    VendingMachineDaoFileImpl testDao;
    
    public VendingMachineDaoFileImplTest() {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
        String testFile = "testinventory.txt";
        String item1 = "1::Item 1::1.00::10";
        String item2 = "2::Item 2::1.00::0";
        
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(testFile));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Could not save item data.", e);
        }
        
        out.println(item1);
        out.println(item2);
        
        out.flush();
        out.close();
        
        testDao = new VendingMachineDaoFileImpl(testFile);
    }

    @Test
    public void testRemoveGetItem() throws Exception {
        // ARRANGE: inventory test file already in good state, just create a copy of item 1
        
        Item temp = testDao.getItem(1);
        Item item1 = new Item(temp.getItemNum());
        item1.setName(temp.getName());
        item1.setCost(temp.getCost());
        item1.setInventory(temp.getInventory());
        
        // ACT: remove an item (decrease its inventory) and store its return
        
        Item item1Removed = testDao.removeItem(1);
        
        // ASSERT: inventory of the second item should be 1 less than the first item, and all other fields
        // should be the same
        
        assertEquals(item1Removed.getInventory(), item1.getInventory()-1, "Checking that inventory was decremented.");
        assertEquals(item1Removed.getItemNum(), item1.getItemNum(), "Checking that item number is unchanged.");
        assertEquals(item1Removed.getName(), item1.getName(), "Checking that name is unchanged");
        assertEquals(item1Removed.getCost(), item1.getCost(), "Checking that cost is unchanged");
        
    }
    
    @Test
    public void testGetAllItems() throws Exception {
        // ARRANGE: inventory test file already in good state, just create copies of items 1 and 2
        
        Item temp = testDao.getItem(1);
        Item item1 = new Item(temp.getItemNum());
        item1.setName(temp.getName());
        item1.setCost(temp.getCost());
        item1.setInventory(temp.getInventory());
        
        Item temp2 = testDao.getItem(2);
        Item item2 = new Item(temp2.getItemNum());
        item2.setName(temp2.getName());
        item2.setCost(temp2.getCost());
        item2.setInventory(temp2.getInventory());
        
        // ACT: get a list of all items
        
        List<Item> allItems = testDao.getAllItems();
        
        // ASSERT: general contents of list
        
        assertNotNull(allItems, "The list of items must not null");
        assertEquals(2, allItems.size(),"List of items should have 2 items.");
        
        // ASSERT: member fields of items in list match those of item copies
        
        assertEquals(item1.getItemNum(), allItems.get(0).getItemNum());
        assertEquals(item1.getName(), allItems.get(0).getName());
        assertEquals(item1.getCost(), allItems.get(0).getCost());
        assertEquals(item1.getInventory(), allItems.get(0).getInventory());
        
        assertEquals(item2.getItemNum(), allItems.get(1).getItemNum());
        assertEquals(item2.getName(), allItems.get(1).getName());
        assertEquals(item2.getCost(), allItems.get(1).getCost());
        assertEquals(item2.getInventory(), allItems.get(1).getInventory());
        
    }
    
}
