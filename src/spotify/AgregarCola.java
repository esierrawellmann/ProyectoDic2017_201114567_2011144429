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
public class AgregarCola {
    
     String agregaCanciones[];
     ArrayList<Cancion> ListaCanciones;

    public AgregarCola(String agregaCanciones[], ArrayList<Cancion> datos,JList<String> jListListaCanciones) {
        
        //System.out.println(RutaDirectorio);
       this.ListaCanciones = datos;
       
        agregaCanciones = new String[datos.size()];
        int x=0;
         for (Cancion cancion:ListaCanciones){
             		
             agregaCanciones[x]=cancion.getNombre_Cancion();	
             x++;      
         }      
         jListListaCanciones.setListData(agregaCanciones);
        
         this.agregaCanciones=agregaCanciones;
    }
  
    public String[] agregaGet(){
        return agregaCanciones;
    }
    
}
