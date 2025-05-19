package br.villes.aplicativoplacas.moldels;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
