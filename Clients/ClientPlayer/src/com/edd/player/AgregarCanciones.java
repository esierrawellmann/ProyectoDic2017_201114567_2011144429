package com.edd.player;

import javax.swing.*;
import java.util.ArrayList;

public class AgregarCanciones {

    String agregaCanciones[];
    ArrayList<com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album.Cancion> ListaCanciones;

    public AgregarCanciones(String agregaCanciones[], ArrayList<com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album.Cancion> datos,JList<String> jListListaCanciones) {

        //System.out.println(RutaDirectorio);
        this.ListaCanciones = datos;

        agregaCanciones = new String[datos.size()];
        int x=0;
        for (com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album.Cancion cancion:ListaCanciones){

            agregaCanciones[x]=cancion.getNombre_cancion();
            x++;
        }
        jListListaCanciones.setListData(agregaCanciones);

        this.agregaCanciones=agregaCanciones;
    }

    public String[] agregaGet(){
        return agregaCanciones;
    }


}
