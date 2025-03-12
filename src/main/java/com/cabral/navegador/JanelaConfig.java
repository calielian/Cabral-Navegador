package com.cabral.navegador;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JanelaConfig extends JFrame {

    JanelaConfig(){
        // define o ícone da janela de configurações
        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/IconeNavegador.png"));

        // inicia a janela de configurações
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(700, 400));
        this.setResizable(false);
        this.setTitle("Configurações");
        this.setIconImage(icon.getImage());

        // cria e configura o campo de texto da página inicial
        JTextField paginaInicial = new JTextField();
        paginaInicial.setBackground(Color.LIGHT_GRAY);
        paginaInicial.setPreferredSize(new Dimension(600, 18));
        paginaInicial.addActionListener(e -> definirPaginaInicial(paginaInicial));

        // cria e configura o campo de texto da página inicial
        JLabel paginaInicialLabel = new JLabel("Página inicial: ");

        // cria e configura o painel onde estará as configurações relacionadas a página inicial
        JPanel paginaInicialPanel = new JPanel();
        paginaInicialPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

        // adiciona os componentes ao painel
        paginaInicialPanel.add(paginaInicialLabel);
        paginaInicialPanel.add(paginaInicial);

        // deixa a janela de configurações visível
        this.add(paginaInicialPanel);
        this.setVisible(true);
    }

    private void definirPaginaInicial(JTextField paginaIncial){

        String urlDigitada = paginaIncial.getText();

        boolean comecaComHTTPS = false;

        // verficações se a página digitada é válida ou não
        if (!urlDigitada.endsWith("/")){
            urlDigitada += "/";
        }

        if (!urlDigitada.startsWith("https://")){
            urlDigitada = "https://" + urlDigitada;
            comecaComHTTPS = true;
        } else if (!comecaComHTTPS && !urlDigitada.startsWith("http://")) {
            urlDigitada = "http://" + urlDigitada;
        }

        paginaIncial.setText(urlDigitada);

        // salva a página inicial
        try {
            Path caminhoArquivoConfig = Paths.get(System.getProperty("user.home"), Main.NOME_PASTA_CONFIG, Main.NOME_ARQUIVO_CONFIGURACAO);
            List<String> url = List.of("PAG_INICIAL=" + urlDigitada);

            Files.write(caminhoArquivoConfig, url);
        } catch (IOException e) {
            System.err.println("Não foi possível definir a página inicial\n" + e.getMessage());
        }

    }

}
