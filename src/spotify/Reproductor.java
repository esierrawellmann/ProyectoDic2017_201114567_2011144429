/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spotify;

import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.html.HTML.Tag;
import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerException;
import javazoom.jlgui.basicplayer.BasicPlayerListener;
import jdk.nashorn.internal.objects.NativeArray;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.CannotWriteException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;

/**
 *
 * @author walteregv95
 */
public class Reproductor extends javax.swing.JFrame {

    /**
     * Creates new form Reproductor
     */
     boolean estado;
      private boolean mute=false;
    private boolean bloquear=false;
    private boolean repitaCancion=false;
    private boolean siguiente=false;
    
    private float volumen=0.8f;
    
    private final BasicPlayer Audio = new BasicPlayer();
    FileNameExtensionFilter filtrado = new FileNameExtensionFilter("Solo Mp3","mp3");
    
    private String ruta = "C:\\Users\\walteregv95\\Music\\MUSIC... WALTER";
    private final JFileChooser abrirFile  = new JFileChooser(new File(ruta));
    
    private File archivo= null;
    private Tag tag;
    private  AudioFile audiofile = new AudioFile();
    
    private String agregaCanciones[]= new String[10];
    private String agregaArtistas[] = new String[10];
    private String agregaAlbums[] = new String[10]; 
    private String agregaPlaylist[] = new String[10];
    private String agregaCancionesCola[] = new String[10];
    
