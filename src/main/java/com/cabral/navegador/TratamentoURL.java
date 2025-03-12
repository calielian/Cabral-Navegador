package com.cabral.navegador;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;

import com.equo.chromium.ChromiumBrowser;

public class TratamentoURL {

    public static String pegarPaginaInicial(){
        try (BufferedReader leitor = new BufferedReader(new InputStreamReader(TratamentoURL.class.getResourceAsStream("/assets/home.txt")))) {
            return leitor.readLine();
        } catch (Exception e) {
            return "";
        }
    }

    public static boolean verificarSeEFavorito(String link){

        System.out.println(link);

        try (BufferedReader leitor = new BufferedReader(new FileReader("/home/ian/Downloads/favorito.txt"))) {

            String linha;

            while ((linha = leitor.readLine()) != null) {
                System.out.print(linha);
                System.out.print(link);
                if (linha.equals(link)){
                    System.out.println("O link está favoritado");
                    Botoes.definirIconeBotaoFavoritos(true);
                    return true;
                }
            }
            System.out.println("O link não está favoritado");
            return false;

        } catch (Exception e) {
            System.err.println(e);
            return false;
        }
    }

    public static void adicionarFavorito(ChromiumBrowser browser){

        if (!verificarSeEFavorito(browser.getUrl())){
            try (FileWriter escritor = new FileWriter("/home/ian/Downloads/favorito.txt", true)) {
                escritor.append(browser.getUrl() + "\n");
                Botoes.definirIconeBotaoFavoritos(browser.getUrl());
                System.out.println("Adicionou o favorito com sucesso");
            } catch (Exception e) {
                System.err.println(e);
            }
        }

    }

}
