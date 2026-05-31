package app;

import model.Venda;

public class TesteVenda {

    public static void main(String[] args) {

        Venda venda = new Venda(
                1,
                "Notebook",
                "Eletronico",
                3500.00,
                2
        );

        System.out.println(venda);
    }
}
