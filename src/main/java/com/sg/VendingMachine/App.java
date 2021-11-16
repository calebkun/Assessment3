/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine;

import com.sg.VendingMachine.controller.VendingMachineController;
import com.sg.VendingMachine.dao.VendingMachineAuditDao;
import com.sg.VendingMachine.dao.VendingMachineAuditDaoFileImpl;
import com.sg.VendingMachine.dao.VendingMachineDao;
import com.sg.VendingMachine.dao.VendingMachineDaoFileImpl;
import com.sg.VendingMachine.service.VendingMachineServiceLayerImpl;
import com.sg.VendingMachine.service.VendingMachineServiceLayer;
import com.sg.VendingMachine.ui.UserIO;
import com.sg.VendingMachine.ui.UserIOConsoleImpl;
import com.sg.VendingMachine.ui.VendingMachineView;

/**
 *
 * @author calebdiaz
 */
public class App {
    public static void main(String args[]){
        
        UserIO myIO = new UserIOConsoleImpl();
        VendingMachineView myView = new VendingMachineView(myIO);
        
        VendingMachineDao myDao = new VendingMachineDaoFileImpl();
        VendingMachineAuditDao myAuditDao = new VendingMachineAuditDaoFileImpl();
        VendingMachineServiceLayer myService = new VendingMachineServiceLayerImpl(myDao, myAuditDao);
        
        VendingMachineController controller = new VendingMachineController(myView, myService);
        controller.run();
    }
    
}
