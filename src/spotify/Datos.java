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
public class Datos { 
    
     private ArrayList<Artista> artistas;
    
    public Datos()
    {
        
    }
    
    public ArrayList<Artista> getLista_Artistas(){
        return artistas;
    }
    
    public void setLista_Artistas(ArrayList<Artista> artista){
        this.artistas=artista;
    }
    
}
