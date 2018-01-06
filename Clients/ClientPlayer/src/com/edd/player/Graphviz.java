package com.edd.player;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Graphviz {

    String path = "/home/erik/cancionesEDD/";
    String path_grafo = "/home/erik/cancionesEDD/";


    public void CrearGrafo(String Grafo)
    {
        try {

            Random randomGenerator = new Random();

            String str_grafo = Grafo;
            String graph_name ="graph"+randomGenerator.nextInt(999999999)+".dot";
            String ruta = path+graph_name;
            File archivo = new File(ruta);
            BufferedWriter bw;

            bw = new BufferedWriter(new FileWriter(archivo));
            bw.write(str_grafo);
            bw.close();

            String dotPath = "dot";

            String fileInputPath = ruta;
            String fileOutputPath = path_grafo +".jpg";

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

            String[] cmd2 = new String[2];
            cmd2[0] = "eog";
            cmd2[1] = fileOutputPath;
            rt.exec(cmd2);

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
        }

    }
}