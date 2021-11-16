/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.controller;

import com.sg.VendingMachine.service.VendingMachineServiceLayer;
import com.sg.VendingMachine.dao.VendingMachinePersistenceException;
import com.sg.VendingMachine.dto.Item;
import com.sg.VendingMachine.service.VendingMachineInsufficientFundsException;
import com.sg.VendingMachine.service.VendingMachineNoItemInventoryException;
import com.sg.VendingMachine.ui.VendingMachineView;

/**
 *
 * @author calebdiaz
 */
public class VendingMachineController {
    
    private VendingMachineView view;
    private VendingMachineServiceLayer service;
    
    public VendingMachineController(VendingMachineView view, VendingMachineServiceLayer service){
        this.view = view;
        this.service = service;
    }
    
    private int getMenuSelection()
        throws VendingMachinePersistenceException{
        return view.printMenuAndGetSelection(service.getAllItems());
    }
    
    private String getMoney(){
        return view.greetAndGetMoney();
    }
    
    private void vendItem(int selection)
        throws VendingMachinePersistenceException{
        boolean hasErrors = false;
        do{
            String money = getMoney();
            Item toVend = service.getItem(selection);
            view.displayItem(toVend);
            try{
                Item vended = service.removeItem(money, selection);
                view.displayVendResult(vended);
                String change = service.dispenseChange(money, vended.getCost());
                view.displayChange(change);
                hasErrors = false;
            } catch (VendingMachineInsufficientFundsException e){
                hasErrors = true;
                view.displayErrorMessage(e.getMessage());
            } catch (VendingMachineNoItemInventoryException e){
                hasErrors = false;
                view.displayErrorMessage(e.getMessage());
            }
        } while (hasErrors);
    }
    
    /**
     * Calls view method to display appropriate message when a user's menu selection does not
     * correspond to a command.
     */
    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }
    
    /**
     * Calls view method to display goodbye message when user selects to exit.
     */
    private void exitMessage() {
        view.displayExitBanner();
    }
    
    public void run(){
        boolean keepGoing = true;
        int menuSelection;
        
        try{
            while(keepGoing){

                menuSelection = getMenuSelection();
                switch (menuSelection) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                            Item selection = service.getItem(menuSelection);
                            if(selection.getInventory()>0){
                                vendItem(menuSelection);
                            } else {
                                unknownCommand();
                            }
                            break;
                        case 6:
                            exitMessage();
                            keepGoing = false;
                            break;
                        default:
                            unknownCommand();
                    }
            }
        } catch (VendingMachinePersistenceException e){
            view.displayErrorMessage(e.getMessage());
        }
    
    }
    
}
