package org.example.dao;

import org.example.model.OrdemPeca;
import org.example.util.Conexao;
import org.example.view.Menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrdemPecaDAO {


    public void inserirOrdem(OrdemPeca ordempeca) throws SQLException {
        String query = "INSERT INTO OrdemPeca" +
                "(idOrdem,idPeca,quantidade)" +
                "VALUES (?,?.?)";

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, ordempeca.getIdOrdem());
            stmt.setInt(2, ordempeca.getIdPeca());
            stmt.setDouble(3, ordempeca.getQuantidade());
            stmt.executeUpdate();
            Menu.mensagemSucesso();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public boolean VerificarOrdem(int idOrdem, int idPeca) throws SQLException {
        String query = """
                SELECT COUNT(0) AS contagem
                FROM OrdemPeca 
                WHERE idOrdem = ?
                AND idPeca =?
                
                
                """;

        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idOrdem);
            stmt.setInt(2, idPeca);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int contagem = rs.getInt("contagem ");
                if (contagem > 0) {
                    return true;
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return false;
    }

    public List<OrdemPeca> BuscarOrdemPoridOrdemManu(int idOrdem) {
        List<OrdemPeca> lista = new ArrayList<>();
        String query = """ 
                SELECT idOrdem 
                      , idPeca 
                      , quantidade
                FROM OrdemPeca
                WHERE idOrdem = ?       
                
                """;
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idOrdem);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int idOrdemNovo = rs.getInt("idOrdem");
                int idPeca = rs.getInt("idPeca");
                double quantidade = rs.getDouble("quantidade");
                var ordem = new OrdemPeca(idOrdemNovo, idPeca, quantidade);
                lista.add(ordem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}
