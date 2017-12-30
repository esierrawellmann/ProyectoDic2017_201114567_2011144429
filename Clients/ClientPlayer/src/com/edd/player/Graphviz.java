package com.edd.player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Graphviz {

    public void CrearArchivo(String Grafo)throws IOException
    {
        String str_grafo = Grafo;
        String ruta = /*aqui va la ruta para el archivo*/"";
        File archivo = new File(ruta);
        BufferedWriter bw;

        bw = new BufferedWriter(new FileWriter(archivo));
        bw.write(str_grafo);
        bw.close();
    }

    public void CrearGrafo(/*se le pueden colocar como parametros el nombre del archivo*/)
    {
        try {

            String dotPath = "dot.exe";

            String fileInputPath = /*aqui se coloca el path del archivo que creamos*/"c:\\grafo1.txt";
            String fileOutputPath = /*aqui se coloca el nombre del grafo*/"c:\\grafo1.jpg";

            String tParam = "-Tjpg";
            String tOParam = "-o";

            String[] cmd = new String[5];
            cmd[0] = dotPath;
            cmd[1] = tParam;
            cmd[2] = fileInputPath;
            cmd[3] = tOParam;
            cmd[4] = fileOutputPath;

            Runtime rt = Runtime.getRuntime();

            rt.exec( cmd );

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }

    }
}