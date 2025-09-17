package org.example.dao;

import org.example.model.OrdemDeManutencao;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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


}
