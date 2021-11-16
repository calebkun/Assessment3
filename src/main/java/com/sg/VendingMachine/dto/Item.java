/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.VendingMachine.dto;

/**
 *
 * @author calebdiaz
 */
public class Item {
    
    private int itemNum;
    private String name;
    private String cost;
    private int inventory;
    
    public Item(int itemNum){
        this.itemNum = itemNum;
        this.inventory = 10;
    }
    
    public int getItemNum(){
        return itemNum;
    }
    
    public String getName(){
        return name;
    }
    
    public void setName(String name){
        this.name = name;
    }
    
    public String getCost(){
        return cost;
    }
    
    public void setCost(String cost){
        this.cost = cost;
    }
    
    public int getInventory(){
        return inventory;
    }
    
    public void setInventory(int inventory){
        this.inventory = inventory;
    }
    
}
