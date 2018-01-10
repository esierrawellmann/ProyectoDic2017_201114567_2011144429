/*
 * Created by JFormDesigner on Fri Jan 05 18:13:40 CST 2018
 */

package com.edd.player;

import java.awt.event.*;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.event.*;
import com.edd.player.DTO.DTOLogin;
import com.edd.player.LoginDialog;
import com.fasterxml.jackson.databind.ObjectMapper;
import javazoom.jlgui.basicplayer.BasicPlayerException;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Walter Garcia
 */
public class Reproductor extends JFrame {

    Controles_Reproductor mi_reproductor = new Controles_Reproductor();
    JFileChooser fileChooser = new JFileChooser();
    File file;

    String user ="";

    public boolean reproduciendo= false;
    public String ruta_mp3 ="La Chalana.mp3";


    public Reproductor() {
        initComponents();

        DefaultListModel<DTOLogin.Data.Year> model = new DefaultListModel();
        list1.setModel(model);



        DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion> modelCanciones = new DefaultListModel();
        jListEnReproduccion.setModel(modelCanciones);





        LoginDialog dialog = new LoginDialog();
        dialog.setSize(400,200);
        dialog.setLocationRelativeTo(null);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                DTOLogin dto = dialog.getData();
                user = dialog.getUser() != null ? dialog.getUser():"";
                DefaultListModel<DTOLogin.Data.Year> model = (DefaultListModel<DTOLogin.Data.Year>)list1.getModel();
                if(dto != null){
                    for(com.edd.player.DTO.DTOLogin.Data.Year year : dto.data.getYears()){
                        model.addElement(year);
                    }
                }
            }
        });
        dialog.setVisible(true);
    }



    public static void main(String[] args){
        Reproductor ventana = new Reproductor();
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    }

    private void btnPlay_PauseActionPerformed(ActionEvent e) {
        // TODO add your code here
        String ruta_cancion = "";

        if (jListEnReproduccion.getModel().getSize() >=1) {

            //ruta_cancion = ((DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion>) jListEnReproduccion.getModel()).elementAt(index_lista).ruta;

            ruta_cancion = ((DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion>) jListEnReproduccion.getModel()).firstElement().ruta;
            /*long index = ((DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion>) jListEnReproduccion.getModel()).firstElement().getIndex();
            ((DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion>) jListEnReproduccion.getModel()).remove((int)index);*/

            file = new File(ruta_cancion);

            if (file.exists() && !file.isDirectory())
            {
                try {
                    AudioInputStream in = AudioSystem.getAudioInputStream(file);
                    mi_reproductor.control.open(in);
                    mi_reproductor.control.play();
                } catch (BasicPlayerException ex) {
                    Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (UnsupportedAudioFileException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            else
            {
                System.out.println("el archivo no existe");
            }
        }
    }
    //}

    private void btnNextActionPerformed(ActionEvent e) {
        // TODO add your code here
        /*if(index_lista<contador)
        {
            index_lista++;
        }*/

        if (jListEnReproduccion.getModel().getSize() >=1)
        {

            ((DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion>) jListEnReproduccion.getModel()).remove(0);
            try {
                HttpHelper.dequeue(user);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }

    private void btnPreviousActionPerformed(ActionEvent e) {
        // TODO add your code here
        try {
            mi_reproductor.control.stop();
        } catch (BasicPlayerException ex) {
            Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void btnSuffleActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void list1ValueChanged(ListSelectionEvent e) {
        DTOLogin.Data.Year year = (DTOLogin.Data.Year) list1.getSelectedValue();
        DefaultListModel<DTOLogin.Data.Year.Genere> modelGeneres = new DefaultListModel();
        jListGeneros.setModel(modelGeneres);
        if(year != null){
            for(com.edd.player.DTO.DTOLogin.Data.Year.Genere genere : year.getGeneros()){
                modelGeneres.addElement(genere);
            }
        }

    }

    private void jListGenerosValueChanged(ListSelectionEvent e) {
        DTOLogin.Data.Year.Genere genere = (DTOLogin.Data.Year.Genere) jListGeneros.getSelectedValue();
        DefaultListModel<DTOLogin.Data.Year.Genere.Artist> modelArtists = new DefaultListModel();
        jListArtistas.setModel(modelArtists);
        if(genere != null){
            button2.setText("Arbo B "+genere.genero+" ");
            for(com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist artist : genere.getArtistas()){
                modelArtists.addElement(artist);
            }
        }

    }

    private void jListArtistasValueChanged(ListSelectionEvent e) {
        // TODO add your code here
        DTOLogin.Data.Year.Genere.Artist artist = (DTOLogin.Data.Year.Genere.Artist) jListArtistas.getSelectedValue();
        DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album> modelAlbums = new DefaultListModel();
        jListAlbums.setModel(modelAlbums);

        if(artist != null){
            button3.setText("ABB "+artist.artista);
            for(com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album album : artist.getAlbums()){
                modelAlbums.addElement(album);
            }
        }
    }

    private void jListAlbumsValueChanged(ListSelectionEvent e) {
        DTOLogin.Data.Year.Genere.Artist.Album album = (DTOLogin.Data.Year.Genere.Artist.Album) jListAlbums.getSelectedValue();
        DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion> modelCanciones = new DefaultListModel();
        jListCanciones.setModel(modelCanciones);
        if(album != null){
            button3.setText("Lista "+album.nombre);
            for(com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album.Cancion cancion : album.getCanciones()){
                modelCanciones.addElement(cancion);
            }
        }
    }

    private void button1ActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void button1MouseReleased(MouseEvent e) {
        // TODO add your code here
        try{
            Graphviz graph = new Graphviz();
            graph.CrearGrafo(HttpHelper.getMatrix());
        }catch (Exception ex){
            System.out.print(ex.getMessage().toString());
        }
    }

    private void btnSuffleMouseReleased(MouseEvent e) {
        // TODO add your code here

        DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion> modelCanciones =  (DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion>)jListEnReproduccion.getModel();

        com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album.Cancion cancion = (com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album.Cancion)jListCanciones.getSelectedValue();

        if(cancion !=null){

            modelCanciones.addElement(cancion);
            try{

                HttpHelper.enqueue(cancion.year,cancion.genere,cancion.artist,cancion.album,cancion.nombre_cancion,user);
            }catch (Exception ex){
                System.out.print(ex.getMessage().toString());
            }
        }
    }

    private void button2MouseReleased(MouseEvent e) {
        // TODO add your code here
        com.edd.player.DTO.DTOLogin.Data.Year year = (com.edd.player.DTO.DTOLogin.Data.Year)list1.getSelectedValue();

        com.edd.player.DTO.DTOLogin.Data.Year.Genere genere = (com.edd.player.DTO.DTOLogin.Data.Year.Genere)jListGeneros.getSelectedValue();
        if(genere !=null && year!= null){

            try{
                Graphviz graph = new Graphviz();
                graph.CrearGrafo(HttpHelper.getBTree(year.getYear(),genere.genero));
            }catch (Exception ex){
                System.out.print(ex.getMessage().toString());
            }
        }
    }

    private void button3MouseReleased(MouseEvent e) {
        // TODO add your code here
        com.edd.player.DTO.DTOLogin.Data.Year year = (com.edd.player.DTO.DTOLogin.Data.Year)list1.getSelectedValue();

        com.edd.player.DTO.DTOLogin.Data.Year.Genere genere = (com.edd.player.DTO.DTOLogin.Data.Year.Genere)jListGeneros.getSelectedValue();
        com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist artist = (com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist)jListArtistas.getSelectedValue();

        if(genere !=null && year!= null && artist != null){

            try{
                Graphviz graph = new Graphviz();
                graph.CrearGrafo(HttpHelper.getABBTree(year.getYear(),genere.genero,artist.artista));
            }catch (Exception ex){
                System.out.print(ex.getMessage().toString());
            }
        }

    }

    private void button4MouseReleased(MouseEvent e) {
        // TODO add your code here
        com.edd.player.DTO.DTOLogin.Data.Year year = (com.edd.player.DTO.DTOLogin.Data.Year)list1.getSelectedValue();

        com.edd.player.DTO.DTOLogin.Data.Year.Genere genere = (com.edd.player.DTO.DTOLogin.Data.Year.Genere)jListGeneros.getSelectedValue();
        com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist artist = (com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist)jListArtistas.getSelectedValue();
        com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album album = (com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album)jListAlbums.getSelectedValue();

        if(genere !=null && year!= null && artist != null && album != null){

            try{
                Graphviz graph = new Graphviz();
                graph.CrearGrafo(HttpHelper.getSongList(year.getYear(),genere.genero,artist.artista,album.nombre));
            }catch (Exception ex){
                System.out.print(ex.getMessage().toString());
            }
        }
    }

    private void button7MouseReleased(MouseEvent e) {
        // TODO add your code here
        try{
            Graphviz graph = new Graphviz();
            graph.CrearGrafo(HttpHelper.getUserList());
        }catch (Exception ex){
            System.out.print(ex.getMessage().toString());
        }
    }

    private void button9MouseReleased(MouseEvent e) {
        // TODO add your code here
        String str = JOptionPane.showInputDialog(null,"Eliminar Usuario Con Nombre?");
        try{
            HttpHelper.deleteUser(str);
        }catch (Exception ex){
            System.out.print(ex.getMessage().toString());
        }

    }

    private void refreshLibrary(){
        //print result

        try{

            ObjectMapper objectMapper = new ObjectMapper();
            //objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
            DTOLogin.Data loginResponse = objectMapper.readValue(HttpHelper.getLibrary(), DTOLogin.Data.class);

            DefaultListModel<DTOLogin.Data.Year> model = new DefaultListModel<>();
            list1.setModel(model);
            if(loginResponse != null){
                for(com.edd.player.DTO.DTOLogin.Data.Year year : loginResponse.getYears()){
                    model.addElement(year);
                }
            }

        }catch (Exception e){
            System.out.print("Failed To Gather Library");
        }
    }

    private void button5MouseReleased(MouseEvent e) {
        // TODO add your code here
        com.edd.player.DTO.DTOLogin.Data.Year year = (com.edd.player.DTO.DTOLogin.Data.Year)list1.getSelectedValue();

        com.edd.player.DTO.DTOLogin.Data.Year.Genere genere = (com.edd.player.DTO.DTOLogin.Data.Year.Genere)jListGeneros.getSelectedValue();


        if(genere !=null && year!= null ){

            try{
                HttpHelper.deleteGenere(year.getYear().toString(),genere.genero);
                refreshLibrary();
            }catch (Exception ex){
                System.out.print(ex.getMessage().toString());
            }
        }
    }

    private void button6MouseReleased(MouseEvent e) {
        // TODO add your code here
        com.edd.player.DTO.DTOLogin.Data.Year year = (com.edd.player.DTO.DTOLogin.Data.Year)list1.getSelectedValue();

        com.edd.player.DTO.DTOLogin.Data.Year.Genere genere = (com.edd.player.DTO.DTOLogin.Data.Year.Genere)jListGeneros.getSelectedValue();
        com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist artist = (com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist)jListArtistas.getSelectedValue();

        if(genere !=null && year!= null && artist != null){

            try{
                HttpHelper.deleteArtist(year.getYear(),genere.genero,artist.artista);
                refreshLibrary();
            }catch (Exception ex){
                System.out.print(ex.getMessage().toString());
            }
        }

    }

    private void button8MouseReleased(MouseEvent e) {
        // TODO add your code here
        com.edd.player.DTO.DTOLogin.Data.Year year = (com.edd.player.DTO.DTOLogin.Data.Year)list1.getSelectedValue();

        com.edd.player.DTO.DTOLogin.Data.Year.Genere genere = (com.edd.player.DTO.DTOLogin.Data.Year.Genere)jListGeneros.getSelectedValue();
        com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist artist = (com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist)jListArtistas.getSelectedValue();
        com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album album = (com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album)jListAlbums.getSelectedValue();
        com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album.Cancion cancion = (com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album.Cancion)jListCanciones.getSelectedValue();

        if(genere !=null && year!= null && artist != null && album != null && cancion != null){

            try{
                HttpHelper.deleteSong(year.getYear(),genere.genero,artist.artista,album.nombre,cancion.nombre_cancion);
                refreshLibrary();
            }catch (Exception ex){
                System.out.print(ex.getMessage().toString());
            }
        }
    }

    private void button10MouseReleased(MouseEvent e) {
        // TODO add your code here

        DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion> model = new DefaultListModel<>();
        int size =  list1.getModel().getSize();
        com.edd.player.DTO.DTOLogin.Data.Year year = (com.edd.player.DTO.DTOLogin.Data.Year) list1.getSelectedValue();

        if(year !=null){
            for(int i = 0 ;i < size;i++){
                DTOLogin.Data.Year objYear = (DTOLogin.Data.Year) list1.getModel().getElementAt(i);
                for (DTOLogin.Data.Year.Genere genere : objYear.getGeneros()) {
                    for (DTOLogin.Data.Year.Genere.Artist artista : genere.getArtistas()) {
                        for (DTOLogin.Data.Year.Genere.Artist.Album album : artista.getAlbums()) {
                            for (DTOLogin.Data.Year.Genere.Artist.Album.Cancion cancion : album.getCanciones()) {
                                if(cancion.year.equals(year.getYear())){
                                    model.addElement(cancion);
                                }
                            }
                        }
                    }
                }

            }
            jListPlaylist.setModel(model);
        }
    }

    private void button11MouseReleased(MouseEvent e) {
        // TODO add your code here
        DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion> model = new DefaultListModel<>();

        com.edd.player.DTO.DTOLogin.Data.Year.Genere selectedGenere = (com.edd.player.DTO.DTOLogin.Data.Year.Genere) jListGeneros.getSelectedValue();

        if(selectedGenere !=null){
            int size =  list1.getModel().getSize();
            for(int i = 0 ;i < size;i++){
                DTOLogin.Data.Year objYear = (DTOLogin.Data.Year) list1.getModel().getElementAt(i);
                for (DTOLogin.Data.Year.Genere genere : objYear.getGeneros()) {
                    for (DTOLogin.Data.Year.Genere.Artist artista : genere.getArtistas()) {
                        for (DTOLogin.Data.Year.Genere.Artist.Album album : artista.getAlbums()) {
                            for (DTOLogin.Data.Year.Genere.Artist.Album.Cancion cancion : album.getCanciones()) {
                                if(cancion.genere.equals(selectedGenere.genero)){
                                    model.addElement(cancion);
                                }
                            }
                        }
                    }
                }
            }
            jListPlaylist.setModel(model);
        }

    }

    private void button12MouseReleased(MouseEvent e) {
        // TODO add your code here
        DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion> model = new DefaultListModel<>();

        com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist selectedArtist = (com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist) jListArtistas.getSelectedValue();

        if(selectedArtist !=null){
            int size =  list1.getModel().getSize();
            for(int i = 0 ;i < size;i++){
                DTOLogin.Data.Year objYear = (DTOLogin.Data.Year) list1.getModel().getElementAt(i);
                for (DTOLogin.Data.Year.Genere genere : objYear.getGeneros()) {
                    for (DTOLogin.Data.Year.Genere.Artist artista : genere.getArtistas()) {
                        for (DTOLogin.Data.Year.Genere.Artist.Album album : artista.getAlbums()) {
                            for (DTOLogin.Data.Year.Genere.Artist.Album.Cancion cancion : album.getCanciones()) {
                                if(cancion.artist.equals(selectedArtist.artista)){
                                    model.addElement(cancion);
                                }
                            }
                        }
                    }
                }
            }
            jListPlaylist.setModel(model);
        }
    }

    private void button13MouseReleased(MouseEvent e) {
        // TODO add your code here
        DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion> model = new DefaultListModel<>();


        com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album selectedAlbum = (com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album) jListAlbums.getSelectedValue();

        if(selectedAlbum !=null){
            int size =  list1.getModel().getSize();
            for(int i = 0 ;i < size;i++){
                DTOLogin.Data.Year objYear = (DTOLogin.Data.Year) list1.getModel().getElementAt(i);
                for (DTOLogin.Data.Year.Genere genere : objYear.getGeneros()) {
                    for (DTOLogin.Data.Year.Genere.Artist artista : genere.getArtistas()) {
                        for (DTOLogin.Data.Year.Genere.Artist.Album album : artista.getAlbums()) {
                            for (DTOLogin.Data.Year.Genere.Artist.Album.Cancion cancion : album.getCanciones()) {
                                if(cancion.album.equals(selectedAlbum.nombre)){
                                    model.addElement(cancion);
                                }
                            }
                        }
                    }
                }
            }
            jListPlaylist.setModel(model);
        }
    }

    private void button15MouseReleased(MouseEvent e) {
        // TODO add your code here

        DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion> model = new DefaultListModel<>();

        String keyword = textField1.getText().toString();


        int size =  list1.getModel().getSize();
        for(int i = 0 ;i < size;i++){
            DTOLogin.Data.Year objYear = (DTOLogin.Data.Year) list1.getModel().getElementAt(i);
            for (DTOLogin.Data.Year.Genere genere : objYear.getGeneros()) {
                for (DTOLogin.Data.Year.Genere.Artist artista : genere.getArtistas()) {
                    for (DTOLogin.Data.Year.Genere.Artist.Album album : artista.getAlbums()) {
                        for (DTOLogin.Data.Year.Genere.Artist.Album.Cancion cancion : album.getCanciones()) {
                            if(cancion.album.toLowerCase().contains(keyword.toLowerCase())){
                                model.addElement(cancion);
                            }
                        }
                    }
                }
            }
        }
        jListPlaylist.setModel(model);
    }

    private void button14MouseReleased(MouseEvent e) {
        // TODO add your code here
        DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion> model = (DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion>)jListPlaylist.getModel();

        DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion> jListEnReproduccionModel = (DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion>)jListEnReproduccion.getModel();



        for(int a=0;a<model.getSize();a++){
            DTOLogin.Data.Year.Genere.Artist.Album.Cancion cancion = (DTOLogin.Data.Year.Genere.Artist.Album.Cancion)model.getElementAt(a);
            jListEnReproduccionModel.addElement(cancion);
            try{

                HttpHelper.enqueue(cancion.year,cancion.genere,cancion.artist,cancion.album,cancion.nombre_cancion,user);
            }catch (Exception ex){
                System.out.print(ex.getMessage().toString());
            }
        }

        DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion> newModel = new DefaultListModel<DTOLogin.Data.Year.Genere.Artist.Album.Cancion>();
        jListPlaylist.setModel(newModel);

    }

    private void button16MouseReleased(MouseEvent e) {


        // TODO add your code here
        try{

            Graphviz graphviz = new Graphviz();
            graphviz.CrearGrafo(HttpHelper.showUserQueue(user));
        }catch (Exception ex){
            System.out.print(ex.getMessage().toString());
        }

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Renan Luna
        panel1 = new JPanel();
        lblCaratula = new JLabel();
        panel2 = new JPanel();
        btnPlay_Pause = new JButton();
        btnNext = new JButton();
        btnPrevious = new JButton();
        SliderProgreso = new JSlider();
        btnMute = new JButton();
        SliderVolume = new JSlider();
        lblTitulo = new JLabel();
        lblArtista = new JLabel();
        jLabelTranscurrido = new JLabel();
        jLabelTiempo = new JLabel();
        panel3 = new JPanel();
        scrollPane1 = new JScrollPane();
        list1 = new JList();
        scrollPane2 = new JScrollPane();
        jListGeneros = new JList();
        scrollPane3 = new JScrollPane();
        jListArtistas = new JList();
        scrollPane4 = new JScrollPane();
        jListAlbums = new JList();
        scrollPane5 = new JScrollPane();
        jListCanciones = new JList();
        scrollPane6 = new JScrollPane();
        jListPlaylist = new JList();
        scrollPane7 = new JScrollPane();
        jListEnReproduccion = new JList();
        button5 = new JButton();
        button6 = new JButton();
        button7 = new JButton();
        button9 = new JButton();
        button8 = new JButton();
        button10 = new JButton();
        button11 = new JButton();
        button12 = new JButton();
        button13 = new JButton();
        button14 = new JButton();
        button15 = new JButton();
        textField1 = new JTextField();
        button16 = new JButton();
        btnSuffle = new JButton();
        button1 = new JButton();
        button2 = new JButton();
        button3 = new JButton();
        button4 = new JButton();

        //======== this ========
        Container contentPane = getContentPane();
        contentPane.setLayout(null);

        //======== panel1 ========
        {
            panel1.setBackground(new Color(51, 51, 55));

            // JFormDesigner evaluation mark
            panel1.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), panel1.getBorder())); panel1.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            panel1.setLayout(null);
            panel1.add(lblCaratula);
            lblCaratula.setBounds(0, 400, 205, 195);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel1.getComponentCount(); i++) {
                    Rectangle bounds = panel1.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel1.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel1.setMinimumSize(preferredSize);
                panel1.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(panel1);
        panel1.setBounds(5, 5, 205, 595);

        //======== panel2 ========
        {
            panel2.setBackground(new Color(37, 38, 38));
            panel2.setLayout(null);

            //---- btnPlay_Pause ----
            btnPlay_Pause.setOpaque(false);
            btnPlay_Pause.setBackground(new Color(37, 38, 38));
            btnPlay_Pause.addActionListener(e -> {
			btnPlay_PauseActionPerformed(e);

		});
            panel2.add(btnPlay_Pause);
            btnPlay_Pause.setBounds(495, 10, 45, 45);

            //---- btnNext ----
            btnNext.setDefaultCapable(false);
            btnNext.setOpaque(false);
            btnNext.setBackground(new Color(37, 38, 38));
            btnNext.addActionListener(e -> btnNextActionPerformed(e));
            panel2.add(btnNext);
            btnNext.setBounds(555, 10, 45, 45);

            //---- btnPrevious ----
            btnPrevious.setOpaque(false);
            btnPrevious.setBackground(new Color(37, 38, 38));
            btnPrevious.addActionListener(e -> btnPreviousActionPerformed(e));
            panel2.add(btnPrevious);
            btnPrevious.setBounds(435, 10, 45, 45);

            //---- SliderProgreso ----
            SliderProgreso.setOpaque(false);
            SliderProgreso.setValue(0);
            panel2.add(SliderProgreso);
            SliderProgreso.setBounds(415, 75, 220, SliderProgreso.getPreferredSize().height);

            //---- btnMute ----
            btnMute.setOpaque(false);
            btnMute.setBackground(new Color(37, 38, 38));
            panel2.add(btnMute);
            btnMute.setBounds(790, 10, 45, 40);

            //---- SliderVolume ----
            SliderVolume.setValue(70);
            SliderVolume.setOpaque(false);
            panel2.add(SliderVolume);
            SliderVolume.setBounds(845, 30, 140, SliderVolume.getPreferredSize().height);
            panel2.add(lblTitulo);
            lblTitulo.setBounds(15, 15, 225, 25);
            panel2.add(lblArtista);
            lblArtista.setBounds(15, 70, 225, 25);
            panel2.add(jLabelTranscurrido);
            jLabelTranscurrido.setBounds(360, 70, 40, 15);
            panel2.add(jLabelTiempo);
            jLabelTiempo.setBounds(640, 75, 40, 16);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel2.getComponentCount(); i++) {
                    Rectangle bounds = panel2.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel2.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel2.setMinimumSize(preferredSize);
                panel2.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(panel2);
        panel2.setBounds(-5, 600, 1010, 115);

        //======== panel3 ========
        {
            panel3.setLayout(null);

            //======== scrollPane1 ========
            {

                //---- list1 ----
                list1.setBackground(new Color(23, 23, 23));
                list1.setForeground(Color.white);
                list1.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                list1.addListSelectionListener(e -> list1ValueChanged(e));
                scrollPane1.setViewportView(list1);
            }
            panel3.add(scrollPane1);
            scrollPane1.setBounds(0, 35, 75, 220);

            //======== scrollPane2 ========
            {

                //---- jListGeneros ----
                jListGeneros.setBackground(new Color(23, 23, 23));
                jListGeneros.setForeground(Color.white);
                jListGeneros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                jListGeneros.addListSelectionListener(e -> jListGenerosValueChanged(e));
                scrollPane2.setViewportView(jListGeneros);
            }
            panel3.add(scrollPane2);
            scrollPane2.setBounds(75, 35, 115, 220);

            //======== scrollPane3 ========
            {

                //---- jListArtistas ----
                jListArtistas.setBackground(new Color(23, 23, 23));
                jListArtistas.setForeground(Color.white);
                jListArtistas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                jListArtistas.addListSelectionListener(e -> jListArtistasValueChanged(e));
                scrollPane3.setViewportView(jListArtistas);
            }
            panel3.add(scrollPane3);
            scrollPane3.setBounds(190, 35, 200, 220);

            //======== scrollPane4 ========
            {

                //---- jListAlbums ----
                jListAlbums.setBackground(new Color(23, 23, 23));
                jListAlbums.setForeground(Color.white);
                jListAlbums.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                jListAlbums.addListSelectionListener(e -> jListAlbumsValueChanged(e));
                scrollPane4.setViewportView(jListAlbums);
            }
            panel3.add(scrollPane4);
            scrollPane4.setBounds(390, 35, 200, 220);

            //======== scrollPane5 ========
            {

                //---- jListCanciones ----
                jListCanciones.setBackground(new Color(23, 23, 23));
                jListCanciones.setForeground(Color.white);
                jListCanciones.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                scrollPane5.setViewportView(jListCanciones);
            }
            panel3.add(scrollPane5);
            scrollPane5.setBounds(590, 35, 200, 220);

            //======== scrollPane6 ========
            {

                //---- jListPlaylist ----
                jListPlaylist.setBackground(new Color(23, 23, 23));
                jListPlaylist.setForeground(Color.white);
                scrollPane6.setViewportView(jListPlaylist);
            }
            panel3.add(scrollPane6);
            scrollPane6.setBounds(0, 305, 355, 240);

            //======== scrollPane7 ========
            {

                //---- jListEnReproduccion ----
                jListEnReproduccion.setBackground(new Color(23, 23, 23));
                jListEnReproduccion.setForeground(Color.white);
                scrollPane7.setViewportView(jListEnReproduccion);
            }
            panel3.add(scrollPane7);
            scrollPane7.setBounds(430, 305, 360, 240);

            //---- button5 ----
            button5.setText("Eliminar Genero");
            button5.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    button5MouseReleased(e);
                }
            });
            panel3.add(button5);
            button5.setBounds(5, 0, 180, 25);

            //---- button6 ----
            button6.setText("Eliminar Artista");
            button6.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    button6MouseReleased(e);
                }
            });
            panel3.add(button6);
            button6.setBounds(190, 0, 180, 24);

            //---- button7 ----
            button7.setText("Usuarios");
            button7.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    button7MouseReleased(e);
                }
            });
            panel3.add(button7);
            button7.setBounds(575, 0, 100, 25);

            //---- button9 ----
            button9.setText("Eliminar Usuario");
            button9.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    button9MouseReleased(e);
                }
            });
            panel3.add(button9);
            button9.setBounds(680, 0, 100, 25);

            //---- button8 ----
            button8.setText("Eliminar Cancion");
            button8.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    button8MouseReleased(e);
                }
            });
            panel3.add(button8);
            button8.setBounds(380, 0, 185, 24);

            //---- button10 ----
            button10.setText("A\u00f1o");
            button10.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    button10MouseReleased(e);
                }
            });
            panel3.add(button10);
            button10.setBounds(new Rectangle(new Point(5, 260), button10.getPreferredSize()));

            //---- button11 ----
            button11.setText("Genero");
            button11.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    button11MouseReleased(e);
                }
            });
            panel3.add(button11);
            button11.setBounds(75, 260, 80, button11.getPreferredSize().height);

            //---- button12 ----
            button12.setText("Artista");
            button12.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    button12MouseReleased(e);
                }
            });
            panel3.add(button12);
            button12.setBounds(160, 260, 80, button12.getPreferredSize().height);

            //---- button13 ----
            button13.setText("Disco");
            button13.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    button13MouseReleased(e);
                }
            });
            panel3.add(button13);
            button13.setBounds(250, 260, 105, button13.getPreferredSize().height);

            //---- button14 ----
            button14.setText(">>");
            button14.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    button14MouseReleased(e);
                }
            });
            panel3.add(button14);
            button14.setBounds(new Rectangle(new Point(365, 405), button14.getPreferredSize()));

            //---- button15 ----
            button15.setText("Agregar A Lista");
            button15.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    button15MouseReleased(e);
                }
            });
            panel3.add(button15);
            button15.setBounds(430, 260, 163, button15.getPreferredSize().height);
            panel3.add(textField1);
            textField1.setBounds(600, 265, 190, textField1.getPreferredSize().height);

            //---- button16 ----
            button16.setText("Graph");
            button16.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    button16MouseReleased(e);
                }
            });
            panel3.add(button16);
            button16.setBounds(355, 455, 75, button16.getPreferredSize().height);

            { // compute preferred size
                Dimension preferredSize = new Dimension();
                for(int i = 0; i < panel3.getComponentCount(); i++) {
                    Rectangle bounds = panel3.getComponent(i).getBounds();
                    preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                    preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
                }
                Insets insets = panel3.getInsets();
                preferredSize.width += insets.right;
                preferredSize.height += insets.bottom;
                panel3.setMinimumSize(preferredSize);
                panel3.setPreferredSize(preferredSize);
            }
        }
        contentPane.add(panel3);
        panel3.setBounds(210, 50, 795, 550);

        //---- btnSuffle ----
        btnSuffle.setText("Encolar");
        btnSuffle.addActionListener(e -> {
			btnSuffleActionPerformed(e);
		});
        btnSuffle.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                btnSuffleMouseReleased(e);
            }
        });
        contentPane.add(btnSuffle);
        btnSuffle.setBounds(915, 10, 80, 25);

        //---- button1 ----
        button1.setText("Ver Matriz");
        button1.addActionListener(e -> button1ActionPerformed(e));
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button1MouseReleased(e);
            }
        });
        contentPane.add(button1);
        button1.setBounds(215, 10, 183, 25);

        //---- button2 ----
        button2.setText("Arbol B");
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button2MouseReleased(e);
            }
        });
        contentPane.add(button2);
        button2.setBounds(400, 10, 180, 25);

        //---- button3 ----
        button3.setText("ABB");
        button3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button3MouseReleased(e);
            }
        });
        contentPane.add(button3);
        button3.setBounds(585, 10, 195, 25);

        //---- button4 ----
        button4.setText("Canciones");
        button4.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                button4MouseReleased(e);
            }
        });
        contentPane.add(button4);
        button4.setBounds(780, 10, 130, 25);

        { // compute preferred size
            Dimension preferredSize = new Dimension();
            for(int i = 0; i < contentPane.getComponentCount(); i++) {
                Rectangle bounds = contentPane.getComponent(i).getBounds();
                preferredSize.width = Math.max(bounds.x + bounds.width, preferredSize.width);
                preferredSize.height = Math.max(bounds.y + bounds.height, preferredSize.height);
            }
            Insets insets = contentPane.getInsets();
            preferredSize.width += insets.right;
            preferredSize.height += insets.bottom;
            contentPane.setMinimumSize(preferredSize);
            contentPane.setPreferredSize(preferredSize);
        }
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Renan Luna
    private JPanel panel1;
    private JLabel lblCaratula;
    private JPanel panel2;
    private JButton btnPlay_Pause;
    private JButton btnNext;
    private JButton btnPrevious;
    private JSlider SliderProgreso;
    private JButton btnMute;
    private JSlider SliderVolume;
    private JLabel lblTitulo;
    private JLabel lblArtista;
    private JLabel jLabelTranscurrido;
    private JLabel jLabelTiempo;
    private JPanel panel3;
    private JScrollPane scrollPane1;
    private JList list1;
    private JScrollPane scrollPane2;
    private JList jListGeneros;
    private JScrollPane scrollPane3;
    private JList jListArtistas;
    private JScrollPane scrollPane4;
    private JList jListAlbums;
    private JScrollPane scrollPane5;
    private JList jListCanciones;
    private JScrollPane scrollPane6;
    private JList jListPlaylist;
    private JScrollPane scrollPane7;
    private JList jListEnReproduccion;
    private JButton button5;
    private JButton button6;
    private JButton button7;
    private JButton button9;
    private JButton button8;
    private JButton button10;
    private JButton button11;
    private JButton button12;
    private JButton button13;
    private JButton button14;
    private JButton button15;
    private JTextField textField1;
    private JButton button16;
    private JButton btnSuffle;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
