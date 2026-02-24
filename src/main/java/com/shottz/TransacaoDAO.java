package com.shottz;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TransacaoDAO {

    // Método para SALVAR (Create) no banco
    public void salvar(Transacao transacao) {
        String sql = "INSERT INTO transacoes (descricao, valor, tipo, data_transacao) VALUES (?, ?, ?, ?)";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, transacao.getDescricao());
            stmt.setDouble(2, transacao.getValor());
            stmt.setString(3, transacao.getTipo());
            stmt.setDate(4, Date.valueOf(transacao.getData())); // Converte LocalDate para o Date do SQL

            stmt.executeUpdate();
            System.out.println("Transação salva no banco com sucesso!");

        } catch (SQLException e) {
            System.out.println("Erro ao salvar no banco: " + e.getMessage());
        }
    }

    // Método para BUSCAR (Read) do banco
    public List<Transacao> listarTodas() {
        List<Transacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM transacoes";

        try (Connection conn = ConexaoDB.conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                String descricao = rs.getString("descricao");
                double valor = rs.getDouble("valor");
                String tipo = rs.getString("tipo");
                LocalDate data = rs.getDate("data_transacao").toLocalDate();

                // Obs: Aqui estou usando a classe pai Transacao. Se sua lógica exigir, 
                // você pode instanciar Receita ou Despesa dependendo da variável 'tipo'.
                // Avalia o tipo que veio do banco de dados para criar o objeto correto
                Transacao t;
                if ("Receita".equalsIgnoreCase(tipo)) {
                    // Se o construtor de Receita não pedir o 'tipo', tire ele daqui
                    t = new Receita(descricao, valor, data); 
                } else {
                    t = new Despesa(descricao, valor, data);
                }

                lista.add(t);
            }

        } catch (SQLException e) {
            System.out.println("Erro ao listar do banco: " + e.getMessage());
        }
        return lista;
    }
}