    private final ArrayList<Cancion> ListaReproduccion = new ArrayList<>();
    private final ArrayList<Cancion> Playlist = new ArrayList<>();
    //private final Datos datosNuevos = datos;
    Datos datos = new Datos();
    private final ArrayList<Artista> ListaArtista = new ArrayList<>();
    private final ArrayList<Album> ListaAlbum = new ArrayList<>();
    private final ArrayList<Cancion> ListaCancion = new ArrayList<>();
    
    
    
    
    public Reproductor() {
        initComponents();
         ImageIcon imagen = new ImageIcon("src\\imagenes\\play.png");
        Icon play = new ImageIcon(imagen.getImage().getScaledInstance(btnPrevious.getWidth(),btnPrevious.getHeight(), Image.SCALE_DEFAULT));
        btnPlay_Pause.setIcon(play);
        ImageIcon imagen2 = new ImageIcon("src\\imagenes\\next.png");
        Icon next = new ImageIcon(imagen2.getImage().getScaledInstance(btnPrevious.getWidth(),btnPrevious.getHeight(), Image.SCALE_DEFAULT));
        btnNext.setIcon(next);
        
        ImageIcon imagen3 = new ImageIcon("src\\imagenes\\previous.png");
        Icon previous = new ImageIcon(imagen3.getImage().getScaledInstance(btnPrevious.getWidth(),btnPrevious.getHeight(), Image.SCALE_DEFAULT));
        btnPrevious.setIcon(previous);
        
         ImageIcon imagen4 = new ImageIcon("src\\imagenes\\Volume.png");
        Icon Mute = new ImageIcon(imagen4.getImage().getScaledInstance(btnPrevious.getWidth(),btnPrevious.getHeight(), Image.SCALE_DEFAULT));
        btnMute.setIcon(Mute);
        estado = false; 
         
         //ACÁ COMIENZA EL EJEMPLO DE DATOS
        ArrayList<Artista> artistas = new ArrayList<>();
        Artista artista1 = new Artista();
        Album album1 = new Album();
        Cancion cancion1 = new Cancion();
        
        Cancion cancion2 = new Cancion();
       
        ArrayList<Cancion> canciones1 = new ArrayList<>();
        
        Album album2 = new Album();
        Cancion cancion3 = new Cancion();
        
        Cancion cancion4 = new Cancion();
        
        ArrayList<Cancion> canciones2 = new ArrayList<>();
        
        ArrayList<Album> albums1 = new ArrayList<>();
       
        Artista artista2 = new Artista();
        Album album3 = new Album();
        Cancion cancion5 = new Cancion();
        
        Cancion cancion6 = new Cancion();
        
        ArrayList<Cancion> canciones3 = new ArrayList<>();
        
        Album album4 = new Album();
        Cancion cancion7 = new Cancion();
        
        Cancion cancion8 = new Cancion();
        
        ArrayList<Cancion> canciones4 = new ArrayList<>();
        
        ArrayList<Album> albums2 = new ArrayList<>();
        
        
        cancion1.setNombre_Cancion("Papercut");
        cancion1.setPath("C:\\Users\\walteregv95\\Music\\MUSIC... WALTER\\Linkin Park\\(2000) Hybrid Theory\\01 Papercut.mp3");
        
         cancion2.setNombre_Cancion("In the End");
        cancion2.setPath("C:\\Users\\walteregv95\\Music\\MUSIC... WALTER\\Linkin Park\\(2000) Hybrid Theory\\08 In the End.mp3");
        
        canciones1.add(cancion1);
        canciones1.add(cancion2);
        album1.setGenero("Nu-Metal");
        album1.setNombre_Album("Hybrid Theory");
        album1.setYear("2000");
        album1.setLista_Canciones(canciones1);
        
        cancion3.setNombre_Cancion("Don't Stay");
        cancion3.setPath("C:\\Users\\walteregv95\\Music\\MUSIC... WALTER\\Linkin Park\\(2003) Meteora\\02 - Don't Stay.mp3");
        
        cancion3.setNombre_Cancion("Don't Stay");
        cancion3.setPath("C:\\Users\\walteregv95\\Music\\MUSIC... WALTER\\Linkin Park\\(2003) Meteora\\02 - Don't Stay.mp3");
        
        
        cancion4.setNombre_Cancion("Numb");
        cancion4.setPath("C:\\Users\\walteregv95\\Music\\MUSIC... WALTER\\Linkin Park\\(2003) Meteora\\13 - Numb.mp3");
        
        canciones2.add(cancion3);
        canciones2.add(cancion4);
        album2.setGenero("General Alternative");
        album2.setLista_Canciones(canciones2);
        album2.setNombre_Album("Meteora");
        album2.setYear("2003"); 
        
         albums1.add(album1);
        albums1.add(album2);
        artista1.setNombre_Artista("Linkin Park");
        artista1.setLista_Albumes(albums1); 
        
        
        cancion5.setNombre_Cancion("Alone Together");
        cancion5.setPath("C:\\Users\\walteregv95\\Music\\MUSIC... WALTER\\Fall out boy\\[2013] Save Rock and Roll\\03 Alone Together.mp3");
        
        cancion6.setNombre_Cancion("Miss Missing You");
        cancion6.setPath("C:\\Users\\walteregv95\\Music\\MUSIC... WALTER\\Fall out boy\\[2013] Save Rock and Roll\\07 Miss Missing You.mp3");
        
        canciones3.add(cancion6);
        canciones3.add(cancion5);
        album3.setGenero("Alternative");
        album3.setLista_Canciones(canciones3);
        album3.setNombre_Album("Save Rock and Roll");
        album3.setYear("2013"); 
        
        cancion7.setNombre_Cancion("Dance, Dance");
        cancion7.setPath("C:\\Users\\walteregv95\\Music\\MUSIC... WALTER\\Fall Out Boy   Dance Dance.mp3");
        
        cancion8.setNombre_Cancion("7 Minutes In Heaven (Atavan Halen)");
        cancion8.setPath("C:\\Users\\walteregv95\\Music\\MUSIC... WALTER\\Fall out boy\\[2005] From Under The Cork Tree\\07 - 7 Minutes In Heaven (Atavan Halen).mp3");
        
        canciones4.add(cancion7);
        canciones4.add(cancion8);
        album4.setGenero("Rock");
        album4.setLista_Canciones(canciones4);
        album4.setNombre_Album("From Under The Cork Tree");
        album4.setYear("2005");
        
        
        albums2.add(album3);
        albums2.add(album4);
        artista2.setLista_Albumes(albums2);
        artista2.setNombre_Artista("Fall Out Boy");
        artistas.add(artista1);
        artistas.add(artista2);
        
        datos.setLista_Artistas(artistas);
        
        //ACÁ TERMINA EL EJEMPLO DE DATOS
        
        LlenarListas();
        SlidersChange();
        basic_playerlistener();
        jlistlistener();
        jListCancioneslistener();
        jListAlbumslistener();
        jListArtistaslistener();
        jListPlaylistlistener();
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnPrevious = new javax.swing.JButton();
        btnPlay_Pause = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnMute = new javax.swing.JButton();
        jLabelTiempo = new javax.swing.JLabel();
        jLabelTranscurrido = new javax.swing.JLabel();
        lblTitulo = new javax.swing.JLabel();
        lblArtista = new javax.swing.JLabel();
        SliderProgreso = new javax.swing.JSlider();
        SliderVolume = new javax.swing.JSlider();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        lblCaratula = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        PanelArtistas = new javax.swing.JPanel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListArtistas = new javax.swing.JList<>();
        PanelAlbums = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListAlbums = new javax.swing.JList<>();
        PanelCanciones = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jListCanciones = new javax.swing.JList<>();
        PanelPlaylist = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        jListPlaylist = new javax.swing.JList<>();
        PanelEnReproduccion = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListEnReproduccion = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        jPanel1.setBackground(new java.awt.Color(37, 38, 38));

        btnPrevious.setBackground(new java.awt.Color(37, 38, 38));
        btnPrevious.setBorder(null);
        btnPrevious.setBorderPainted(false);
        btnPrevious.setContentAreaFilled(false);
        btnPrevious.setFocusPainted(false);
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        btnPlay_Pause.setBackground(new java.awt.Color(37, 38, 38));
        btnPlay_Pause.setBorder(null);
        btnPlay_Pause.setBorderPainted(false);
        btnPlay_Pause.setContentAreaFilled(false);
        btnPlay_Pause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPlay_PauseActionPerformed(evt);
            }
        });

        btnNext.setBackground(new java.awt.Color(37, 38, 38));
        btnNext.setBorder(null);
        btnNext.setBorderPainted(false);
        btnNext.setContentAreaFilled(false);
        btnNext.setFocusPainted(false);
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnMute.setBackground(new java.awt.Color(37, 38, 38));
        btnMute.setBorder(null);
        btnMute.setBorderPainted(false);
        btnMute.setContentAreaFilled(false);
        btnMute.setFocusPainted(false);

        jLabelTiempo.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabelTiempo.setForeground(new java.awt.Color(255, 255, 255));

        jLabelTranscurrido.setFont(new java.awt.Font("Century Gothic", 0, 11)); // NOI18N
        jLabelTranscurrido.setForeground(new java.awt.Color(255, 255, 255));

        lblTitulo.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(255, 255, 255));

        lblArtista.setFont(new java.awt.Font("Century Gothic", 0, 12)); // NOI18N
        lblArtista.setForeground(new java.awt.Color(255, 255, 255));

        SliderProgreso.setToolTipText("");
        SliderProgreso.setValue(0);
        SliderProgreso.setOpaque(false);

        SliderVolume.setForeground(new java.awt.Color(51, 153, 0));
        SliderVolume.setValue(80);
        SliderVolume.setOpaque(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblArtista, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                        .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnPlay_Pause, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(128, 128, 128)
                        .addComponent(btnMute, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SliderVolume, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabelTranscurrido, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(SliderProgreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabelTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(lblTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(21, 63, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btnPlay_Pause, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(btnPrevious, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(btnNext, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(btnMute, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                            .addComponent(SliderVolume, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblArtista, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabelTiempo, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SliderProgreso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabelTranscurrido, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );

        jPanel2.setBackground(new java.awt.Color(61, 59, 59));

        jButton1.setBackground(new java.awt.Color(15, 15, 15));
        jButton1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Agregar a la Playlist +");
        jButton1.setBorder(null);
        jButton1.setOpaque(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(15, 15, 15));
        jButton2.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 255, 255));
        jButton2.setText("Vaciar Cola de Reproduccion");
        jButton2.setBorder(null);
        jButton2.setOpaque(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(15, 15, 15));
        jButton3.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("Cargar Playlist");
        jButton3.setBorder(null);
        jButton3.setOpaque(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setBackground(new java.awt.Color(15, 15, 15));
        jButton4.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton4.setForeground(new java.awt.Color(255, 255, 255));
        jButton4.setText("Guardar Playlist");
        jButton4.setBorder(null);
        jButton4.setOpaque(false);
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(15, 15, 15));
        jButton5.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Quitar de la Playlist -");
        jButton5.setBorder(null);
        jButton5.setOpaque(false);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblCaratula, javax.swing.GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addComponent(jButton2)
                .addGap(18, 18, 18)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblCaratula, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6))
        );

        jPanel3.setBackground(new java.awt.Color(15, 15, 15));

        jLabel1.setFont(new java.awt.Font("Century Gothic", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Usuario");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(428, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(88, 88, 88))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addContainerGap(92, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(23, 23, 23));
        jPanel4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane4.setBackground(new java.awt.Color(52, 53, 53));

        jListArtistas.setBackground(new java.awt.Color(23, 23, 23));
        jScrollPane4.setViewportView(jListArtistas);

        javax.swing.GroupLayout PanelArtistasLayout = new javax.swing.GroupLayout(PanelArtistas);
        PanelArtistas.setLayout(PanelArtistasLayout);
        PanelArtistasLayout.setHorizontalGroup(
            PanelArtistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
        );
        PanelArtistasLayout.setVerticalGroup(
            PanelArtistasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelArtistasLayout.createSequentialGroup()
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Artistas", PanelArtistas);

        jScrollPane3.setBackground(new java.awt.Color(52, 53, 53));

        jListAlbums.setBackground(new java.awt.Color(23, 23, 23));
        jScrollPane3.setViewportView(jListAlbums);

        javax.swing.GroupLayout PanelAlbumsLayout = new javax.swing.GroupLayout(PanelAlbums);
        PanelAlbums.setLayout(PanelAlbumsLayout);
        PanelAlbumsLayout.setHorizontalGroup(
            PanelAlbumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
        );
        PanelAlbumsLayout.setVerticalGroup(
            PanelAlbumsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAlbumsLayout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Albums", PanelAlbums);

        jScrollPane2.setBackground(new java.awt.Color(52, 53, 53));

        jListCanciones.setBackground(new java.awt.Color(23, 23, 23));
        jScrollPane2.setViewportView(jListCanciones);

        javax.swing.GroupLayout PanelCancionesLayout = new javax.swing.GroupLayout(PanelCanciones);
        PanelCanciones.setLayout(PanelCancionesLayout);
        PanelCancionesLayout.setHorizontalGroup(
            PanelCancionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
        );
        PanelCancionesLayout.setVerticalGroup(
            PanelCancionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelCancionesLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 347, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Canciones", PanelCanciones);

        jScrollPane5.setBackground(new java.awt.Color(52, 53, 53));

        jListPlaylist.setBackground(new java.awt.Color(23, 23, 23));
        jScrollPane5.setViewportView(jListPlaylist);

        javax.swing.GroupLayout PanelPlaylistLayout = new javax.swing.GroupLayout(PanelPlaylist);
        PanelPlaylist.setLayout(PanelPlaylistLayout);
        PanelPlaylistLayout.setHorizontalGroup(
            PanelPlaylistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
        );
        PanelPlaylistLayout.setVerticalGroup(
            PanelPlaylistLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPlaylistLayout.createSequentialGroup()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Playlist", PanelPlaylist);

        jScrollPane1.setBackground(new java.awt.Color(52, 53, 53));

        jListEnReproduccion.setBackground(new java.awt.Color(23, 23, 23));
        jScrollPane1.setViewportView(jListEnReproduccion);

        javax.swing.GroupLayout PanelEnReproduccionLayout = new javax.swing.GroupLayout(PanelEnReproduccion);
        PanelEnReproduccion.setLayout(PanelEnReproduccionLayout);
        PanelEnReproduccionLayout.setHorizontalGroup(
            PanelEnReproduccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
        );
        PanelEnReproduccionLayout.setVerticalGroup(
            PanelEnReproduccionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelEnReproduccionLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("En Reproduccion", PanelEnReproduccion);

        jPanel4.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 660, 370));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, 0)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPlay_PauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPlay_PauseActionPerformed
        // TODO add your handling code here:
       
        if (estado == false) {
            
            estado = true;
             ImageIcon imagen = new ImageIcon("src\\imagenes\\pause.png");
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
           Cancion Cancion = ListaReproduccion.get(indice);
          
           String nombre = Cancion.getNombre_Cancion();
                    String NombreArtista = "";
                     for (Artista artista:ListaArtista) {
                         for (Album album:artista.getLista_Albumes()) {
                             for (Cancion objeto:album.getLista_Canciones()) {
                                 if (objeto.getNombre_Cancion().equals(nombre)) {
                                     NombreArtista = artista.getNombre_Artista();
                                 }
                             }
                         }
                     }
           //System.out.println(ReproduceCancion);
           archivo = new File(Cancion.getPath());
           
           SliderVolume.setEnabled(true);
           SliderProgreso.setEnabled(true);
           
          
          //CaratulaCancion(archivo.toString());
          CaratulaCancion(Cancion.getPath());
           try { 
                        
               audiofile = AudioFileIO.read(archivo); 
               
               Audio.open(new File(Cancion.getPath()));
               Audio.play();
               Audio.setGain(volumen);
               
               //int pista = jListCanciones.getAnchorSelectionIndex()+1;
              // lblTitulo.setText(audiofile.getTag().getFirst(FieldKey.TITLE));
           //lblArtista.setText(audiofile.getTag().getFirst(FieldKey.ARTIST));
           
           
           lblTitulo.setText(Cancion.getNombre_Cancion());
           lblArtista.setText(NombreArtista);
           
           } catch (InvalidAudioFrameException|ReadOnlyFileException |TagException |IOException |CannotReadException|BasicPlayerException ex) {
               JOptionPane.showMessageDialog(this, ex + "\n  la informacion de imagen de la cancion excede el pixelado admitido por la libreria..."
                                                     
                                                      ,"Error en las Id3Tag",1);
               

               try {
                   
                   Audio.stop();
                   Cancion RutaCancion = ListaReproduccion.get(indice);
                   //String RutaCancion = datos.get(indice);
                   AudioFile file = AudioFileIO.read(new File(RutaCancion.getPath()));
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
             ImageIcon imagen = new ImageIcon("src\\imagenes\\play.png");
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
 
         
        
        
        
    }//GEN-LAST:event_btnPlay_PauseActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here: 
        
        int indice = jListCanciones.getSelectedIndex();
        
        
        int opcion = JOptionPane.showConfirmDialog(null,"Quieres agregar esta canción a tu Playlist?","Agregar a la Playlist",JOptionPane.YES_NO_OPTION);
        
        if (opcion==0) {
            Playlist.add(ListaCancion.get(indice));
            
            if (Playlist!=null) {
                
                AgregarAPlaylist agregar = new AgregarAPlaylist(agregaPlaylist,Playlist,jListPlaylist);
           //agregaCanciones= agre.agregaGet();
           agregaPlaylist= agregar.agregaGet();
           if (Playlist!=null){
              jListPlaylist.setSelectedIndex(0);
             // archivo = new File(datos.get(0));
                  
           }    
                
            }
        }
        
       
     
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        
        
        if (jListEnReproduccion.getSelectedIndex()+1!=agregaCanciones.length){
            bloquear=true;
            Comprobacion(1);
            bloquear=false;
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        // TODO add your handling code here:
        
           
        if (jListEnReproduccion.getSelectedIndex()!=0){
            bloquear=true;
            Comprobacion(-1);
            bloquear=false;
        }
    }//GEN-LAST:event_btnPreviousActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        
        DefaultListModel model = new DefaultListModel();
        jListEnReproduccion.setModel(model);
        model.removeAllElements();
        ListaReproduccion.clear();
        
        
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

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
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Reproductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Reproductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Reproductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Reproductor.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Reproductor().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel PanelAlbums;
    private javax.swing.JPanel PanelArtistas;
    private javax.swing.JPanel PanelCanciones;
    private javax.swing.JPanel PanelEnReproduccion;
    private javax.swing.JPanel PanelPlaylist;
    private javax.swing.JSlider SliderProgreso;
    private javax.swing.JSlider SliderVolume;
    private javax.swing.JButton btnMute;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPlay_Pause;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelTiempo;
    private javax.swing.JLabel jLabelTranscurrido;
    private javax.swing.JList<String> jListAlbums;
    private javax.swing.JList<String> jListArtistas;
    private javax.swing.JList<String> jListCanciones;
    private javax.swing.JList<String> jListEnReproduccion;
    private javax.swing.JList<String> jListPlaylist;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblArtista;
    private javax.swing.JLabel lblCaratula;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables

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

    private void jlistlistener() {
       
        jListEnReproduccion.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(jListEnReproduccion.getAnchorSelectionIndex());
                
                int indice = jListEnReproduccion.getSelectedIndex();

                 if (indice!=-1){
                    //String ReproduceCancion = datos.get(indice);
                    
                    Cancion cancion = ListaReproduccion.get(indice);
                    String nombre = cancion.getNombre_Cancion();
                    String NombreArtista = "";
                     for (Artista artista:ListaArtista) {
                         for (Album album:artista.getLista_Albumes()) {
                             for (Cancion objeto:album.getLista_Canciones()) {
                                 if (objeto.getNombre_Cancion().equals(nombre)) {
                                     NombreArtista = artista.getNombre_Artista();
                                 }
                             }
                         }
                     }
                    File file = new File(cancion.getPath());
                    try {
                    audiofile = AudioFileIO.read(file);                   
                    //tag=audiofile.getTag();

                    } catch (CannotReadException | IOException | TagException | NullPointerException |
                        ReadOnlyFileException | InvalidAudioFrameException ex) {/*System.out.println("Error.. " + ex); */}
                    
                  
                   // lblTitulo.setText(audiofile.getTag().getFirst(FieldKey.TITLE));
                   // lblArtista.setText(audiofile.getTag().getFirst(FieldKey.ARTIST));
                   
                  lblTitulo.setText(cancion.getNombre_Cancion());
                  lblArtista.setText(NombreArtista);
                    CaratulaCancion(file.toString());
                    if (e.getClickCount()==2){
                        try {
                            bloquear=true;
                            Audio.stop();
                            bloquear=false;
                        } catch (BasicPlayerException ex) {/*System.out.println("Error... " + ex);*/}
                        btnPlay_Pause.doClick();
                        if (Audio.getStatus()==2){
                            btnPlay_Pause.doClick();
                            
                        }
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

    private void Comprobacion(int opera) {
        //Audio.getStatus() valor -1 al empezar... 
        //valor 0 cuando se reproduce..
        //valor 1 cuando esta pausado..
        //valor 2 cuando detienes la cancion...
        
        int indice = jListEnReproduccion.getSelectedIndex();        
        if (ListaReproduccion!=null & indice!=-1 & Audio.getStatus()==0){
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

    private void LlenarListas() {
        
       
        
        for (Artista artista : datos.getLista_Artistas()) {
            
            ListaArtista.add(artista);
            
             for (Album album : artista.getLista_Albumes()) {
                ListaAlbum.add(album);
                
                 for (Cancion cancion : album.getLista_Canciones()) {
                     
                     ListaCancion.add(cancion);
                 }
            }
          
        }  
        
         AgregarArtistas agregaartistas = new AgregarArtistas(agregaArtistas,datos.getLista_Artistas(),jListArtistas);
        agregaArtistas = agregaartistas.agregaGet();
        
        AgregarAlbums agregaalbums = new AgregarAlbums(agregaAlbums,ListaAlbum,jListAlbums);
        agregaAlbums = agregaalbums.agregaGet();
        
        AgregarCanciones_Objeto agregacanciones = new AgregarCanciones_Objeto(agregaCanciones,ListaCancion,jListCanciones);
        agregaCanciones = agregacanciones.agregaGet();
        
        
    }

    private void jListCancioneslistener() {
      jListCanciones.addMouseListener(new MouseAdapter() {
         @Override
            public void mouseClicked(MouseEvent e) {
                
             
                
                    if (e.getClickCount()==2){
                      int opt = JOptionPane.showConfirmDialog(null, "Quieres agregar la cancion a cola?","Agregar",JOptionPane.YES_NO_OPTION);
                      
                        if (opt==0) {
                            
                            int indice = jListCanciones.getSelectedIndex();
                            ListaReproduccion.add(ListaCancion.get(indice));
                            AgregarACola();
                        }
                    }
                 
            }  
        
        
});
      
    }

    private void jListAlbumslistener() {
       jListAlbums.addMouseListener(new MouseAdapter() {
         @Override
            public void mouseClicked(MouseEvent e) {
                
                    if (e.getClickCount()==2){
                      int opt = JOptionPane.showConfirmDialog(null, "Quieres agregar todo el Album a la cola?","Agregar Album",JOptionPane.YES_NO_OPTION);
                      
                        if (opt==0) {
                            
                            int indice = jListAlbums.getSelectedIndex();
                            
                           Album album = ListaAlbum.get(indice);
                            for (Cancion cancion :album.getLista_Canciones()) {
                                ListaReproduccion.add(cancion);
                            }
                            AgregarACola();
                            
                        }
                    }
                 
            }  
        
        
});
    }

    private void jListArtistaslistener() {
       jListArtistas.addMouseListener(new MouseAdapter() {
         @Override
            public void mouseClicked(MouseEvent e) {
                
                    if (e.getClickCount()==2){
                      int opt = JOptionPane.showConfirmDialog(null, "Quieres agregar todas las canciones del Artista a la cola?","Agregar Artista",JOptionPane.YES_NO_OPTION);
                      
                        if (opt==0) {
                            
                            int indice = jListArtistas.getSelectedIndex();
                           Artista artista = ListaArtista.get(indice);
                           
                            for (Album album:artista.getLista_Albumes()) {
                                for (Cancion cancion:album.getLista_Canciones()) {
                                    ListaReproduccion.add(cancion);
                                }
                            }
                            
                            AgregarACola();
                        }
                    }
                 
            }  

           
        
        
});
    }  
    
    private void AgregarACola() {
        
           
         
       /* abrirFile.setDialogTitle("Directorio Agregar Canciones");
        abrirFile.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);//Accederemos solamente a directorios.
        if (abrirFile.showOpenDialog(jPanel1)==0){
            ruta = abrirFile.getSelectedFile().toString();
                       
        } 
        */
        
        if (ListaReproduccion!=null & Audio.getStatus()!=1){
            //archivo = new File(ruta);
            
           //AgregarCanciones agre = new AgregarCanciones(agregaCanciones, archivo, datos, jListCanciones);   
           //AgregarCanciones_Objeto agregar = new AgregarCanciones_Objeto(agregaCanciones,canciones,jListCanciones);
           
           AgregarCola agregar = new AgregarCola(agregaCancionesCola,ListaReproduccion,jListEnReproduccion);
           //agregaCanciones= agre.agregaGet();
           agregaCanciones= agregar.agregaGet();
           if (ListaReproduccion!=null){
              jListCanciones.setSelectedIndex(0);
             // archivo = new File(datos.get(0));
                  
           }                    
        }     
        
       
              
           }

    private void jListPlaylistlistener() {
        
         jListPlaylist.addMouseListener(new MouseAdapter() {
         @Override
            public void mouseClicked(MouseEvent e) {
                
                    if (e.getClickCount()==2){
                      int opt = JOptionPane.showConfirmDialog(null, "Quieres agregar tu Playlist a la cola?","Agregar Playlist",JOptionPane.YES_NO_OPTION);
                      
                        if (opt==0) {
                           
                            
                            for (Cancion cancion : Playlist) {
                                ListaReproduccion.add(cancion);
                            }
                            AgregarACola();
                        }
                    }
                 
            }  

           
        
        
});
        
    }
}
