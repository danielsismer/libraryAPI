package com.biblioteca.sismer.infrastructure.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseCredentials {

    private static final String URL = "jdbc:mysql://127.0.0.1:3306/biblioteca?user=root";
    private static final String USER = "root";
    private static final String PASSWORD = "mysqlPW";

    public static Connection connectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void main(String[] args) {
        try{
            Connection conn = connectar();
            if(!conn.isClosed()){
                System.out.println("OK");
            } else {
                System.out.println("ERROR");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
