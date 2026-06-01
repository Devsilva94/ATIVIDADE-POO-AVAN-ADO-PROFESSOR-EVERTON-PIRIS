package service;

import java.util.List;
import model.Venda;

public interface IProcessor {
    void processar(List<Venda> dados);
}