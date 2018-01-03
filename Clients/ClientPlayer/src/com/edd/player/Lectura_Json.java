/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edd.player;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 *
 * @author Renan
 */
public class Lectura_Json {
    
    public static void Leer_Json() throws IOException
    {
        //read json file data to String
        byte[] jsonData = Files.readAllBytes(Paths.get("Artista.txt"));
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        //convert json string to object
        Datos rep = objectMapper.readValue(jsonData, Datos.class);
        
        
        
        for(int i = 0; i<rep.getLista_Artistas().size();i++)
        {
            System.out.println("Artista:"+rep.getLista_Artistas().get(i).getNombre_Artista());
            
            for(int j = 0; j<rep.getLista_Artistas().get(i).getLista_Albumes().size();j++)
            {
                
                System.out.println(rep.getLista_Artistas().get(i).getLista_Albumes().get(j).getNombre_Album());
                System.out.println(rep.getLista_Artistas().get(i).getLista_Albumes().get(j).getGenero());
                System.out.println(rep.getLista_Artistas().get(i).getLista_Albumes().get(j).getYear());
                
                for(int k = 0; k<rep.getLista_Artistas().get(i).getLista_Albumes().get(j).getLista_Canciones().size();k++)
                {
                    System.out.println(rep.getLista_Artistas().get(i).getLista_Albumes().get(j).getLista_Canciones().get(k).getNombre_Cancion());
                    System.out.println(rep.getLista_Artistas().get(i).getLista_Albumes().get(j).getLista_Canciones().get(k).getPath());
                }
                
            }
        }
    }
    
}
