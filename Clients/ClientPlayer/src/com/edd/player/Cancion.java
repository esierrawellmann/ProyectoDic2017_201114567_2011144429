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
    
    private String nombre;
    private String artista;
    private String album;
    private String genero;
    private String year;
    private String path;
    
    public Cancion(/*String Nombre, String Artista, String Album, String Genero, String Year, String Path*/){
        
        /*nombre=Nombre;
        artista=Artista;
        album=Album;
        genero=Genero;
        year=Year;
        path=Path;*/
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre=nombre;
    }
    
    public String getArtista(){
        return artista;
    }
    
    public void setArtista(String artista){
        this.artista=artista;
    }
    
    public String getAlbum(){
        return album;
    }
    
    public void setAlbum(String album){
        this.album=album;
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
    
    public String getPath(){
        return path;
    }
    
    public void setPath(String path){
        this.path=path;
    }
    
}
