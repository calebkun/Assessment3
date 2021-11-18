/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine;


import com.sg.VendingMachine.controller.VendingMachineController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 *
 * @author calebdiaz
 */
public class App {
    public static void main(String args[]){
        
        AnnotationConfigApplicationContext appContext = new AnnotationConfigApplicationContext();
        appContext.scan("com.sg.VendingMachine");
        appContext.refresh();
        
        VendingMachineController controller = appContext.getBean("vendingMachineController", VendingMachineController.class);
        controller.run(); // run our app
    }
    
}
