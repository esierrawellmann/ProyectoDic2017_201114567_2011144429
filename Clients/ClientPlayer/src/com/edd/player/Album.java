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
public class Album {
    
    private String nombre_album;
    private ArrayList<Cancion> canciones;
    
    public Album()
    {
        
    }
    
    public String getnombre(){
        return nombre_album;
    }
    
    public void setnombre(String nombre){
        this.nombre_album=nombre;
    }
    
    public ArrayList<Cancion> getcanciones(){
        return canciones;
    }
    
    public void setcanciones(ArrayList<Cancion> lista){
        this.canciones=lista;
    }
    
}