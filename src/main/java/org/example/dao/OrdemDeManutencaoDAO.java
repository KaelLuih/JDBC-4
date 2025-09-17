package org.example.dao;

import org.example.model.OrdemDeManutencao;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrdemDeManutencaoDAO {

    public static void CadastrarOrdemDeManutencao(OrdemDeManutencao ordem)throws SQLException {
        String query = "INSERT INTO OrdemManutencao( idMaquina, " +
                "                                    idTecnico, " +
                "                                    dataSolicitacao, " +
                "                                    status)" +
                "        VALUES" +
                "        (?,?,?,?)   ";
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1,ordem.getIdMaquina());
            stmt.setInt(2,ordem.getIdTecnico());
            stmt.setDate(3,java.sql.Date.valueOf(ordem.getDataSolicitacao()));
            stmt.setString(4, ordem.getStatus());
            stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public List<OrdemDeManutencao> listarOrdensManutencaoPendente(){
        List<OrdemDeManutencao> ordens = new ArrayList<>();
        String query = """
                    SELECT id
                    	, idMaquina
                    	, idTecnico
                    	, dataSolicitacao
                        , status
                    FROM OrdemManutencao
                    WHERE status = 'PENDENTE'
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                int idMaquina = rs.getInt("idMaquina");
                int idTecnico = rs.getInt("idTecnico");
                Date dataSolicitacao = rs.getDate("dataSolicitacao");
                String status = rs.getString("status");

                var ordem = new OrdemDeManutencao(id, idMaquina,idTecnico, ((java.sql.Date) dataSolicitacao).toLocalDate(),status);
                ordens.add(ordem);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return ordens;
    }
    public static void AtualizarStatus(int idOrdem) {
        String query = """
                UPDATE OrdemManutencao
                SET status = 'EXECUTADO'
                WHERE id = ?
                """;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idOrdem);
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
