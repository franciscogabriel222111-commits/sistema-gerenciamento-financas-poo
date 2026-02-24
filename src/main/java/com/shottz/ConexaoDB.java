package com.shottz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    
    // 1. URL de Conexão:
    // Se for local, costuma ser algo como: "jdbc:postgresql://localhost:5432/financas"
    // Se for na nuvem (ex: Supabase), pegue a string JDBC no painel do banco.
    private static final String URL = "jdbc:postgresql://localhost:5432/bd_financas"; // Substitua "localholt" pelo host do seu banco e "bd_financas" pelo nome do seu banco
    
    // 2. Credenciais:
    private static final String USER = "postgres"; // Usuário padrão
    private static final String PASS = "shottz"; // Coloque a senha do banco

    public static Connection conectar() throws SQLException {
        // Essa linha é a que efetivamente "abre a porta" do banco
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // --- CÓDIGO DE TESTE RÁPIDO ---
    // Colocamos um main() aqui só para rodar esse arquivo isolado e ver se conecta
    public static void main(String[] args) {
        try {
            Connection conn = conectar();
            System.out.println("✅ Conexão com o PostgreSQL estabelecida com sucesso!");
            conn.close();
        } catch (SQLException e) {
            System.out.println("❌ Erro ao conectar: " + e.getMessage());
        }
    }
}