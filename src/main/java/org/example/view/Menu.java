package org.example.view;

import java.util.Scanner;

public class Menu {

    public static int EscolhaMenu(){
        Scanner input = new Scanner(System.in);
        System.out.println("""
               --- Sistema de Manutenção Industrial ---
                
                    1- Cadastrar Máquina
                    2- Cadastrar Tecnico 
                    3- Cadastrar Peça
                    4- Criar Ordem De Manutenção
                    5- Associar Peças á Ordem
                    6- Executar Manutenção
                    
                \n""");
        int escolha = input.nextInt();
        return escolha;
    }
    public static void mensagemSucesso(){
        System.out.println("Cadastrado com sucesso \n");
    }
    public static void mensagemErro(){
        System.out.println("Houve Algum erro \n");
    }

}
