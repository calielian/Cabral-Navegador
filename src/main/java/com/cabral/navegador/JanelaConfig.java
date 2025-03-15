package com.cabral.navegador;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class JanelaConfig extends JFrame {

    JanelaConfig(){
        // define o ícone da janela de configurações
        ImageIcon icon = new ImageIcon(getClass().getResource("/assets/IconeNavegador.png"));

        // inicia a janela de configurações
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(700, 400));
        this.setResizable(false);
        this.setTitle("Configurações");
        this.setIconImage(icon.getImage());

        // cria e configura o painel onde estará as configurações
        JPanel painelConfig = new JPanel();
        painelConfig.setLayout(new BoxLayout(painelConfig, BoxLayout.Y_AXIS));

        // cria e configura o label da configuração da página inicial
        JLabel paginaInicialLabel = new JLabel("Página inicial: ");

        // cria e configura o campo de texto da página inicial
        JTextField paginaInicialCampo = new JTextField();
        paginaInicialCampo.setMaximumSize(new Dimension(this.getWidth(), 20)); // tamanho máximo para garantir que fique na mesma linha
        paginaInicialCampo.addActionListener(e -> definirPaginaInicial(paginaInicialCampo));

        // cria e configura o painel onde estará as configurações relacionadas a página inicial
        JPanel paginaInicialPanel = new JPanel();
        paginaInicialPanel.setLayout(new BoxLayout(paginaInicialPanel, BoxLayout.X_AXIS));

        // cria e configura o label da configuração do tema
        JLabel temaLabel = new JLabel("Tema: ");

        // cria o grupo e botões de botões do tema
        ButtonGroup temaBotoes = new ButtonGroup();

        JRadioButton temaClaro = new JRadioButton("Claro");
        JRadioButton temaEscuro = new JRadioButton("Escuro");

        temaClaro.addActionListener(e -> definirTema(temaClaro));
        temaEscuro.addActionListener(e -> definirTema(temaEscuro));

        temaClaro.setSelected(true);
        temaBotoes.add(temaClaro);
        temaBotoes.add(temaEscuro);

        // cria e configura o painel onde estará as configurações relacionadas ao tema
        JPanel temaPanel = new JPanel();
        temaPanel.setLayout(new BoxLayout(temaPanel, BoxLayout.X_AXIS));

        // adiciona os componentes aos seus respectivos painéis
        paginaInicialPanel.add(paginaInicialLabel);
        paginaInicialPanel.add(paginaInicialCampo);

        temaPanel.add(temaLabel);
        temaPanel.add(temaClaro);
        temaPanel.add(temaEscuro);

        painelConfig.add(paginaInicialPanel);
        painelConfig.add(temaPanel);

        // deixa a janela de configurações visível
        this.add(painelConfig);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // função para definir a página inicial
    private void definirPaginaInicial(JTextField paginaInicialCampo){

        String urlDigitada = paginaInicialCampo.getText();

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

        paginaInicialCampo.setText(urlDigitada);

        // salva a página inicial
        try {
            Path caminhoArquivoConfig = Paths.get(System.getProperty("user.home"), Main.NOME_PASTA_CONFIG, Main.NOME_ARQUIVO_CONFIGURACAO);
            List<String> url = List.of("PAG_INICIAL=" + urlDigitada);

            List<String> arquivoConfig = Files.readAllLines(caminhoArquivoConfig);
            List<String> novoArquivoConfig = List.of();

            for (String linha : arquivoConfig){
                if (linha.startsWith("PAG_INICIAL=")) {
                    novoArquivoConfig.add(url.get(0));
                } else {
                    novoArquivoConfig.add(linha);
                }
            }

            if (!novoArquivoConfig.contains("PAG_INICIAL")){
                novoArquivoConfig.add(url.get(0));
            }

            Files.write(caminhoArquivoConfig, novoArquivoConfig);
        } catch (IOException e) {
            System.err.println("Não foi possível definir a página inicial\n" + e.getMessage());
        }

    }

    private void definirTema(JRadioButton temaEscolhido){
        switch (temaEscolhido.getText()) {
            case "Claro":
                Main.tema = "claro";
                JOptionPane.showMessageDialog(null, "Tema alterado, por favor feche e abra novamente para aplicar as alterações");
                break;
        
            case "Escuro":
                Main.tema = "escuro";
                JOptionPane.showMessageDialog(null, "Tema alterado, por favor feche e abra novamente para aplicar as alterações");
                break;
        }

        System.out.println("DHuisfhiudahgfsdgsgrrgf");

        try {
            Path caminhoArquivoConfig = Paths.get(System.getProperty("user.home"), Main.NOME_PASTA_CONFIG, Main.NOME_ARQUIVO_CONFIGURACAO);
            List<String> tema = List.of("TEMA=" + Main.tema);

            List<String> arquivoConfig = Files.readAllLines(caminhoArquivoConfig);
            List<String> novoArquivoConfig = List.of();

            for (String linha : arquivoConfig){
                if (linha.startsWith("TEMA=")) {
                    novoArquivoConfig.add(tema.get(0));
                } else {
                    novoArquivoConfig.add(linha);
                }
            }

            if (!novoArquivoConfig.contains("TEMA=")){
                novoArquivoConfig.add(tema.get(0));
            }

            Files.write(caminhoArquivoConfig, novoArquivoConfig);

        } catch (Exception e) {
            // TODO: handle exception
        }

    }

}
