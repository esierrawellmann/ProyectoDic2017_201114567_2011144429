package com.edd.player;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class App {
    private JButton testButton;
    private JPanel jpContent;

    public App() {
        testButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Hello!");
            }
        });
    }
    public static void main(String[] args){
        JFrame frame = new JFrame("App");
        frame.setContentPane(new App().jpContent);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
