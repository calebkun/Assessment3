/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.ui;

import com.sg.VendingMachine.dto.Item;
import java.util.*;

/**
 *
 * @author calebdiaz
 */
public class VendingMachineView {
    
    // UserIO member used to display to and get from the user information.
    final UserIO io;
    
    public VendingMachineView(UserIO io){
        this.io = io;
    }
    
    public String greetAndGetMoney(){
        String money = io.readBigDecimal("Please enter money in dollar amount.");
        return money;
    }
    
    /**
     * Prints main menu to user and prompts for selection.
     * 
     * @return int indicating menu selection
     */
    public int printMenuAndGetSelection(List<Item> itemList) {
        
        io.print("_______________________________");
        io.print("|       Vending Machine        |");
        io.print("===Item====Cost====Inventory===");
        
        itemList.stream()
                .forEach((item) -> {
                    if(item.getInventory()>0){
                        io.print(item.getItemNum()+". "+item.getName()+"   $"+item.getCost()+"        "+item.getInventory());
                    }
                });
        
        io.print("===============================");
        io.print("|______________________________|");
        io.print("6. Exit");
        io.print("");

        return io.readInt("Please select an item from the above choices to vend.", 1, 6);
    }
    
    public void displayItem(Item item){
        if (item != null) {
            io.print("");
            io.print("You selected: "+item.getName()+" Cost: $"+item.getCost());
            io.print("");
        } else {
            io.print("No such item.");
        }
    }
    
    public void displayVendResult(Item item){
        if(item != null){
          io.print("Item successfully vended.");
        }else{
          io.print("No such item.");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayChange(String change){
        io.print(change);
    }
    
    // Displays error message
    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
    
    // Displays exit message
    public void displayExitBanner() {
        io.print("Good Bye!");
    }

    // Displays unknown command message
    public void displayUnknownCommandBanner() {
        io.print("Unknown Command");
    }
}

