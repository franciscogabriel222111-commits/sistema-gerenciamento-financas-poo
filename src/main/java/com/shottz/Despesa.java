package com.shottz;

import java.time.LocalDate;

/**
 * REQUISITO POO: HERANÇA
 */
public class Despesa extends Transacao {

    public Despesa(String descricao, double valor, LocalDate data) {
        super(descricao, valor, data);
    }

    // REQUISITO POO: POLIMORFISMO
    @Override
    public String getTipo() {
        return "Despesa";
    }
}