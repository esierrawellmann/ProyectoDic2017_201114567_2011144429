/*
 * Created by JFormDesigner on Fri Jan 05 18:13:40 CST 2018
 */

package com.edd.player;

import java.awt.event.*;
import com.edd.player.App;
import com.edd.player.DTO.DTOLogin;
import com.edd.player.LoginDialog;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;
import javazoom.jlgui.basicplayer.*;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author Walter Garcia
 */
public class Reproductor extends JFrame {

    boolean estado;
    private boolean mute=false;
    private boolean bloquear=false;
    private boolean repitaCancion=false;
    private boolean siguiente=false;
    boolean SufflePlay;

    private float volumen=0.8f;
    private final BasicPlayer Audio = new BasicPlayer();
    FileNameExtensionFilter filtrado = new FileNameExtensionFilter("Solo Mp3","mp3");
    private String ruta = "C:\\";
    private  AudioFile audiofile = new AudioFile();

    private File archivo= null;

    private String agregaCanciones[]= new String[10];
    private String agregaArtistas[] = new String[10];
    private String agregaAlbums[] = new String[10];
    private String agregaPlaylist[] = new String[10];
    private String agregaCancionesCola[] = new String[10];

    DTOLogin  dto;


    private final ArrayList<com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album.Cancion> ListaReproduccion = new ArrayList<>();



