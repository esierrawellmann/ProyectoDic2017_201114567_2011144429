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
public class Artista {
    
    private String nombre;
    private ArrayList<Album> albums;
    
    public Artista()
    {
        
    }
    
    public String getnombre(){
        return nombre;
    }
    
    public void setnombre(String nombre){
        this.nombre=nombre;
    }
    
    public ArrayList<Album> getdiscos(){
        return albums;
    }
    
    public void setdiscos(ArrayList<Album> album){
        this.albums=album;
    }
    
}
