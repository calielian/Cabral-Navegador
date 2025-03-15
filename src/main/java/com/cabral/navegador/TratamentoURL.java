package com.cabral.navegador;

import java.util.HashMap;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import com.equo.chromium.ChromiumBrowser;

public class TratamentoURL {

    public static HashMap<String, String> linkAba = new HashMap<>();
    public static String abaSelecionada;

    public static String pegarPaginaInicial(){

        try {
            // pega o caminho do arquivo de configuração
            Path caminhoArquivoConfig = Paths.get(System.getProperty("user.home"), Main.NOME_PASTA_CONFIG, Main.NOME_ARQUIVO_CONFIGURACAO);

            // cria uma lista de strings que contém todas as linhas lidas do arquivo
            List<String> linhas = Files.readAllLines(caminhoArquivoConfig);

            // itera pela lista e verifica se possui a linha relacionada a página inicial
            for (String linha : linhas){
                if (linha.contains("PAG_INICIAL=")){
                    return linha.split("PAG_INICIAL=")[1];
                }
            }

            return "https://www.google.com/";
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return "";
        }
    }

    public static boolean verificarSeEFavorito(String link){

        // verifica se a página é favorito ou não

        try {
            Path caminhoArquivoConfig = Paths.get(System.getProperty("user.home"), Main.NOME_PASTA_CONFIG, Main.NOME_ARQUIVO_FAVORITOS);
            List<String> linhas = Files.readAllLines(caminhoArquivoConfig);

            for (String linha : linhas){
                if (linha.contains(link)){
                    System.out.println("O link está favoritado");
                    Botoes.definirIconeBotaoFavoritos(true);
                    return true;
                }
            }

            System.out.println("O link não está favoritado");
            return false;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public static void adicionarFavorito(ChromiumBrowser browser){

        // adiciona a página aos favoritos
        if (!verificarSeEFavorito(browser.getUrl())){
            try {
                Path caminhoArquivoFav = Paths.get(System.getProperty("user.home"), Main.NOME_PASTA_CONFIG, Main.NOME_ARQUIVO_FAVORITOS);
                List<String> url = List.of(browser.getUrl());

                Files.write(caminhoArquivoFav, url, StandardOpenOption.APPEND);
                Botoes.definirIconeBotaoFavoritos(true);

                System.out.println("Adicionou o favorito com sucesso");
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }

    }

    public static String[] pegarFavoritos(){

        // retorna a lista de favoritos
        List<String> listaFavoritosArquivo;

        try {
            Path caminhoArquivoFav = Paths.get(System.getProperty("user.home"), Main.NOME_PASTA_CONFIG, Main.NOME_ARQUIVO_FAVORITOS);

            listaFavoritosArquivo = Files.readAllLines(caminhoArquivoFav);

            String[] listaFavoritos = new String[listaFavoritosArquivo.size()];

            for (int i = 0; i < listaFavoritosArquivo.size(); i++){
                listaFavoritos[i] = listaFavoritosArquivo.get(i);
            }

            System.out.println("Favoritos pegos com sucesso");
            return listaFavoritos;
        } catch (IOException e) {
            String[] listaFavoritos = {"Não foi possível ler os arquivo de favoritos"};
            System.err.println(e.getMessage());
            return listaFavoritos;
        }
    }

    public static boolean apagarFavorito(String link){

        // remove 1 favorito em específico
        List<String> listaFavoritosArquivo;
        List<String> listaFavoritos = List.of();
        try {
            Path caminhoArquivoFav = Paths.get(System.getProperty("user.home"), Main.NOME_PASTA_CONFIG, Main.NOME_ARQUIVO_FAVORITOS);

            listaFavoritosArquivo = Files.readAllLines(caminhoArquivoFav);

            for (String favorito : listaFavoritosArquivo){

                if (favorito == link){
                    continue;
                }

                listaFavoritos.add(favorito);
            }

            Files.write(caminhoArquivoFav, listaFavoritos, StandardOpenOption.WRITE);

            System.out.println("Favorito removido com sucesso!");
            return true;

        } catch (IOException e) {
            System.err.println("Não foi possível apagar o favorito\n" + e);
            return false;
        }

    }

}
