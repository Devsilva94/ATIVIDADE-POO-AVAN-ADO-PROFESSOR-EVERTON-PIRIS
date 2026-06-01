package factory;

import service.IProcessor;
import service.VendaProcessor;

public class ProcessorFactory {

    public static IProcessor criarProcessador(String tipo) {
        if (tipo == null || tipo.trim().isEmpty()) {
            throw new IllegalArgumentException("Tipo não informado.");
        }

        switch (tipo.toUpperCase()) {
            case "VENDA":
                return new VendaProcessor();
            default:
                throw new IllegalArgumentException("Processador não encontrado para o tipo: " + tipo);
        }
    }
}