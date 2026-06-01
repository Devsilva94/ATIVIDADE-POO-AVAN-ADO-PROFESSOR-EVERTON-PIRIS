package app;

import model.Venda;
import service.CSVReader;
import service.RelatorioService; // Trocamos o Singleton pelo Service aqui
import thread.VendaThread;

import java.util.ArrayList;
import java.util.List;

public class Main {

    private static final String CAMINHO_ARQUIVO = "src/resources/vendas.csv";
    private static final int TAMANHO_LOTE = 1000;

    public static void main(String[] args) {
        System.out.println("Iniciando a leitura do arquivo CSV...");

        CSVReader reader = new CSVReader();
        List<Venda> todasVendas = reader.lerArquivo(CAMINHO_ARQUIVO);

        if (todasVendas.isEmpty()) {
            System.err.println("Nenhum registro encontrado ou erro ao ler o arquivo. Encerrando.");
            return;
        }

        System.out.printf("Total de registros carregados: %d%n", todasVendas.size());
        System.out.println("Iniciando a divisão em lotes e disparo das Threads...");

        List<Thread> threadsAtivas = new ArrayList<>();
        int totalRegistros = todasVendas.size();

        for (int i = 0; i < totalRegistros; i += TAMANHO_LOTE) {
            int fimLote = Math.min(i + TAMANHO_LOTE, totalRegistros);

            List<Venda> lote = todasVendas.subList(i, fimLote);

            VendaThread tarefa = new VendaThread(lote);
            Thread thread = new Thread(tarefa, "Thread-Lote-" + (i / TAMANHO_LOTE + 1));

            threadsAtivas.add(thread);
            thread.start();
        }

        System.out.printf("Total de %d threads disparadas em paralelo. Aguardando conclusão...%n", threadsAtivas.size());

        for (Thread thread : threadsAtivas) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("A thread principal foi interrompida: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\nProcessamento concluído com sucesso!");

        RelatorioService relatorioService = new RelatorioService();
        relatorioService.gerarRelatorio();
    }
}