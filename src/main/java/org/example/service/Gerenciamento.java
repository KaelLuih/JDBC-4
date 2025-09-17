package org.example.service;

import org.example.dao.*;
import org.example.model.*;
import org.example.view.Menu;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public static void CadastrarOrdem() throws SQLException {
        MaquinaDAO maquinaDAO = new MaquinaDAO();
        TecnicoDAO tecnicoDAO = new TecnicoDAO();
        OrdemDeManutencaoDAO ordemDAO = new OrdemDeManutencaoDAO();


        List<Maquina> maquinas = maquinaDAO.ListarMaquinasOperacionais();
        if (maquinas.isEmpty()) {
            System.out.println("Não há máquinas OPERACIONAIS.");
            return;
        }
        System.out.println("MÁQUINAS OPERACIONAIS:");
        for (int i = 0; i < maquinas.size(); i++) {
            System.out.println((i + 1) + " - " + maquinas.get(i).getNome()
                    + " | Setor: " + maquinas.get(i).getSetor());
        }

        int escolhaMaquina;
        while (true) {
            System.out.print("Escolha a máquina (número): ");
            escolhaMaquina = input.nextInt();
            if (escolhaMaquina >= 1 && escolhaMaquina <= maquinas.size()) break;
            System.out.println("Opção inválida!");
        }
        Maquina maquinaSelecionada = maquinas.get(escolhaMaquina - 1);


        List<Tecnico> tecnicos = tecnicoDAO.ListarTecnicos();
        if (tecnicos.isEmpty()) {
            System.out.println("Não há técnicos cadastrados.");
            return;
        }
        System.out.println("TÉCNICOS DISPONÍVEIS:");
        for (int i = 0; i < tecnicos.size(); i++) {
            System.out.println((i + 1) + " - " + tecnicos.get(i).getNome()
                    + " | Esp: " + tecnicos.get(i).getEspecialidade());
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
                maquinaSelecionada.getId(),
                tecnicoSelecionado.getId(),
                LocalDate.now(),
                "PENDENTE"
        );
        ordemDAO.CadastrarOrdemDeManutencao(ordem);
        maquinaDAO.atualizarStatus(maquinaSelecionada);

        Menu.mensagemSucesso();
    }

    public static void AssociarPeca() throws SQLException {
        var ordemDAO = new OrdemDeManutencaoDAO();
        List<Integer> opcoesMaquina = new ArrayList<>();
        List<OrdemDeManutencao> ordens = ordemDAO.listarOrdensManutencaoPendente();


        if (!ordens.isEmpty()) {
            for (OrdemDeManutencao ordem : ordens) {
                System.out.println("\n----ORDEM----\n");
                System.out.println("id ordem:" + ordem.getId());
                System.out.println("id Maquina:" + ordem.getIdMaquina());
                System.out.println("id Tecnico:" + ordem.getIdTecnico());
                System.out.println("DATA solicitação:" + ordem.getDataSolicitacao());
                System.out.println("STATUS:" + ordem.getStatus());

                opcoesMaquina.add(ordem.getId());

            }
            System.out.println("Digite o id da maquina que deseja atribuir a ordem");
            int escolha = input.nextInt();
            input.nextLine();

            if (opcoesMaquina.contains(escolha)) {
                boolean sair = false;
                while (!sair) {
                    var pecaDao = new PecaDAO();
                    List<Integer> opcoesPeca = new ArrayList<>();
                    List<Peca> pecas = pecaDao.listarPecas();

                    var ordemPecaDao = new OrdemPecaDAO();

                    for (Peca peca : pecas) {

                        if (!ordemPecaDao.VerificarOrdem(escolha, peca.getId())) {

                            System.out.println("\n------PECAS------");
                            System.out.println("ID ORDEM: " + peca.getId());
                            System.out.println("NOME: " + peca.getNome());
                            System.out.println("ESTOQUE: " + peca.getEstoque());
                            System.out.println("--------------------\n");

                            opcoesPeca.add(peca.getId());

                        }
                    }

                    System.out.println("Digite o id da peça para selecionar: ");
                    int idPeca = input.nextInt();
                    input.nextLine();

                    if (opcoesPeca.contains(idPeca)) {
                        System.out.println("Digite a quantidade da peça que sera utilizada: ");
                        double quantidadePeca = input.nextDouble();
                        input.nextLine();

                        double quantidadeEstoque = 0;

                        for (Peca peca : pecas) {
                            if (idPeca == peca.getId()) {
                                quantidadeEstoque = peca.getEstoque();
                            }
                        }
                        if (quantidadePeca >= quantidadePeca) {
                            var OrdemPeca = new OrdemPeca(escolha, idPeca, quantidadePeca);
                            ordemPecaDao.inserirOrdem(OrdemPeca);
                        } else {
                            System.out.println("Estoque não esta batendo");
                        }
                    } else {
                        System.out.println("opção invalida");
                        AssociarPeca();
                    }
                }

            } else {
                System.out.println("opção invalida");
                AssociarPeca();
            }

        }
    }
    public static void RealizarManutencao()throws SQLException{

    }



}
