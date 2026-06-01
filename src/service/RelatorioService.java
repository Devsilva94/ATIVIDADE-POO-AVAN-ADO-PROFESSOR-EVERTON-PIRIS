package service;

import singleton.RelatorioSingleton;

public class RelatorioService {

    public void gerarRelatorio() {
        RelatorioSingleton relatorio = RelatorioSingleton.getInstance();
        relatorio.exibirRelatorioFinal();
    }
}