package com.edd.player;

import com.edd.player.DTO.DTOLogin;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class App extends JFrame{
    private JButton testButton;
    private JPanel jpContent;
    private JList list1;
    private JList list2;
    private JList list3;
    private JList list4;
    private JList list5;
    private JPanel panel1;
    private JButton button1;
    DefaultListModel model;

    public DefaultListModel getModel() {
        return model;
    }

    public void setModel(DefaultListModel model) {
        this.model = model;
    }



    public App() {
        this.setContentPane(this.jpContent);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.pack();
        this.setVisible(true);


        panel1.setVisible(true);

        JFrame frame = new JFrame("App");






        DefaultListModel model = new DefaultListModel();
        list1.setModel(model);
        LoginDialog dialog = new LoginDialog();

        dialog.setSize(400,200);
        dialog.setLocationRelativeTo(null);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                DTOLogin dto = dialog.getData();
                for(com.edd.player.DTO.DTOLogin.Data.Year year : dto.data.getYears()){
                    App.this.getModel().addElement(year);
                }
            }
        });
        dialog.setVisible(true);
    }
    public static void main(String[] args){


      //  new App();

        Reproductor ventana = new Reproductor();

        ventana.setVisible(true);



    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
