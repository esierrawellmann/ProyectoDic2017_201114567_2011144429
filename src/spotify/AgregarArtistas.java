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
public class AgregarArtistas {

     String[] Artistas;
    ArrayList<Artista> ListaArtistas;
    
    
    public AgregarArtistas(String[] AgregaArtistas,ArrayList<Artista> Artistas,JList<String> jListListaArtista) {
        
         this.ListaArtistas = Artistas;
        AgregaArtistas = new String[Artistas.size()];
        
         int x=0;
         for (Artista artista:ListaArtistas){
             		
             AgregaArtistas[x]=artista.getNombre_Artista();	
             x++;      
         }      
         jListListaArtista.setListData(AgregaArtistas);
        
         this.Artistas=AgregaArtistas;
        
        
        
    }
    
    public String[] agregaGet(){
        return Artistas;
    }
}
