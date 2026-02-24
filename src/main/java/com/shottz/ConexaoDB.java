package com.shottz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {
    
    
    private static final String URL = "jdbc:postgresql://localhost:5432/bd_financas";
    
    
    private static final String USER = "postgres"; 
    private static final String PASS = "shottz"; 

    public static Connection conectar() throws SQLException {
        
        return DriverManager.getConnection(URL, USER, PASS);
    }

    
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