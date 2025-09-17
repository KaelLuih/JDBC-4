package org.example.service;

import org.example.dao.MaquinaDAO;
import org.example.dao.OrdemDeManutencaoDAO;
import org.example.dao.PecaDAO;
import org.example.dao.TecnicoDAO;
import org.example.model.Maquina;
import org.example.model.OrdemDeManutencao;
import org.example.model.Peca;
import org.example.model.Tecnico;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Gerenciamento {
    static Scanner input = new Scanner(System.in);

    public static void CadastrarMaquina() throws SQLException {
        System.out.println("Digite o nome da maquina");
        String nome = input.nextLine();
        System.out.println("Digite o setor da maquina");
        String setor = input.nextLine();


        Maquina maquina = new Maquina(nome, setor);
        var dao = new MaquinaDAO();
        if (nome.isEmpty() || setor.isEmpty()) {
            System.out.println("Voce não pode deixar vazio");
            return;
        }
        try {
            if (dao.ValidacaoExistencia(nome, setor)) {
                System.out.println("Ja existe uma maquina com esse nome nesse setor");
                return;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        dao.CadastrarMaquina(maquina);

    }

    public static void CadastrarTecnico() throws SQLException {
        System.out.println("Digite o nome do tecnico");
        String nome = input.nextLine();
        System.out.println("Digite a especialidade");
        String especialidade = input.nextLine();

        Tecnico tecnico = new Tecnico(nome, especialidade);
        var dao = new TecnicoDAO();
        if (especialidade.isEmpty() || nome.isEmpty()) {
            System.out.println("Não pode deixar os valores vazios");
        }
        try {
            if (dao.ValidarDuplicacao(nome, especialidade)) {
                System.out.println("Voce esta duplicando Tecnicos");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        dao.CadastrarTecnico(tecnico);

    }

    public static void CadastrarPEca() throws SQLException {
        System.out.println("Digite o nome da Peça");
        String nome = input.nextLine();
        System.out.println("Digite a quantidade no estoque ");
        double estoque = input.nextDouble();
        input.nextLine();

        Peca peca = new Peca(nome, estoque);
        var dao = new PecaDAO();


        if (nome.isEmpty()) {
            System.out.println("è obrigatorio adicionar um nome a peça");
            return;
        }
        try {
            if (dao.ValidacaoDuplicacao(nome)) {
                System.out.println("Voce nao pode duplicar uma peça");
                return;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        if (estoque <= 0) {
            System.out.println("Tem que Adicionar algum valor valido ao estoque ");
            return;
        }
        dao.CadastrarPeca(peca);
    }

    public static void CadastrarOrdem() throws SQLException{
        MaquinaDAO maquinaDAO=new MaquinaDAO();
        TecnicoDAO tecnicoDAO = new TecnicoDAO();
        OrdemDeManutencaoDAO ordemDAO = new OrdemDeManutencaoDAO();


        List<Maquina> maquinas = maquinaDAO.ListarMaquinasOperacionais();
        if(maquinas.isEmpty()){
            System.out.println("Não há maquinas cadastradas");
            return;
        }
        System.out.println("MAQUINAS OPERACIONAIS DISPONIVEIS");
        for(int i =0; i<maquinas.size(); i++){
            System.out.println((i + 1) + "-" + maquinas.get(i).getNome() + "-" + maquinas.get(i).getSetor()
            );
            int escolhaMaquina;
            while (true) {
                System.out.print("Escolha a máquina (número): ");
                escolhaMaquina = input.nextInt();
                if (escolhaMaquina >= 1 && escolhaMaquina <= maquinas.size()) break;
                System.out.println("Opção inválida!");
            }
           Maquina maquina = maquinas.get(escolhaMaquina - 1);
        }

        List<Tecnico> tecnicos = tecnicoDAO.ListarTecnicos();
        if (tecnicos.isEmpty()) {
            System.out.println("Não há técnicos cadastrados.");
            return;
        }
        System.out.println("Técnicos disponíveis:");
        for (int i = 0; i < tecnicos.size(); i++) {
            System.out.println((i + 1) + ". " + tecnicos.get(i).getNome() + " - " + tecnicos.get(i).getEspecialidade());
        }
        int escolhaTecnico;
        while (true) {
            System.out.print("Escolha o técnico (número): ");
            escolhaTecnico = input.nextInt();
            if (escolhaTecnico >= 1 && escolhaTecnico <= tecnicos.size()) break;
            System.out.println("Opção inválida!");
        }
        Tecnico tecnicoSelecionado = tecnicos.get(escolhaTecnico - 1);

        OrdemDeManutencao ordem = new OrdemDeManutencao(
                maquina.getId(),
                tecnicoSelecionado.getId(),
                LocalDate.now(), // Data atual
                "PENDENTE"
        );
    }

}
