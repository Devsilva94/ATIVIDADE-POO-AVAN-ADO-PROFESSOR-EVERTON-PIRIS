package thread;

import factory.ProcessorFactory;
import model.Venda;
import service.IProcessor;
import java.util.List;

public class VendaThread implements Runnable {

    private final List<Venda> loteVendas;

    public VendaThread(List<Venda> loteVendas) {
        this.loteVendas = loteVendas;
    }

    @Override
    public void run() {
        if (loteVendas == null || loteVendas.isEmpty()) {
            return;
        }

        try {
            IProcessor processador = ProcessorFactory.criarProcessador("VENDA");
            processador.processar(loteVendas);
        } catch (Exception e) {
            System.err.println("Erro ao processar lote na " + Thread.currentThread().getName() + ": " + e.getMessage());
        }
    }
}