package singleton;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.Map;

public class RelatorioSingleton {

    // atribuição do Sigleton(única instância)
    private static volatile RelatorioSingleton instancia;

    // --- Estado Thread-Safe ---

    // Valor total armazenado em centavos
    private final AtomicLong valorTotalCentavos = new AtomicLong(0);

    // Quantidade total de itens vendidos
    private final AtomicInteger quantidadeTotal = new AtomicInteger(0);

    // Mapeamento dos produtos pelo mais vendido
    private final ConcurrentHashMap<String, AtomicInteger> vendasPorProduto =
            new ConcurrentHashMap<>();

    // Construtor privado
    private RelatorioSingleton() {}

    // Criação do Singleton de forma segura em ambiente multithread
    public static RelatorioSingleton getInstance() {
        if (instancia == null) {
            synchronized (RelatorioSingleton.class) {
                if (instancia == null) {
                    instancia = new RelatorioSingleton();
                }
            }
        }
        return instancia;
    }

    // Métodos de atualização(chamados pelas threads)

    public void adicionarVenda(String produto, double valor, int quantidade) {
        // Converte para centavos antes de somar (evita race condition com double)
        long centavos = Math.round(valor * quantidade * 100);
        valorTotalCentavos.addAndGet(centavos);

        quantidadeTotal.addAndGet(quantidade);

        // Garante que a chave existe e incrementa atomicamente
        vendasPorProduto
            .computeIfAbsent(produto, k -> new AtomicInteger(0))
            .addAndGet(quantidade);
    }

    // Método de leitura

    public double getValorTotal() {
        return valorTotalCentavos.get() / 100.0;
    }

    public int getQuantidadeTotal() {
        return quantidadeTotal.get();
    }

    public String getProdutoMaisVendido() {
        return vendasPorProduto.entrySet()
                .stream()
                .max(Map.Entry.comparingByValue(
                        (a, b) -> Integer.compare(a.get(), b.get())))
                .map(Map.Entry::getKey)
                .orElse("Nenhum produto registrado");
    }

    // Relatório final

    public void exibirRelatorioFinal() {
        System.out.println("========================================");
        System.out.println("          RELATÓRIO FINAL DE VENDAS     ");
        System.out.println("========================================");
        System.out.printf("Valor total vendido  : R$ %,.2f%n", getValorTotal());
        System.out.printf("Quantidade total     : %d itens%n", getQuantidadeTotal());
        System.out.printf("Produto mais vendido : %s (%d unidades)%n",
                getProdutoMaisVendido(),
                vendasPorProduto.getOrDefault(
                        getProdutoMaisVendido(), new AtomicInteger(0)).get());
        System.out.println("========================================");
    }
}