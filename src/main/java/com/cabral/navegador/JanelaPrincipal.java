package com.cabral.navegador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.equo.chromium.ChromiumBrowser;

public class JanelaPrincipal extends JFrame {

    private int tamanhoBarraNavegacao = 35;

    public Integer contadorURLPrevios = 0;

    JanelaPrincipal(){
        
        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/IconeNavegador.png"));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(500, 500));
        this.setLayout(new BorderLayout());
        this.setSize(1200, 600);
        this.setTitle("Cabral Navegador");
        this.setIconImage(icon.getImage());

        JPanel barraNavegacao = new JPanel();
        JPanel visualizadorSite = new JPanel();
        JPanel botoesNavegacao = new JPanel();
        JPanel botoesFavConfig = new JPanel();

        JTextField barraURL = new JTextField();
        barraURL.setBorder(null);
        barraURL.setBackground(Botoes.pegarCorBotao());

        barraNavegacao.setLayout(new BorderLayout());
        barraNavegacao.setPreferredSize(new Dimension(0, tamanhoBarraNavegacao));
        barraNavegacao.setBackground(Color.LIGHT_GRAY);

        visualizadorSite.setLayout(new BorderLayout());
        ChromiumBrowser browser = ChromiumBrowser.swing(visualizadorSite,BorderLayout.CENTER, TratamentoURL.pegarPaginaInicial());
        barraURL.addActionListener(e-> irParaNovoURL(barraURL.getText(), browser, barraURL));
        Botoes.definirIconeBotaoFavoritos(barraURL.getText());


        botoesNavegacao.setLayout(new BorderLayout());
        botoesNavegacao.setPreferredSize(new Dimension(Botoes.LARGURA * 3, tamanhoBarraNavegacao));

        botoesNavegacao.add(Botoes.pegarBotaoPrevio(tamanhoBarraNavegacao), BorderLayout.WEST);
        botoesNavegacao.add(Botoes.pegarBotaoProximo(tamanhoBarraNavegacao), BorderLayout.CENTER);
        botoesNavegacao.add(Botoes.pegarBotaoRecarregar(tamanhoBarraNavegacao), BorderLayout.EAST);

        botoesFavConfig.setLayout(new BorderLayout());
        botoesFavConfig.setPreferredSize(new Dimension(Botoes.LARGURA * 2, tamanhoBarraNavegacao));

        botoesFavConfig.add(Botoes.pegarBotaoFavoritos(tamanhoBarraNavegacao), BorderLayout.WEST);
        botoesFavConfig.add(Botoes.pegarBotaoConfig(tamanhoBarraNavegacao), BorderLayout.EAST);

        barraNavegacao.add(botoesNavegacao, BorderLayout.WEST);
        barraNavegacao.add(botoesFavConfig, BorderLayout.EAST);
        barraNavegacao.add(barraURL, BorderLayout.CENTER);

        this.add(barraNavegacao, BorderLayout.NORTH);
        this.add(visualizadorSite);
        this.setVisible(true);

        Botoes.browser = browser;
        Botoes.barraURL = barraURL;

        barraURL.setFont(new Font(getFont().getFamily(), Font.PLAIN, 18));
        barraURL.setText(TratamentoURL.pegarPaginaInicial());

    }

    private void irParaNovoURL(String url, ChromiumBrowser browser, JTextField barraURL){
        browser.setUrl(url);
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
        barraURL.setText(browser.getUrl());

        Botoes.definirIconeBotaoFavoritos(barraURL.getText());
    }

}
