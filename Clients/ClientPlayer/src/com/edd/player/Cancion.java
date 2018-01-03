/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edd.player;

/**
 *
 * @author Renan
 */
public class Cancion {
    
    private String nombre_cancion;
    private String ruta;
    
    public Cancion()
    {
        
    }
    
    public String getNombre_Cancion(){
        return nombre_cancion;
    }
    
    public void setNombre_Cancion(String nombre){
        this.nombre_cancion=nombre;
    }
    
    public String getPath(){
        return ruta;
    }
    
    public void setPath(String path){
        this.ruta=path;
    }
    
}
