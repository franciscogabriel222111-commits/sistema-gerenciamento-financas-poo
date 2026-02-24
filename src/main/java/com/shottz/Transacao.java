package com.shottz;

import java.time.LocalDate;


public abstract class Transacao {
    // REQUISITO POO: ENCAPSULAMENTO (Atributos privados)
    private String descricao;
    private double valor;
    private LocalDate data;

    public Transacao(String descricao, double valor, LocalDate data) {
        this.descricao = descricao;
        this.valor = valor;
        this.data = data;
    }

    // REQUISITO POO: POLIMORFISMO
    // Método abstrato que obriga as subclasses a dizerem o que são
    public abstract String getTipo();

    // GETTERS E SETTERS (Encapsulamento)
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    public LocalDate getData() { return data; }
    public void setData(LocalDate data) { this.data = data; }
}
