/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edd.player;

import java.util.ArrayList;

/**
 *
 * @author Renan
 */
public class Datos {
    
    private ArrayList<Year> years;
    
    public Datos()
    {
        
    }
    
    public ArrayList<Year> getyears(){
        return years;
    }
    
    public void setyears(ArrayList<Year> years){
        this.years=years;
    }
    
}
