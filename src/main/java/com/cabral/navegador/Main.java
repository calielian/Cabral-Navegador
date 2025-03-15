package com.cabral.navegador;

import java.util.List;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static final String NOME_PASTA_CONFIG = ".cabral_navegador";
    public static final String NOME_ARQUIVO_CONFIGURACAO = "config.cabral";
    public static final String NOME_ARQUIVO_FAVORITOS = "favoritos.cabral";
    public static void main(String[] args) {

        // pega a pasta pessoal do usuário (independente do sistema)
        String pastaUsuario = System.getProperty("user.home");

        // cria o caminho dos arquivos/pasta e cria uma lista feita só para possibilitar a criação dos arquivos
        Path caminhoPastaConfig = Paths.get(pastaUsuario, NOME_PASTA_CONFIG);
        Path caminhoArquivoConfig = Paths.get(pastaUsuario, NOME_PASTA_CONFIG, NOME_ARQUIVO_CONFIGURACAO);
        Path caminhoArquivoFav = Paths.get(pastaUsuario, NOME_PASTA_CONFIG, NOME_ARQUIVO_FAVORITOS);
        List<String> escrever = List.of("");

        try {
            // verifica se a pasta de configuração já existe
            if (!Files.exists(caminhoPastaConfig)) {
                // cria a pasta e arquivos
                Files.createDirectories(caminhoPastaConfig);
                Files.write(caminhoArquivoConfig, escrever);
                Files.write(caminhoArquivoFav, escrever);

                System.out.println("Pasta de configurações criada com sucesso em: " + caminhoPastaConfig);
                System.out.println("Arquivo de configuração criado com sucesso em: " + caminhoArquivoConfig);
                System.out.println("Arquivo de favoritos criado com sucesso em: " + caminhoArquivoFav);
            } else {
                System.out.println("Pasta já existe em: " + caminhoPastaConfig);
            }

            System.out.println("Iniciando aplicativo...");
            new JanelaPrincipal();
        } catch (IOException e) {
            System.err.println("Erro ao criar a pasta\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro\n" + e.getMessage());
        }
    }
}