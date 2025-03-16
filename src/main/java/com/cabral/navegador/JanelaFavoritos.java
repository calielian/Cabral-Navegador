package com.cabral.navegador;

import java.awt.Dimension;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import com.equo.chromium.ChromiumBrowser;

public class JanelaFavoritos extends JFrame{

    public static ChromiumBrowser browser;
    public static JTextField barraUrl;

    private DefaultListModel<String> listaFavoritosModel = new DefaultListModel<>();
    private JList<String> listaFavoritos = new JList<>(listaFavoritosModel);
    private JScrollPane painel = new JScrollPane(listaFavoritos);

    JanelaFavoritos(){
        // inicia a janela
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(new Dimension(700, 600));
        this.setTitle("Favoritos");
        this.setIconImage(new ImageIcon(getClass().getResource("/assets/IconeNavegador.png")).getImage());
        this.setResizable(false);

        // adiciona os favoritos a lista
        int i = 0;
        for (String favorito : TratamentoURL.pegarFavoritos()){
            listaFavoritosModel.add(i++,favorito);
        }

        listaFavoritos.addListSelectionListener(e -> irParaFavorito());

        painel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        painel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        this.add(painel);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    // função que manda o navegador ir para o favorito ou apagá-lo
    private void irParaFavorito(){
        Object[] opcoes = {"Ir para a página", "Apagar", "Nada"}; // lista de opções

        String favoritoSelecionado = listaFavoritos.getSelectedValue();

        // caso o valor for nulo, saia da função
        if (favoritoSelecionado == null) {
            return;
        }

        StringBuilder favorito = new StringBuilder();
        
        // adiciona uma nova linha a cada 60 caracteres
        for (int i = 0; i < favoritoSelecionado.length(); i += 60){
            favorito.append(favoritoSelecionado.substring(i, Math.min(i + 60, favoritoSelecionado.length())));

            if (i + 60 < favoritoSelecionado.length()) {
                favorito.append("\n");
            }
        }

        int escolha = JOptionPane.showOptionDialog( 
            null, // componente pai (null centraliza)
            String.format("O que deseja fazer com '%s'?", favorito.toString()), // conteúdo a mostrar
            "Confirmação", // título
            JOptionPane.YES_NO_CANCEL_OPTION, // tipo de opções
            JOptionPane.QUESTION_MESSAGE, // tipo de mensagem
            new ImageIcon(getClass().getResource("/assets/navegadorMini.png")), // ícone personalizado
            opcoes, // lista de opções
            opcoes[0] // opção padrão
            );

        switch (escolha) {
            case JOptionPane.YES_OPTION:
                browser.setUrl(favoritoSelecionado);
                barraUrl.setText(favoritoSelecionado);
                Botoes.definirIconeBotaoFavoritos(favoritoSelecionado);
                break;
            case JOptionPane.NO_OPTION:

                if (TratamentoURL.apagarFavorito(favoritoSelecionado)){
                    listaFavoritosModel.removeElement(favoritoSelecionado);
                }

                break;
        }
    }

}
