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
public class Year {
    
    private String year;
    private ArrayList<Genero> generos;
    
    public Year()
    {
        
    }
    
    public String getyear(){
        return year;
    }
    
    public void setyear(String year){
        this.year=year;
    }
    
    public ArrayList<Genero> getgeneros(){
        return generos;
    }
    
    public void setgeneros(ArrayList<Genero> lista){
        this.generos=lista;
    }
    
}
