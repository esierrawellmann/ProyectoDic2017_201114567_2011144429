/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spotify;

import java.util.ArrayList;
import javax.swing.JList;

/**
 *
 * @author walteregv95
 */
public class AgregarAlbums {

    String Albums[];
    ArrayList<Album> ListaAlbums;
    
    
    public AgregarAlbums(String[] AgregaAlbums,ArrayList<Album> Albums,JList<String> jListListaAlbums) {
        
        this.ListaAlbums = Albums;
        AgregaAlbums = new String[Albums.size()];
        
         int x=0;
         for (Album album:ListaAlbums){
             		
             AgregaAlbums[x]=album.getNombre_Album();	
             x++;      
         }      
         jListListaAlbums.setListData(AgregaAlbums);
        
         this.Albums=AgregaAlbums;
        
        
        
    }
    
    public String[] agregaGet(){
        return Albums;
    }
    
}
