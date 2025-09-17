package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static final String URL = "jdbc:mysql://localhost:3306/Sistema_Manutenca?useSSL=false&serverTimezone=UTC";
private final static String USER = "root";
private final static String SENHA = "mysqlPW";


public static Connection conectar() throws SQLException{
    return DriverManager.getConnection(URL,USER,SENHA);
}

}
