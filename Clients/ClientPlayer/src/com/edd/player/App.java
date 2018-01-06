package com.edd.player;

import com.edd.player.DTO.DTOLogin;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;



public class App extends JFrame{
    private JButton testButton;
    private JPanel jpContent;
    private JList list1;
    private JList list2;
    private JList list3;
    private JList list4;
    private JList list5;
    private JPanel panel1 = new JPanel(null);
    private JButton button1;
    DefaultListModel model;




    public App( ) {



        this.setContentPane(this.jpContent);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.pack();
        this.setVisible(true);




        JFrame frame = new JFrame("App");






        DefaultListModel model = new DefaultListModel();
        list1.setModel(model);
        LoginDialog dialog = new LoginDialog();

        dialog.setSize(400,200);
        dialog.setLocationRelativeTo(null);

        DefaultListModel listModel1 = new DefaultListModel();
        list1.setModel(listModel1);
        DefaultListModel listModel2 = new DefaultListModel();
        list2.setModel(listModel2);
        DefaultListModel listModel3 = new DefaultListModel();
        list3.setModel(listModel3);
        DefaultListModel listModel4 = new DefaultListModel();
        list4.setModel(listModel4);
        DefaultListModel listModel5 = new DefaultListModel();
        list5.setModel(listModel5);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                DTOLogin dto = dialog.getData();
                for(com.edd.player.DTO.DTOLogin.Data.Year year : dto.data.getYears()){
                    App.this.getModel().addElement(year);

                    ((DefaultListModel)list1.getModel()).addElement(year);
                }
            }
        });
        dialog.setVisible(true);
        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                //com.edd.player.DTO.DTOLogin.Data.Year year = (com.edd.player.DTO.DTOLogin.Data.Year)e;

                com.edd.player.DTO.DTOLogin.Data.Year year = null;

                super.mouseClicked(e);
            }
        });
        list1.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {

                DTOLogin.Data.Year selectedYear = (DTOLogin.Data.Year) list1.getModel().getElementAt(list1.getSelectedIndex());

                //System.out.println(sel);
            }
        });
    }
    public static void main(String[] args){


        new App();



    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
