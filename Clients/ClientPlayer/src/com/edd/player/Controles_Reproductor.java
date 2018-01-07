package com.edd.player;

import javazoom.jlgui.basicplayer.BasicController;
import javazoom.jlgui.basicplayer.BasicPlayer;
import javazoom.jlgui.basicplayer.BasicPlayerEvent;
import javazoom.jlgui.basicplayer.BasicPlayerListener;

import java.io.PrintStream;
import java.util.Map;

public class Controles_Reproductor implements BasicPlayerListener
{

    private PrintStream out = null;

    BasicPlayer player = new BasicPlayer( );

    public Controles_Reproductor()
    {

        //player.addBasicPlayerListener(this);//añadimos el "escuchador" de eventos

        //out = System.out;
    }

    BasicController control = (BasicController) player;//Controlador para player

    //Metodos sobreescritos:

    public void opened(Object stream, Map properties) {

        display("opened : " + properties.toString());
    }

    public void progress(int bytesread, long microseconds, byte[] pcmdata, Map properties) {

        display("progress : " + properties.toString());
    }

    public void stateUpdated(BasicPlayerEvent event) {
        display("stateUpdated : " + event.toString());

    }

    public void setController(BasicController controller) {
        display("setController : " + controller);
    }

    public void display(String msg) {
        if (out != null) {
            out.println(msg);
        }
    }
}