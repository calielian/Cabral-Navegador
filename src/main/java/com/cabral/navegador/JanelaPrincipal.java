package com.cabral.navegador;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.equo.chromium.ChromiumBrowser;

public class JanelaPrincipal extends JFrame {

    private final int TAMANHO_BARRA_NAVEGACAO = 70;
    private final int TAMANHO_BOTOES = 35;

    // menu popup
    private JPopupMenu menu = new JPopupMenu();

    // botão para abrir o popup menu
    private JButton botaoExtras = Botoes.pegarBotaoExtras(TAMANHO_BOTOES);

    JanelaPrincipal(){

        // Inicia a janela principal do navegador
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
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
        JPanel botoesAba = new JPanel();
        JPanel gerenciamentoAbas = new JPanel();

        // cria os itens do menu popup
        JMenuItem configuracoes = new JMenuItem("Configurações");
        configuracoes.addActionListener(e -> menuConfig());

        JMenuItem favoritos = new JMenuItem("Favoritos");
        favoritos.addActionListener(e -> menuFavoritos());

        // configura o botão para a função de exibir o menu
        botaoExtras.addActionListener(e -> exibirPopupMenu());
        
        // cria e configura a barra de URL
        JTextField barraURL = new JTextField();

        // cria e configura o painel de abas e a primeira aba
        JPanel painelAba = new JPanel();
        painelAba.setLayout(new BoxLayout(painelAba, BoxLayout.X_AXIS));

        JButton botao = new JButton("Aba 1");

        TratamentoURL.linkAba.put("Aba 1", TratamentoURL.pagina_inicial);
        TratamentoURL.abaSelecionada = "1";

        botao.addActionListener(e -> Botoes.alterarAba(botao));
        botao.setName(botao.getText());

        painelAba.add(botao);
        Botoes.botoes[0] = botao;
        Botoes.mapaBotoes.put(botao.getName(), 1);

        // cria o painel de scroll
        JScrollPane abas = new JScrollPane(painelAba);
        abas.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        abas.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        abas.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 10));

        // configura a barra de navegação (onde estará os botões e a barra de URL)
        barraNavegacao.setLayout(new BorderLayout());
        barraNavegacao.setPreferredSize(new Dimension(0, TAMANHO_BARRA_NAVEGACAO));

        // define o visualizador do site (onde a página web será exibida), cria a instância do navegador e define a ação da barra de URL
        visualizadorSite.setLayout(new BorderLayout());
        ChromiumBrowser browser = ChromiumBrowser.swing(visualizadorSite,BorderLayout.CENTER, TratamentoURL.pagina_inicial);
        barraURL.addActionListener(e -> irParaNovoURL(barraURL.getText(), browser, barraURL));

        // define o painel onde estará os botões de navegação
        botoesNavegacao.setLayout(new BorderLayout());
        botoesNavegacao.setPreferredSize(new Dimension(Botoes.LARGURA * 3, TAMANHO_BOTOES));

        // define o painel onde estará os botões de faboritos e configurações
        botoesFavConfig.setLayout(new BorderLayout());
        botoesFavConfig.setPreferredSize(new Dimension(Botoes.LARGURA * 2, TAMANHO_BOTOES));

        // define o painel onde estára os botões de gerenciamento de aba
        botoesAba.setLayout(new BorderLayout());
        botoesAba.setPreferredSize(new Dimension(Botoes.LARGURA * 2, TAMANHO_BOTOES));

        // define o painel onde estará as abas
        gerenciamentoAbas.setLayout(new BorderLayout());
        gerenciamentoAbas.setPreferredSize(new Dimension(0, TAMANHO_BOTOES));

        // os comandos abaixo adicionam os botões e componentes em seus respectivos painéis
        botoesNavegacao.add(Botoes.pegarBotaoPrevio(TAMANHO_BOTOES), BorderLayout.WEST);
        botoesNavegacao.add(Botoes.pegarBotaoProximo(TAMANHO_BOTOES), BorderLayout.CENTER);
        botoesNavegacao.add(Botoes.pegarBotaoRecarregar(TAMANHO_BOTOES), BorderLayout.EAST);

        botoesFavConfig.add(Botoes.pegarBotaoFavoritos(TAMANHO_BOTOES), BorderLayout.WEST);
        botoesFavConfig.add(botaoExtras, BorderLayout.EAST);

        barraNavegacao.add(botoesNavegacao, BorderLayout.WEST);
        barraNavegacao.add(botoesFavConfig, BorderLayout.EAST);
        barraNavegacao.add(barraURL, BorderLayout.CENTER);
        barraNavegacao.add(gerenciamentoAbas, BorderLayout.SOUTH);

        botoesAba.add(Botoes.pegarBotaoNovaAba(TAMANHO_BOTOES), BorderLayout.WEST);
        botoesAba.add(Botoes.pegarBotaoApagarAba(TAMANHO_BOTOES), BorderLayout.EAST);

        gerenciamentoAbas.add(abas, BorderLayout.CENTER);
        gerenciamentoAbas.add(botoesAba, BorderLayout.EAST);

        menu.add(configuracoes);
        menu.add(favoritos);

        // deixa a janela principal visível
        this.add(barraNavegacao, BorderLayout.NORTH);
        this.add(visualizadorSite);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // entrega as instâncias da barra de URL, do navegador e das abas
        Botoes.browser = browser;
        Botoes.barraURL = barraURL;
        Botoes.painelAbas = painelAba;
        JanelaFavoritos.barraUrl = barraURL;
        JanelaFavoritos.browser = browser;

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
        TratamentoURL.linkAba.put("Aba " + TratamentoURL.abaSelecionada, browser.getUrl());

        Botoes.definirIconeBotaoFavoritos(barraURL.getText());
    }

    // abre o menu de configurações
    private void menuConfig(){
        new JanelaConfig();
    }

    // abre o nenu de favoritos
    private void menuFavoritos(){
        new JanelaFavoritos();
    }

    // função para exibir o menu
    private void exibirPopupMenu(){
        // Coordenadas relativas ao botão
        int coordenadaXRelativaBotao = 0; // é 0 porque em relação ao botão, ele está na posição 0
        int coordenadaYRelativaBotao = botaoExtras.getHeight();

        int coordenadaXAbsolutaBotao = botaoExtras.getLocationOnScreen().x; // pega a localização absoluta do botão na tela no eixo X (horizontal)

        int limite = this.getLocationOnScreen().x + this.getWidth(); // a localização absoluta da janela na tela no eixo X (horizontal) + a largura da janela faz com que se descubra qual o limite de onde o menu pode aparecer
        // sem a localização, não terá o ponto de referência de onde o menu deve ficar, fazendo com que respeite o tamanho da janela, mas não onde ela está

        if (coordenadaXAbsolutaBotao + menu.getPreferredSize().width > limite) {
            coordenadaXRelativaBotao = botaoExtras.getWidth() - menu.getPreferredSize().width; // subtrai a largura do botão com a largura do menu, fazendo com que o menu vá para a esquerda e não fique nenhuma parte pra fora
        }

        // Exibe o menu com tudo correto
        menu.show(botaoExtras, coordenadaXRelativaBotao, coordenadaYRelativaBotao);
    }
}
