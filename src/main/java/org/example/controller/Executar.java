package org.example.controller;

import org.example.service.Gerenciamento;
import org.example.view.Menu;

import java.sql.SQLException;

public class Executar {

    public void executarSistema()throws SQLException {
        while (true){
            System.out.println("--- Bem vindo ao Sistema ---\n \n ");
            int escolha = Menu.EscolhaMenu();

            switch (escolha) {
                case 1: {
                    Gerenciamento.CadastrarMaquina();
                    break;
                }
                case 2:{
                    Gerenciamento.CadastrarTecnico();
                    break;
                }
                case 3:{
                    Gerenciamento.CadastrarPEca();
                    break;
                }
                case 4:{
                    Gerenciamento.CadastrarOrdem();
                    break;
                }
                case 5:{

                }
            }
        }
    }

}
