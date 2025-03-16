package com.cabral.navegador;

import java.util.List;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static final String NOME_PASTA_CONFIG = ".cabral_navegador";
    public static final String NOME_ARQUIVO_CONFIGURACAO = "config.cabral";
    public static final String NOME_ARQUIVO_FAVORITOS = "favoritos.cabral";
    public static String tema = "claro";
    public static String pagina_inicial = "about:blank";
    public static void main(String[] args) {

        // pega a pasta pessoal do usuário (independente do sistema)
        String pastaUsuario = System.getProperty("user.home");

        // cria o caminho dos arquivos/pasta e cria uma lista feita só para possibilitar a criação dos arquivos
        Path caminhoPastaConfig = Paths.get(pastaUsuario, NOME_PASTA_CONFIG);
        Path caminhoArquivoConfig = Paths.get(pastaUsuario, NOME_PASTA_CONFIG, NOME_ARQUIVO_CONFIGURACAO);
        Path caminhoArquivoFav = Paths.get(pastaUsuario, NOME_PASTA_CONFIG, NOME_ARQUIVO_FAVORITOS);

        List<String> escrever = List.of("PAG_INICIAL=", "TEMA=claro");
        List<String> escreverFav = List.of("");

        try {
            // verifica se a pasta de configuração já existe
            if (!Files.exists(caminhoPastaConfig)) {
                // cria a pasta e arquivos
                Files.createDirectories(caminhoPastaConfig);
                Files.write(caminhoArquivoConfig, escrever);
                Files.write(caminhoArquivoFav, escreverFav);

                System.out.println("Pasta de configurações criada com sucesso em: " + caminhoPastaConfig);
                System.out.println("Arquivo de configuração criado com sucesso em: " + caminhoArquivoConfig);
                System.out.println("Arquivo de favoritos criado com sucesso em: " + caminhoArquivoFav);
            } else {
                System.out.println("Pasta de configurações já existe em: " + caminhoPastaConfig);
            }

            // verifica se o arquivo de configuração existe
            if (!Files.exists(caminhoArquivoConfig)) {
                Files.write(caminhoArquivoConfig, escrever);

                System.out.println("Arquivo de configuração recriado com sucesso em: " + caminhoArquivoConfig);
            }

            // verifica se o arquivo de favoritos existe
            if (!Files.exists(caminhoArquivoFav)) {
                Files.write(caminhoArquivoFav, escreverFav);

                System.out.println("Arquivo de favoritos recriado com sucesso em: " + caminhoArquivoFav);
            }

            // define o tema
            List<String> arquivoConfig = Files.readAllLines(caminhoArquivoConfig);
            tema = arquivoConfig.get(1).split("TEMA=")[1];

            if (tema.equals("claro")){
                FlatLightLaf.setup();
            } else {
                FlatDarkLaf.setup();
            }

            try {
                File arquivoHTML = transferirHTML();
    
                TratamentoURL.pagina_inicial = arquivoHTML.toURI().toString();
            } catch (Exception e) {
                System.err.println("Erro ao inicializar a página inicial\n" + e.getMessage());
            }

            // inicia o app
            System.out.println("Iniciando aplicativo...");
            new JanelaPrincipal();
        } catch (IOException e) {
            System.err.println("Erro ao criar a pasta\n" + e.getMessage());
        } catch (Exception e) {
            System.out.println("Erro\n" + e.getMessage());
        }
    }

    private static File transferirHTML() throws Exception {

        // transfere o arquivo HTML do JAR para um arquivo temporário
        Path arquivoHTMLTemp = Files.createTempFile("pag_inicial", ".html"); // cria um arquivo temporário
        File arquivoHTML = arquivoHTMLTemp.toFile(); // entrega o caminho do arquivo/entrega o arquivo
        arquivoHTML.deleteOnExit(); // define para deletar quando o app for fechado
        
        // pega o HTML do JAR
        try (InputStream arquivoHTMLJAR = Main.class.getResourceAsStream(String.format("/assets/pag_inicial%s.html", "_" + tema))) {

            if (arquivoHTMLJAR == null){
                throw new RuntimeException("Não foi possível acessar o arquivo html");
            }

            // abre um try com o arquivo para ser escrito
            try (OutputStream arquivoHTMLOutput = new FileOutputStream(arquivoHTML)) {
                byte[] buffer = new byte[1024]; // cria uma array de 1024 bytes (1 MB)
                int bytesLidos; // variável dos bytes lidos

                // enquanto o byte lido do do arquivo HTML do JAR for diferente de -1 (não está no fim do arquivo)
                while ((bytesLidos = arquivoHTMLJAR.read(buffer)) != -1) {
                    arquivoHTMLOutput.write(buffer, 0, bytesLidos); // escreve os bytes lidos no arquivo HTML temporário
                }
            } 
        }

        /* Por que?
         * 
         * Porque se eu tõ copiando os bytes do arquivo A para arquivo B, então eu tô literalmente copiando
         * as informações do A para o B, seria como se eu duplicasse os arquivos. Da mesma forma que se eu copiar
         * o DNA de uma pessoa para outra, estarei criando uma cópia exata de uma pessoa.
         */

        return arquivoHTML; 
    }
}