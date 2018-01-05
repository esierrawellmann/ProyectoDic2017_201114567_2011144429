/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spotify;

import java.util.ArrayList;

/**
 *
 * @author walteregv95
 */
public class Artista { 
    
     private String nombre;
    private ArrayList<Album> albums;
    
    public Artista()
    {
        
    }
    
    public String getNombre_Artista(){
        return nombre;
    }
    
    public void setNombre_Artista(String nombre){
        this.nombre=nombre;
    }
    
    public ArrayList<Album> getLista_Albumes(){
        return albums;
    }
    
    public void setLista_Albumes(ArrayList<Album> album){
        this.albums=album;
    }
    
}
