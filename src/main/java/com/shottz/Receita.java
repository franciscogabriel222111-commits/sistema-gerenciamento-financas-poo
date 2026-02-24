package com.shottz;

import java.time.LocalDate;

/**
 * REQUISITO POO: HERANÇA
 * Receita "É UMA" Transacao.
 */
public class Receita extends Transacao {

    public Receita(String descricao, double valor, LocalDate data) {
        super(descricao, valor, data);
    }

    // REQUISITO POO: POLIMORFISMO (Sobrescrita)
    @Override
    public String getTipo() {
        return "Receita";
    }
}
