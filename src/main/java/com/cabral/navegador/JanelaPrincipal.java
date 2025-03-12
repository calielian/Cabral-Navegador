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

    private final int TAMANHO_BARRA_NAVEGACAO = 35;

    JanelaPrincipal(){

        // Inicia a janela principal do navegador
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(500, 500));
        this.setLayout(new BorderLayout());
        this.setSize(1200, 600);
        this.setTitle("Cabral Navegador");

        // define o ícone do aplicativo
        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/IconeNavegador.png"));
        this.setIconImage(icon.getImage());

        // cria os painéis
        JPanel barraNavegacao = new JPanel();
        JPanel visualizadorSite = new JPanel();
        JPanel botoesNavegacao = new JPanel();
        JPanel botoesFavConfig = new JPanel();
        
        // cria e configura a barra de URL
        JTextField barraURL = new JTextField();
        barraURL.setBorder(null);
        barraURL.setBackground(Botoes.pegarCorBotao());

        // configura a barra de navegação (onde estará os botões e a barra de URL)
        barraNavegacao.setLayout(new BorderLayout());
        barraNavegacao.setPreferredSize(new Dimension(0, TAMANHO_BARRA_NAVEGACAO));
        barraNavegacao.setBackground(Color.LIGHT_GRAY);

        // cria o visualizador do site (onde a página web será exibida), cria a instância do navegador e define a ação da barra de URL
        visualizadorSite.setLayout(new BorderLayout());
        ChromiumBrowser browser = ChromiumBrowser.swing(visualizadorSite,BorderLayout.CENTER, TratamentoURL.pegarPaginaInicial());
        barraURL.addActionListener(e -> irParaNovoURL(barraURL.getText(), browser, barraURL));

        // configura o painel onde estará os botões de navegação
        botoesNavegacao.setLayout(new BorderLayout());
        botoesNavegacao.setPreferredSize(new Dimension(Botoes.LARGURA * 3, TAMANHO_BARRA_NAVEGACAO));

        // define o painel onde estará os botões de faboritos e configurações
        botoesFavConfig.setLayout(new BorderLayout());
        botoesFavConfig.setPreferredSize(new Dimension(Botoes.LARGURA * 2, TAMANHO_BARRA_NAVEGACAO));

        // os comandos abaixo adicionam os botões e componentes em seus respectivos painéis
        botoesNavegacao.add(Botoes.pegarBotaoPrevio(TAMANHO_BARRA_NAVEGACAO), BorderLayout.WEST);
        botoesNavegacao.add(Botoes.pegarBotaoProximo(TAMANHO_BARRA_NAVEGACAO), BorderLayout.CENTER);
        botoesNavegacao.add(Botoes.pegarBotaoRecarregar(TAMANHO_BARRA_NAVEGACAO), BorderLayout.EAST);

        botoesFavConfig.add(Botoes.pegarBotaoFavoritos(TAMANHO_BARRA_NAVEGACAO), BorderLayout.WEST);
        botoesFavConfig.add(Botoes.pegarBotaoConfig(TAMANHO_BARRA_NAVEGACAO), BorderLayout.EAST);

        barraNavegacao.add(botoesNavegacao, BorderLayout.WEST);
        barraNavegacao.add(botoesFavConfig, BorderLayout.EAST);
        barraNavegacao.add(barraURL, BorderLayout.CENTER);

        // deixa a janela principal visível
        this.add(barraNavegacao, BorderLayout.NORTH);
        this.add(visualizadorSite);
        this.setVisible(true);

        // entrega as instâncias da barra de URL e do navegador pra classe "Botoes"
        Botoes.browser = browser;
        Botoes.barraURL = barraURL;

        // define a fonte da barra de URL para o tamanho 18, insere o URL da página inicial na barra e verifica se a página é favorita ou não
        barraURL.setFont(new Font(getFont().getFamily(), Font.PLAIN, 18));
        barraURL.setText(TratamentoURL.pegarPaginaInicial());
        Botoes.definirIconeBotaoFavoritos(barraURL.getText());

    }

    // a função abaixo faz com que ao clicar na tecla "Enter", vá para a URL digitada
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
