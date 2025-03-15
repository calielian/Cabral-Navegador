package com.cabral.navegador;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.equo.chromium.ChromiumBrowser;

public class Botoes {

    // cria os ícones dos botões
    private static ImageIcon botaoProximoIcon = new ImageIcon(Botoes.class.getResource("/assets/botaoProximo.png"));
    private static ImageIcon botaoPrevioIcon = new ImageIcon(Botoes.class.getResource("/assets/botaoPrevio.png"));
    private static ImageIcon naoFavorito = new ImageIcon(Botoes.class.getResource("/assets/naoFavorito.png"));
    private static ImageIcon favorito = new ImageIcon(Botoes.class.getResource("/assets/favorito.png"));
    private static ImageIcon botaoRecarregarIcon = new ImageIcon(Botoes.class.getResource("/assets/botaoRecarregar.png"));
    private static ImageIcon botaoExtraIcon = new ImageIcon(Botoes.class.getResource("/assets/botaoExtra.png"));
    private static ImageIcon botaoNovaAbaIcon = new ImageIcon(Botoes.class.getResource("/assets/adicionarAba.png"));
    private static ImageIcon botaoApagarAbaIcon = new ImageIcon(Botoes.class.getResource("/assets/apagarAba.png"));

    // cria os botões
    private static JButton botaoPrevio = new JButton();
    private static JButton botaoProximo = new JButton();
    private static JButton botaoFavoritos = new JButton();
    private static JButton botaoRecarregar = new JButton();
    private static JButton botaoExtras = new JButton();
    private static JButton botaoNovaAba = new JButton();
    private static JButton botaoApagarAba = new JButton();

    // cria variáveis e uma constante que são necessárias para o uso do programa
    public static ChromiumBrowser browser;
    public static JTextField barraURL;
    public static final int LARGURA = 50;
    public static JPanel painelAbas;

    // as funções abaixo configuram e entregam os botões
    public static JButton pegarBotaoPrevio(int altura){
        botaoPrevio.setPreferredSize(new Dimension(LARGURA, altura));
        botaoPrevio.setIcon(botaoPrevioIcon);
        botaoPrevio.setFocusable(false);
        botaoPrevio.addActionListener(e -> botaoSelecionado(e));

        return botaoPrevio;
    };

    public static JButton pegarBotaoProximo(int altura){
        botaoProximo.setPreferredSize(new Dimension(LARGURA, altura));
        botaoProximo.setIcon(botaoProximoIcon);
        botaoProximo.setFocusable(false);
        botaoProximo.addActionListener(e -> botaoSelecionado(e));

        return botaoProximo;
    };

    public static JButton pegarBotaoFavoritos(int altura){
        botaoFavoritos.setPreferredSize(new Dimension(LARGURA, altura));
        botaoFavoritos.setFocusable(false);
        botaoFavoritos.addActionListener(e -> botaoSelecionado(e));

        return botaoFavoritos;
    }

    public static JButton pegarBotaoRecarregar(int altura){
        botaoRecarregar.setPreferredSize(new Dimension(LARGURA, altura));
        botaoRecarregar.setIcon(botaoRecarregarIcon);
        botaoRecarregar.setFocusable(false);
        botaoRecarregar.addActionListener(e -> botaoSelecionado(e));

        return botaoRecarregar;
    }

    public static JButton pegarBotaoExtras(int altura){
        botaoExtras.setPreferredSize(new Dimension(LARGURA, altura));
        botaoExtras.setIcon(botaoExtraIcon);
        botaoExtras.setFocusable(false);

        return botaoExtras;
    }

    public static JButton pegarBotaoNovaAba(int altura){
        botaoNovaAba.setPreferredSize(new Dimension(LARGURA, altura));
        botaoNovaAba.setIcon(botaoNovaAbaIcon);
        botaoNovaAba.setFocusable(false);
        botaoNovaAba.addActionListener(e -> criarAba());

        return botaoNovaAba;
    }

    public static JButton pegarBotaoApagarAba(int altura){
        botaoApagarAba.setPreferredSize(new Dimension(LARGURA, altura));
        botaoApagarAba.setIcon(botaoApagarAbaIcon);
        botaoApagarAba.setFocusable(false);
        botaoApagarAba.addActionListener(e -> botaoSelecionado(e));

        return botaoApagarAba;
    }
    // as duas funções abaixo define o ícone do botão de favoritos
    public static void definirIconeBotaoFavoritos(String link){
        botaoFavoritos.setIcon(TratamentoURL.verificarSeEFavorito(link) ? favorito : naoFavorito);
    }

    public static void definirIconeBotaoFavoritos(boolean efavorito){
        botaoFavoritos.setIcon(efavorito ? favorito : naoFavorito);
    }

    // função de tratamento do clique nos botões
    private static void botaoSelecionado(ActionEvent evento){
        
        if (evento.getSource() == botaoProximo){

            browser.goForward();
            esperar();
            barraURL.setText(browser.getUrl());
            definirIconeBotaoFavoritos(barraURL.getText());

        } else if (evento.getSource() == botaoPrevio){

            browser.goBack();
            esperar();
            barraURL.setText(browser.getUrl());
            definirIconeBotaoFavoritos(barraURL.getText());

        } else if (evento.getSource() == botaoFavoritos){

            TratamentoURL.adicionarFavorito(browser);
            definirIconeBotaoFavoritos(true);

        } else if (evento.getSource() == botaoRecarregar){

            browser.reload();
            definirIconeBotaoFavoritos(barraURL.getText());

        } else if (evento.getSource() == botaoApagarAba){

            System.out.println("Remover");

        }
    }

    // essa função para o processo, para esperar
    private static void esperar(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.err.println(e);
        }
    }

    private static void criarAba(){
        JButton botao = new JButton(String.format("Aba %d", painelAbas.getComponentCount() + 1));

        TratamentoURL.linkAba.put(botao.getText(), TratamentoURL.pegarPaginaInicial());
        TratamentoURL.abaSelecionada = botao.getText().split("Aba ")[1];

        botao.addActionListener(e -> alterarAba(botao));

        painelAbas.add(botao);
        painelAbas.revalidate();
        painelAbas.repaint();
    }

    public static void alterarAba(JButton botao){

        TratamentoURL.linkAba.put(String.format("Aba %d", Integer.parseInt(TratamentoURL.abaSelecionada)), browser.getUrl());

        TratamentoURL.abaSelecionada = botao.getText().split("Aba ")[1];

        browser.setUrl(TratamentoURL.linkAba.get(botao.getText()));
        barraURL.setText(TratamentoURL.linkAba.get(botao.getText()));
    }
}
