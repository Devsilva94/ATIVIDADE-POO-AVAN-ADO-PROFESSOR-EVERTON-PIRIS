package model;

/**
 * Classe que representa uma venda.
 * Cada linha do CSV será transformada em um objeto Venda.
 */
public class Venda {

    private int id;
    private String produto;
    private String categoria;
    private double valor;
    private int quantidade;

    // Construtor vazio
    public Venda() {
    }

    // Construtor completo
    public Venda(int id, String produto, String categoria,
                 double valor, int quantidade) {

        this.id = id;
        this.produto = produto;
        this.categoria = categoria;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getProduto() {
        return produto;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }


    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }


    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }


    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Venda{" +
                "id=" + id +
                ", produto='" + produto + '\'' +
                ", categoria='" + categoria + '\'' +
                ", valor=" + valor +
                ", quantidade=" + quantidade +
                '}';
    }
}