/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spotify;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JList;

/**
 *
 * @author walteregv95
 */
public class AgregarCanciones {
   
     String agregaCanciones[];

    public AgregarCanciones(String agregaCanciones[],File archivo,ArrayList<String> datos,
            JList<String> jListListaCanciones) {
        File RutaDirectorio = archivo;
        //System.out.println(RutaDirectorio);
                
        agregaCanciones = RutaDirectorio.list();

        for (String objeto : agregaCanciones) {
            String extension = objeto.substring(objeto.length()-3,objeto.length());
            if (extension.equals("mp3")) {
                //System.out.println(objeto);
                datos.add(RutaDirectorio+"/"+objeto);
            }              
        }
        agregaCanciones = new String[datos.size()];
        int x=0;
         for (String anade:datos){
             File file = new File(anade);		
             agregaCanciones[x]=file.getName();	
             x++;      
         }      
         jListListaCanciones.setListData(agregaCanciones);
         this.agregaCanciones=agregaCanciones;
    }
  
    public String[] agregaGet(){
        return agregaCanciones;
    }
}
