package com.cesar.dao.generic.JDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static Connection connection;

    // Removido o construtor com parâmetro, não é necessário
    private ConnectionFactory() {
    }

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = initConnection();
        }
        return connection;
    }

    private static Connection initConnection() throws SQLException {
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:15432/vendas_online_2", "postgres", "admin");
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar conexão com o banco de dados", e);
        }
    }
}
