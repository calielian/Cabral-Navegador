package com.cabral.navegador;

import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.equo.chromium.ChromiumBrowser;
public class JanelaFavoritos extends JFrame{

    public static ChromiumBrowser browser;
    public static JTextField barraUrl;

    private JList<String> listaFavoritos = new JList<>(TratamentoURL.pegarFavoritos());

    JanelaFavoritos(){
        // inicia a janela
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(700, 600));
        this.setTitle("Favoritos");
        this.setIconImage(new ImageIcon(getClass().getResource("/assets/IconeNavegador.png")).getImage());
        this.setResizable(false);

        listaFavoritos.addListSelectionListener(e -> irParaFavorito());

        this.add(listaFavoritos);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void irParaFavorito(){
        Object[] opcoes = {"Sim", "Não"};

        int escolha = JOptionPane.showOptionDialog(
            null, // componente pai (null centraliza)
            String.format("Deseja selecionar '%s'?", listaFavoritos.getSelectedValue()), // conteúdo a mostrar
            "Confirmação", // título
            JOptionPane.YES_NO_OPTION, // tipo de opções
            JOptionPane.QUESTION_MESSAGE, // tipo de mensagem
            new ImageIcon(getClass().getResource("/assets/navegadorMini.png")), // ícone personalizado
            opcoes, // lista de opções
            opcoes[1] // opção padrão
            );

        switch (escolha) {
            case JOptionPane.YES_OPTION:
                browser.setUrl(listaFavoritos.getSelectedValue());
                barraUrl.setText(listaFavoritos.getSelectedValue());
                Botoes.definirIconeBotaoFavoritos(listaFavoritos.getSelectedValue());
                break;
        }
    }

}
