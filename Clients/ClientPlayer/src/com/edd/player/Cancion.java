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

    private Integer index;
    private String nombre_cancion;
    private String ruta;
    
    public Cancion()
    {
        
    }
    
    public Integer getindex(){
        return index;
    }
    
    public void setindex(Integer index){
        this.index=index;
    }
    
    public String getnombre(){
        return nombre_cancion;
    }
    
    public void setnombre(String nombre){
        this.nombre_cancion=nombre;
    }
    
    public String getpath(){
        return ruta;
    }
    
    public void setpath(String path){
        this.ruta=path;
    }
    
}
