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
public class Genero {
    
    private String genero;
    private ArrayList<Artista> artistas;
    
    public Genero()
    {
        
    }
    
    public String getnombre(){
        return genero;
    }
    
    public void setnombre(String genero){
        this.genero=genero;
    }
    
    public ArrayList<Artista> getartistas(){
        return artistas;
    }
    
    public void setartistas(ArrayList<Artista> lista){
        this.artistas=lista;
    }
    
}
