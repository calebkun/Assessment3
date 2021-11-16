/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.dao;

import com.sg.VendingMachine.dto.Item;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 *
 * @author calebdiaz
 */
public class VendingMachineDaoFileImpl implements VendingMachineDao {
    
    // constants used for file persistence
    public final String INVENTORY_FILE;
    public static final String DELIMITER = "::";
    
    // map containing records for our items
    private Map<Integer, Item> inventory = new HashMap<>();
    
    public VendingMachineDaoFileImpl(){
        this.INVENTORY_FILE = "inventory.txt";
    }
    
    public VendingMachineDaoFileImpl(String file_name){
        this.INVENTORY_FILE = file_name;
    }
    
    /**
     * Responsible for taking a line from our file and parsing the line to
     * extract info needed to create a corresponding Item object.
     * 
     * @param itemAsText
     * @return itemFromFile
     */
    private Item unmarshallItem(String itemAsText){
        // itemAsText is expecting a line read in from our file.
        // For example, it might look like this:
        // 1::Item 1::1.00::10
        //
        // We then split that line on our DELIMITER - which we are using as ::
        // Leaving us with an array of Strings, stored in dvdTokens.
        // Which should look like this:
        // ______________________________________
        // |     |        |          |          |
        // |1    |Item 1  |   1.00   |   10     |
        // |     |        |          |          |
        // -------------------------------------
        //  [0]     [1]       [2]        [3]    
        String[] itemTokens = itemAsText.split(DELIMITER);

        // Given the pattern above, the item number is in index 0 of the array.
        String itemNum = itemTokens[0];

        // Which we can then use to create a new Item object to satisfy
        // the requirements of the Item constructor.
        Item itemFromFile = new Item(Integer.parseInt(itemNum));

        // However, there are 3 remaining tokens that need to be set into the
        // new item object. We do this manually by using the appropriate setters.

        // Index 1 - name
        itemFromFile.setName(itemTokens[1]);

        // Index 2 - cost
        itemFromFile.setCost(itemTokens[2]);

        // Index 3 - inventory
        itemFromFile.setInventory(Integer.parseInt(itemTokens[3]));
        

        // We have now created a Item!
        return itemFromFile;
    }
    
    /**
     * Responsible for taking a Item object and translating it's member fields
     * into the appropriate format to be stored in our file. The string of the appropriate
     * file is then passed as input to unmarshallItem()
     * 
     * @param anItem
     * @return itemAsText
     */
    private String marshallItem(Item anItem){
        // We need to turn an Item object into a line of text for our file.
        // For example, we need an in memory object to end up like this:
        // 1::Item 1::1.00::10

        // We get out each property, and concatenate with our DELIMITER as a kind of spacer. 

        // Start with the item number, since that's supposed to be first.
        String itemAsText = String.valueOf(anItem.getItemNum()) + DELIMITER;

        // add the rest of the properties in the correct order:
        
        // name
        itemAsText += anItem.getName() + DELIMITER;
        
        // cost
        itemAsText += anItem.getCost() + DELIMITER;
        
        // inventory -- skip delimiter
        itemAsText += String.valueOf(anItem.getInventory());

        // We have now turned an item to text! Return it!
        return itemAsText;
    }

    /**
     * Creates Scanner object to open persistence file if it exists 
     * and parses each line, calling unmarshallItem() on each to create Item objects
     * 
     * @throws VendingMachinePersistenceException 
     */
    private void loadInventory() throws VendingMachinePersistenceException {
        Scanner scanner;

        try {
            // Create Scanner for reading the file
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(INVENTORY_FILE)));
        } catch (FileNotFoundException e) {
            throw new VendingMachinePersistenceException(
                    "-_- Could not load inventory data into memory.", e);
        }
        // currentLine holds the most recent line read from the file
        String currentLine;
        // currentItem holds the most recent item unmarshalled
        Item currentItem;
        // Go through INVENTORY_FILE line by line, decoding each line into a 
        // Item object by calling the unmarshallItem method.
        // Process while we have more lines in the file
        while (scanner.hasNextLine()) {
            // get the next line in the file
            currentLine = scanner.nextLine();
            // unmarshall the line into a Item
            currentItem = unmarshallItem(currentLine);

            // Put currentItem into the map using title as the key
            inventory.put(currentItem.getItemNum(), currentItem);
        }
        // close scanner
        scanner.close();
    }
    
    /**
    * Writes all items in the library out to a INVENTORY_FILE.  See loadInventory
    * for file format.
    * 
    * @throws VendingMachineDaoException if an error occurs writing to the file
    */
    private void writeInventory() throws VendingMachinePersistenceException {
        // We are not handling the IOException - but
        // we are translating it to an application specific exception and 
        // then simple throwing it (i.e. 'reporting' it) to the code that
        // called us.  It is the responsibility of the calling code to 
        // handle any errors that occur.
        PrintWriter out;

        try {
            out = new PrintWriter(new FileWriter(INVENTORY_FILE));
        } catch (IOException e) {
            throw new VendingMachinePersistenceException(
                    "Could not save item data.", e);
        }

        // Write out the Item objects to the inventory file.
        String itemAsText;
        List<Item> itemList = this.getAllItems();
        for (Item currentItem : itemList) {
            // turn an Item into a String
            itemAsText = marshallItem(currentItem);
            // write the Item object to the file
            out.println(itemAsText);
            // force PrintWriter to write line to the file
            out.flush();
        }
        // Clean up
        out.close();
    }

    
    @Override
    public List<Item> getAllItems()
        throws VendingMachinePersistenceException{
        loadInventory();
        return new ArrayList<Item>(inventory.values());
    };
    
    @Override
    public Item getItem(int itemNumber)
        throws VendingMachinePersistenceException{
        loadInventory();
        return inventory.get(itemNumber);
    };
    
    @Override
    public Item removeItem(int itemNumber)
        throws VendingMachinePersistenceException{
        loadInventory();
        Item selection = inventory.get(itemNumber);
        selection.setInventory(selection.getInventory() -1);
        inventory.put(itemNumber, selection);
        writeInventory();
        return selection;
    };
}
