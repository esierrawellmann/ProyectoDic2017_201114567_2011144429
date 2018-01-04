/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.edd.player;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Renan
 */
public class Lectura_Json {

    static String path = "C:\\Users\\Renan\\Desktop\\prueba2.txt";
    
    public static void Crear_Archivo(String Json)throws IOException
    {
        
        String str_json = Json;
        String ruta = /*aqui va la ruta para el archivo*/path;
        File archivo = new File(ruta);
        BufferedWriter bw;

        bw = new BufferedWriter(new FileWriter(archivo));
        bw.write(str_json);
        bw.close();
    }
    
    public static void Leer_Json() throws IOException
    {
        //read json file data to String
        byte[] jsonData = Files.readAllBytes(Paths.get(path));
        //create ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();
        //convert json string to object
        Datos rep = objectMapper.readValue(jsonData, Datos.class);
        
        
        
        for(int i = 0; i<rep.getyears().size();i++)
        {
            System.out.println("Año:"+rep.getyears().get(i).getyear());
            
            for(int j = 0; j<rep.getyears().get(i).getgeneros().size();j++)
            {
                
                System.out.println("Genero:"+rep.getyears().get(i).getgeneros().get(j).getnombre());
                
                for(int k = 0; k<rep.getyears().get(i).getgeneros().get(j).getartistas().size();k++)
                {
                    System.out.println("Artista:"+rep.getyears().get(i).getgeneros().get(j).getartistas().get(k).getnombre());
                    
                    for(int x = 0; x<rep.getyears().get(i).getgeneros().get(j).getartistas().get(k).getdiscos().size();x++)
                    {
                        System.out.println("Album:"+rep.getyears().get(i).getgeneros().get(j).getartistas().get(k).getdiscos().get(x).getnombre());
                        
                        for(int y = 0; y<rep.getyears().get(i).getgeneros().get(j).getartistas().get(k).getdiscos().get(x).getcanciones().size();y++)
                        {
                            System.out.println("Index:"+rep.getyears().get(i).getgeneros().get(j).getartistas().get(k).getdiscos().get(x).getcanciones().get(y).getindex());
                            System.out.println("Nombre Cancion:"+rep.getyears().get(i).getgeneros().get(j).getartistas().get(k).getdiscos().get(x).getcanciones().get(y).getnombre());
                            System.out.println("Path"+rep.getyears().get(i).getgeneros().get(j).getartistas().get(k).getdiscos().get(x).getcanciones().get(y).getpath());
                
                        }
                    }
                }
            }
        }
    }
    
}
