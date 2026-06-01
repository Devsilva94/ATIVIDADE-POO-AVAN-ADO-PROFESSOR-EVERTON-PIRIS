package service;

import java.util.List;
import model.Venda;
import singleton.RelatorioSingleton;

public class VendaProcessor implements IProcessor {

    @Override
    public void processar(List<Venda> vendas) {
        // Pega a instância única que seu colega configurou
        RelatorioSingleton relatorio = RelatorioSingleton.getInstance();

        for (Venda venda : vendas) {
            relatorio.adicionarVenda(
                venda.getProduto(), 
                venda.getValor(), 
                venda.getQuantidade()
            );
        }
    }
}