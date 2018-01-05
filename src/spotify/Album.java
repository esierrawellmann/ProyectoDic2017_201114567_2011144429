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
public class Album {  
    
      private String nombre_album;
    private String genero;
    private String year;
    private ArrayList<Cancion> canciones;
    
    public Album()
    {
        
    }
    
    public String getNombre_Album(){
        return nombre_album;
    }
    
    public void setNombre_Album(String nombre){
        this.nombre_album=nombre;
    }
    
    public String getGenero(){
        return genero;
    }
    
    public void setGenero(String genero){
        this.genero=genero;
    }
    
    public String getYear(){
        return year;
    }
    
    public void setYear(String year){
        this.year=year;
    }
    
    public ArrayList<Cancion> getLista_Canciones(){
        return canciones;
    }
    
    public void setLista_Canciones(ArrayList<Cancion> lista){
        this.canciones=lista;
    }
    
}
