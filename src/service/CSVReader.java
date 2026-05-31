package service;

import model.Venda;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public List<Venda> lerArquivo(String caminhoArquivo) {
        List<Venda> vendas = new ArrayList<>();
        Path path = Paths.get(caminhoArquivo);

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String linha;
            boolean primeiraLinha = true;

            while ((linha = reader.readLine()) != null) {

                // Comando pra pular o cabeçalho 
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }

                // Server pra ignorar linhas vazias
                if (linha.isBlank()) continue;

                String[] campos = linha.split(",");

                // Garante que a linha tem todos os campos esperados
                if (campos.length < 5) {
                    System.err.println("Linha inválida ignorada: " + linha);
                    continue;
                }

                try {
                    int id             = Integer.parseInt(campos[0].trim());
                    String produto     = campos[1].trim();
                    String categoria   = campos[2].trim();
                    double valor       = Double.parseDouble(campos[3].trim());
                    int quantidade     = Integer.parseInt(campos[4].trim());

                    vendas.add(new Venda(id, produto, categoria, valor, quantidade));
                } catch (NumberFormatException e) {
                    System.err.println("Erro ao converter dados na linha: " + linha);
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        return vendas;
    }
}