    public Reproductor() {
        initComponents();

        ImageIcon imagen = new ImageIcon("src\\com\\edd\\player\\Imagenes\\play.png");
        Icon play = new ImageIcon(imagen.getImage().getScaledInstance(btnPrevious.getWidth(),btnPrevious.getHeight(), Image.SCALE_DEFAULT));
        btnPlay_Pause.setIcon(play);
        ImageIcon imagen2 = new ImageIcon("src\\com\\edd\\player\\Imagenes\\next.png");
        Icon next = new ImageIcon(imagen2.getImage().getScaledInstance(btnPrevious.getWidth(),btnPrevious.getHeight(), Image.SCALE_DEFAULT));
        btnNext.setIcon(next);

        ImageIcon imagen3 = new ImageIcon("src\\com\\edd\\player\\Imagenes\\previous.png");
        Icon previous = new ImageIcon(imagen3.getImage().getScaledInstance(btnPrevious.getWidth(),btnPrevious.getHeight(), Image.SCALE_DEFAULT));
        btnPrevious.setIcon(previous);

        ImageIcon imagen4 = new ImageIcon("src\\com\\edd\\player\\Imagenes\\volume.png");
        Icon Mute = new ImageIcon(imagen4.getImage().getScaledInstance(btnPrevious.getWidth(),btnPrevious.getHeight(), Image.SCALE_DEFAULT));
        btnMute.setIcon(Mute);

        ImageIcon imagen5 = new ImageIcon("src\\com\\edd\\player\\Imagenes\\switch_off.jpeg");
        Icon suffle = new ImageIcon(imagen5.getImage().getScaledInstance(btnSuffle.getWidth(),btnSuffle.getHeight(), Image.SCALE_DEFAULT));
        btnSuffle.setIcon(suffle);
        estado = false;
        SufflePlay = false;

       LlenarListas();
        SlidersChange();
        basic_playerlistener();
       /* jlistlistener();
        jListCancioneslistener();
        jListAlbumslistener();
        jListArtistaslistener();
        jListPlaylistlistener();*/

        SliderVolume.setEnabled(false);
        SliderProgreso.setEnabled(false);




        /*this.setContentPane(this.jpContent);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.pack();
        this.setVisible(true);*/




        JFrame frame = new JFrame("App");






        DefaultListModel model = new DefaultListModel();
        list1.setModel(model);
        LoginDialog dialog = new LoginDialog();

        dialog.setSize(400,200);
        dialog.setLocationRelativeTo(null);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                DTOLogin  dto = dialog.getData();

                for(com.edd.player.DTO.DTOLogin.Data.Year year : dto.data.getYears()){
                   // App.this.getModel().addElement(year);

                }
            }
        });
        dialog.setVisible(true);
    }

    private void basic_playerlistener() {
        Audio.addBasicPlayerListener(new BasicPlayerListener() {
            @Override//Este metodo se cumple cuando abrimos la cancion...
            public void opened(Object o, Map map) {
                //System.out.println(map);

                //LLamamos al metodo para que nos imprima el tiempo de duracion de la cancion....
                CalculoSegundero(map.get("duration").toString(), "Duracion: ", jLabelTiempo);



                SliderProgreso.setMaximum(Integer.parseInt(map.get("mp3.length.bytes").toString()));
                SliderProgreso.setMinimum(0);
            }

            @Override//Este metodo se cumple cuando la cancion esta en progreso....
            public void progress(int i, long l, byte[] bytes, Map propiedades) {

                // ElementosMap= propiedades;




                try{

                }catch(ArrayIndexOutOfBoundsException e){System.out.println("Error"+e);}

                //LLamamos al este metodo que nos calcula el tiempo trancurrido...
                CalculoSegundero(propiedades.get("mp3.position.microseconds").toString(), "Transcurrido: ", jLabelTranscurrido);

                Object bytesTranscurrido =  propiedades.get("mp3.position.byte");
                bytesTranscurrido= Integer.parseInt(bytesTranscurrido.toString());
                SliderProgreso.setValue((int)bytesTranscurrido);
            }

            @Override
            public void stateUpdated(BasicPlayerEvent bpe) {

                if (!bloquear){
                    if (Audio.getStatus()==2 & repitaCancion){
                        btnPlay_Pause.doClick();
                    }
                    if (jListEnReproduccion.getSelectedIndex()+1!=agregaCanciones.length){
                        if (Audio.getStatus()==2 & siguiente){
                            int pista = jListEnReproduccion.getAnchorSelectionIndex();
                            jListEnReproduccion.setSelectedIndex(pista+1);
                            repaint();
                            btnPlay_Pause.doClick();
                        }
                    }
                }
            }

            @Override
            public void setController(BasicController bc) {

            }


        });
    }

    private void LlenarListas() {

        for (com.edd.player.DTO.DTOLogin.Data.Year year: dto.data.years) {
            for (com.edd.player.DTO.DTOLogin.Data.Year.Genere genero: year.generos) {
               for ( com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist artista: genero.artistas){
                   for (com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album album: artista.albums) {
                       for (com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album.Cancion cancion: album.canciones  ) {

                           ListaReproduccion.add(cancion);
                       }
                   }
               }
            }
        }



        /*AgregarCanciones agregaartistas = new AgregarCanciones(agregaArtistas,datos.getLista_Artistas(),jListArtistas);
        agregaArtistas = agregaartistas.agregaGet();

        AgregarAlbums agregaalbums = new AgregarAlbums(agregaAlbums,ListaAlbum,jListAlbums);
        agregaAlbums = agregaalbums.agregaGet();*/

        AgregarCanciones agregacanciones = new AgregarCanciones(agregaCanciones,ListaReproduccion,jListCanciones);
        agregaCanciones = agregacanciones.agregaGet();

    }

    private void SlidersChange() {
        SliderProgreso.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    Audio.pause();
                } catch (BasicPlayerException ex) {
                    // Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                try {
                    SliderProgreso.setValue(SliderProgreso.getValue());
                    Audio.resume();
                    Audio.seek(SliderProgreso.getValue());
                    Audio.setGain(volumen);


                } catch (BasicPlayerException ex) {
                    // Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        SliderVolume.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {

                if (Audio.getStatus()!=-1){
                    try {
                        volumen = SliderVolume.getValue()/100f;
                        Audio.setGain(volumen);


                    } catch (BasicPlayerException ex) {
                        //Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });


    }

    private void CalculoSegundero(String milisegundos, String texto, JLabel label) {
        float horas,mint;
        float segundostotal =  Integer.parseInt(milisegundos)/1000000;//Almacenamos y pasamos a segundos los microsegundos obtenenidos.

        horas = (int)segundostotal/3600;		//Conversion de segundostotal a horas.
        mint = (int)segundostotal/60-horas *60;		//Conversion de segundostotal a minutos.
        segundostotal= segundostotal-mint*60-horas*3600;//Conversion de segundostotal a segundos.

        String secundero = (int) mint +":"+ (int)segundostotal;//Creamos una variable String para almacenar el tiempo total horas, minutos, segundos.
        label.setText(secundero);

    }

    private void btnPlay_PauseActionPerformed(ActionEvent e) {
        // TODO add your code here


        if (estado == false) {

            estado = true;
            ImageIcon imagen = new ImageIcon("src\\com\\edd\\player\\Imagenes\\pause.png");
            Icon play = new ImageIcon(imagen.getImage().getScaledInstance(btnPrevious.getWidth(),btnPrevious.getHeight(), Image.SCALE_DEFAULT));
            btnPlay_Pause.setIcon(play);

            if (Audio.getStatus()==1){
                try {
                    Audio.resume();
                } catch (BasicPlayerException ex) {
                    // Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                int indice = jListEnReproduccion.getSelectedIndex();

                if (ListaReproduccion!=null & Audio.getStatus()!=0 & indice!=-1){
                    //System.out.println(Audio.getSleepTime());
                    com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album.Cancion Cancion = ListaReproduccion.get(indice);

                    String nombre = Cancion.getNombre_cancion();
                    String NombreArtista = "";
                    for (com.edd.player.DTO.DTOLogin.Data.Year year: dto.data.years) {
                        for (com.edd.player.DTO.DTOLogin.Data.Year.Genere genero:year.generos ) {
                            for (com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist artista: genero.artistas) {
                                for (com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album album:artista.albums) {
                                    for (com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album.Cancion objeto:album.canciones) {
                                        if (objeto.getNombre_cancion().equals(nombre)) {
                                            NombreArtista = artista.getArtista();
                                        }
                                    }
                                }
                            }
                        }
                    }


                    //System.out.println(ReproduceCancion);
                    archivo = new File(Cancion.getRuta());

                    SliderVolume.setEnabled(true);
                    if (SufflePlay == false) {
                        SliderProgreso.setEnabled(true);
                    }



                    //CaratulaCancion(archivo.toString());
                    CaratulaCancion(Cancion.getRuta());
                    try {

                        audiofile = AudioFileIO.read(archivo);

                        Audio.open(new File(Cancion.getRuta()));
                        Audio.play();
                        Audio.setGain(volumen);

                        //int pista = jListCanciones.getAnchorSelectionIndex()+1;
                        // lblTitulo.setText(audiofile.getTag().getFirst(FieldKey.TITLE));
                        //lblArtista.setText(audiofile.getTag().getFirst(FieldKey.ARTIST));


                        lblTitulo.setText(Cancion.getNombre_cancion());
                        lblArtista.setText(NombreArtista);

                    } catch (InvalidAudioFrameException|ReadOnlyFileException |TagException |IOException |CannotReadException|BasicPlayerException ex) {
                        JOptionPane.showMessageDialog(this, ex + "\n  la informacion de imagen de la cancion excede el pixelado admitido por la libreria..."

                                ,"Error en las Id3Tag",1);


                        try {

                            Audio.stop();
                            com.edd.player.DTO.DTOLogin.Data.Year.Genere.Artist.Album.Cancion RutaCancion = ListaReproduccion.get(indice);
                            //String RutaCancion = datos.get(indice);
                            AudioFile file = AudioFileIO.read(new File(RutaCancion.getRuta()));
                            org.jaudiotagger.tag.Tag tager = file.getTag();
                            tager.deleteField(FieldKey.COVER_ART);
                            tager.deleteArtworkField();
                            AudioFileIO.write(file);
                            InputStream inputStream = new FileInputStream(ruta);
                            OutputStream outputStream =  new FileOutputStream("Audio.mp3");

                            byte[] buf = new byte[1024];
                            int len;

                            while ((len = inputStream.read(buf)) > 0) {
                                outputStream.write(buf, 0, len);
                            }
                            inputStream.close();
                            outputStream.close();


                            System.out.println();
                            System.out.println("El contenido de caratula mp3 es de masiado grande....Borrela!!!");

                        }
                        catch (IOException ex1) {/*System.out.println(ex1);*/}
                        catch (TagException ex1) {/*System.out.println(ex1);*/}
                        catch (ReadOnlyFileException ex1) {/*System.out.println(ex1);*/}
                        catch (InvalidAudioFrameException ex1) {/*System.out.println(ex1);*/}
                        catch (CannotWriteException ex1) {/*System.out.println(ex1);*/}
                        catch (CannotReadException ex1) {/*System.out.println(ex1);*/}
                        catch (BasicPlayerException ex1) {/*System.out.println(ex1);*/}



                    }
                }
            }

        } else if (estado == true) {
            estado = false;
            ImageIcon imagen = new ImageIcon("src\\com\\edd\\player\\Imagenes\\play.png");
            Icon play = new ImageIcon(imagen.getImage().getScaledInstance(btnPrevious.getWidth(),btnPrevious.getHeight(), Image.SCALE_DEFAULT));
            btnPlay_Pause.setIcon(play);

            if (Audio.getStatus()==0){
                try {
                    Audio.pause();

                } catch (BasicPlayerException ex) {
                    // Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }





    }

    public void CaratulaCancion(String rut){
        Image img = null;
        try {
            Mp3File Mp3A = new Mp3File(rut);
            if (Mp3A.hasId3v2Tag()){
                ID3v2 idTag = Mp3A.getId3v2Tag();
                byte[] datosImagen = idTag.getAlbumImage();
                img = ImageIO.read(new ByteArrayInputStream(datosImagen));
            }
            else {
                img = ImageIO.read(getClass().getResource("imagen/caratula.png"));
            }
        }
        catch (IllegalArgumentException | IOException ex1){System.out.printf("No Expecificas ruta"); }
        catch (NullPointerException e1) {System.out.println("No hay caratula en el mp3");

            try {
                img = ImageIO.read(getClass().getResource("imagen/caratula.png"));
            }
            catch (IOException e) {}
        }
        catch (UnsupportedTagException | InvalidDataException e) {}
        //Este codigo nos ayudara a reajustar la imagen a nuestro JLabel para que este se redimensione.....
        /*int ancho = img.getWidth(null);
        int alto = img.getHeight(null);
        if (ancho>390){
            ancho=390;
        }
        if (alto>270){
            alto=270;
        }*/
        img = img.getScaledInstance(lblCaratula.getWidth(), lblCaratula.getHeight(),0);
        lblCaratula.setIcon(new ImageIcon(img));
    }

    private void btnNextActionPerformed(ActionEvent e) {
        // TODO add your code here

        if (SufflePlay == true) {


            int tamaño = ListaReproduccion.size();
            Random aleatorio = new Random();
            int indice = aleatorio.nextInt(tamaño);
            if (jListEnReproduccion.getSelectedIndex()+1!=agregaCanciones.length){
                bloquear=true;
                jListEnReproduccion.setSelectedIndex(indice);
                Comprobacion(0);
                bloquear=false;
            }





        } else if (SufflePlay == false) {
            if (jListEnReproduccion.getSelectedIndex()+1!=agregaCanciones.length){
                bloquear=true;
                Comprobacion(1);
                bloquear=false;
            }
        }
    }

    private void Comprobacion(int opera) {

        //Audio.getStatus() valor -1 al empezar...
        //valor 0 cuando se reproduce..
        //valor 1 cuando esta pausado..
        //valor 2 cuando detienes la cancion...

        int indice = jListEnReproduccion.getSelectedIndex();
        if (ListaReproduccion!=null & indice!=-1 /*&  Audio.getStatus()==0 */){
            //El metodo getAnchotSelectionIndex obtenemos el numero de posicion en el que se encuentra el JList...
            int pista = jListEnReproduccion.getAnchorSelectionIndex();
            jListEnReproduccion.setSelectedIndex(pista+opera);
            try {
                Audio.stop();

            } catch (BasicPlayerException ex) {
                //Logger.getLogger(Reproductor.class.getName()).log(Level.SEVERE, null, ex);
            }

            btnPlay_Pause.doClick();
            btnPlay_Pause.doClick();

        }
    }

    private void btnPreviousActionPerformed(ActionEvent e) {

        // TODO add your code here



        if (jListEnReproduccion.getSelectedIndex()!=0){
            bloquear=true;
            Comprobacion(-1);
            bloquear=false;
        }
    }

    private void btnSuffleActionPerformed(ActionEvent e) {


        // TODO add your code here

    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Walter Garcia
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
        btnSuffle = new JButton();

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
            btnPlay_Pause.addActionListener(e -> btnPlay_PauseActionPerformed(e));
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
                scrollPane1.setViewportView(list1);
            }
            panel3.add(scrollPane1);
            scrollPane1.setBounds(0, 0, 75, 305);

            //======== scrollPane2 ========
            {

                //---- jListGeneros ----
                jListGeneros.setBackground(new Color(23, 23, 23));
                jListGeneros.setForeground(Color.white);
                scrollPane2.setViewportView(jListGeneros);
            }
            panel3.add(scrollPane2);
            scrollPane2.setBounds(75, 0, 115, 305);

            //======== scrollPane3 ========
            {

                //---- jListArtistas ----
                jListArtistas.setBackground(new Color(23, 23, 23));
                jListArtistas.setForeground(Color.white);
                scrollPane3.setViewportView(jListArtistas);
            }
            panel3.add(scrollPane3);
            scrollPane3.setBounds(190, 0, 200, 305);

            //======== scrollPane4 ========
            {

                //---- jListAlbums ----
                jListAlbums.setBackground(new Color(23, 23, 23));
                jListAlbums.setForeground(Color.white);
                scrollPane4.setViewportView(jListAlbums);
            }
            panel3.add(scrollPane4);
            scrollPane4.setBounds(390, 0, 200, 305);

            //======== scrollPane5 ========
            {

                //---- jListCanciones ----
                jListCanciones.setBackground(new Color(23, 23, 23));
                jListCanciones.setForeground(Color.white);
                scrollPane5.setViewportView(jListCanciones);
            }
            panel3.add(scrollPane5);
            scrollPane5.setBounds(590, 0, 200, 305);

            //======== scrollPane6 ========
            {

                //---- jListPlaylist ----
                jListPlaylist.setBackground(new Color(23, 23, 23));
                jListPlaylist.setForeground(Color.white);
                scrollPane6.setViewportView(jListPlaylist);
            }
            panel3.add(scrollPane6);
            scrollPane6.setBounds(0, 305, 390, 240);

            //======== scrollPane7 ========
            {

                //---- jListEnReproduccion ----
                jListEnReproduccion.setBackground(new Color(23, 23, 23));
                jListEnReproduccion.setForeground(Color.white);
                scrollPane7.setViewportView(jListEnReproduccion);
            }
            panel3.add(scrollPane7);
            scrollPane7.setBounds(395, 305, 395, 240);

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
        panel3.setBounds(210, 55, 795, 545);

        //---- btnSuffle ----
        btnSuffle.addActionListener(e -> btnSuffleActionPerformed(e));
        contentPane.add(btnSuffle);
        btnSuffle.setBounds(935, 25, 45, 25);

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
    // Generated using JFormDesigner Evaluation license - Walter Garcia
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
    private JButton btnSuffle;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
