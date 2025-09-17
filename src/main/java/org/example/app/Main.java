package org.example.app;


import org.example.controller.Executar;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args)throws SQLException {
        new Executar().executarSistema();
    }
}