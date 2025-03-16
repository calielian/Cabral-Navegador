package com.cabral.navegador;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.equo.chromium.ChromiumBrowser;

public class Botoes {
    static String tema = "_" + Main.tema;

    // cria os ícones dos botões
    private static ImageIcon botaoProximoIcon = new ImageIcon(Botoes.class.getResource(String.format("/assets/botaoProximo%s.png", tema)));
    private static ImageIcon botaoPrevioIcon = new ImageIcon(Botoes.class.getResource(String.format("/assets/botaoPrevio%s.png", tema)));
    private static ImageIcon naoFavorito = new ImageIcon(Botoes.class.getResource(String.format("/assets/naoFavorito%s.png", tema)));
    private static ImageIcon favorito = new ImageIcon(Botoes.class.getResource(String.format("/assets/favorito%s.png", tema)));
    private static ImageIcon botaoRecarregarIcon = new ImageIcon(Botoes.class.getResource(String.format("/assets/botaoRecarregar%s.png", tema)));
    private static ImageIcon botaoExtraIcon = new ImageIcon(Botoes.class.getResource(String.format("/assets/botaoExtra%s.png", tema)));
    private static ImageIcon botaoNovaAbaIcon = new ImageIcon(Botoes.class.getResource(String.format("/assets/adicionarAba%s.png", tema)));
    private static ImageIcon botaoApagarAbaIcon = new ImageIcon(Botoes.class.getResource(String.format("/assets/apagarAba%s.png", tema)));

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

    public static JButton[] botoes = new JButton[100];
    public static HashMap<String, Integer> mapaBotoes = new HashMap<>();  

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
        botaoApagarAba.addActionListener(e -> apagarAba());

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

    // função que cria aba
    private static void criarAba(){

        // verifica se o limite de abas foi atingido
        if (painelAbas.getComponentCount() <= 100) {
            JButton botao = new JButton(String.format("Aba %d", painelAbas.getComponentCount() + 1)); // cria um botão

            TratamentoURL.linkAba.put(botao.getText(), TratamentoURL.pagina_inicial); // adiciona ele no mapa de link de abas
            TratamentoURL.abaSelecionada = botao.getText().split("Aba ")[1]; // define como aba selecionada

            botao.addActionListener(e -> alterarAba(botao));
            botao.setName(String.format("Aba %d", painelAbas.getComponentCount() + 1)); // define um nome (um HTML id não necessariamente único)
            mapaBotoes.put(botao.getName(), Integer.parseInt(TratamentoURL.abaSelecionada)); // adiciona ele num mapa de botões

            botoes[painelAbas.getComponentCount()] = botao;
            painelAbas.add(botao);
            painelAbas.revalidate();
            painelAbas.repaint();

            browser.setUrl(TratamentoURL.pagina_inicial);
            barraURL.setText(TratamentoURL.pegarPaginaInicial());
            TratamentoURL.linkAba.put(botao.getText(), TratamentoURL.pagina_inicial);
        }
    }

    // função que apaga 1 aba
    private static void apagarAba() {
        if (painelAbas.getComponentCount() > 1) {
            // Remove a aba do painel e do mapa
            int indiceRemovido = Integer.parseInt(TratamentoURL.abaSelecionada) - 1;
            painelAbas.remove(indiceRemovido);
            mapaBotoes.remove("Aba " + TratamentoURL.abaSelecionada);
    
            // Remove o botão do array botoes e reorganiza os elementos
            for (int i = indiceRemovido; i < botoes.length - 1; i++) {
                botoes[i] = botoes[i + 1];
            }
            botoes[botoes.length - 1] = null; // Limpa o último elemento
    
            // Reorganiza as abas restantes
            for (int i = 0; i < botoes.length; i++) {
                if (botoes[i] != null) {
                    // Atualiza o nome do botão e o valor no mapaBotoes
                    String novoNome = "Aba " + (i + 1);
                    botoes[i].setText(novoNome);
                    botoes[i].setName(novoNome);
    
                    // Atualiza o mapaBotoes
                    Integer valorAntigo = mapaBotoes.get(botoes[i].getName());
                    if (valorAntigo != null) {
                        mapaBotoes.put(novoNome, i + 1);
                        mapaBotoes.remove(botoes[i].getName());
                    }
                }
            }
    
            // Atualiza a aba selecionada para a aba anterior
            TratamentoURL.abaSelecionada = Integer.toString(Math.max(1, Integer.parseInt(TratamentoURL.abaSelecionada) - 1)); // Math.max porque se eu apagar a aba 1 o resultado seria abaSelecionada = 0, o que é impossível
    
            // Atualiza a URL do navegador para a aba selecionada
            String url = TratamentoURL.linkAba.get("Aba " + TratamentoURL.abaSelecionada);
            if (url != null) {
                browser.setUrl(url);
                barraURL.setText(url.startsWith("file:") ? "" : url);
            }
    
            painelAbas.revalidate();
            painelAbas.repaint();
        }
    }

    // função para alterar aba
    public static void alterarAba(JButton botao){

        TratamentoURL.linkAba.put(String.format("Aba %d", Integer.parseInt(TratamentoURL.abaSelecionada)), browser.getUrl());

        TratamentoURL.abaSelecionada = botao.getText().split("Aba ")[1];

        String link = TratamentoURL.linkAba.get(botao.getText());

        browser.setUrl(link);
        barraURL.setText((link.startsWith("file:")) ? "" : link);
    }
}
