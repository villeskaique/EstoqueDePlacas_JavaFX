package br.villes.aplicativoplacas.moldels;

public class Placa {
    private int id;
    private String fabricante;
    private String codigo;
    private String tipo;
    private String modelo;
    private String preco;
    private String dataPostagem;

    public Placa(int id, String fabricante, String codigo, String tipo, String modelo, String preco, String dataPostagem) {
        this.id = id;
        this.fabricante = fabricante;
        this.codigo = codigo;
        this.tipo = tipo;
        this.modelo = modelo;
        this.preco = preco;
        this.dataPostagem = dataPostagem;
    }

    public Placa() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }

    public String getDataPostagem() {
        return dataPostagem;
    }

    public void setDataPostagem(String dataPostagem) {
        this.dataPostagem = dataPostagem;
    }
}
