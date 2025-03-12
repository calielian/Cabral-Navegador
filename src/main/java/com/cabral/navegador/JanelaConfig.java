package com.cabral.navegador;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class JanelaConfig extends JFrame {

    JanelaConfig(){
        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/IconeNavegador.png"));

        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(700, 400));
        this.setResizable(false);
        this.setTitle("Configurações");
        this.setIconImage(icon.getImage());
        this.setVisible(true);
    }

}
