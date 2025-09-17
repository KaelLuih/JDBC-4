package org.example.dao;

import org.example.model.Peca;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PecaDAO {

    public static void CadastrarPeca(Peca peca)throws SQLException {
        String query = "INSERT INTO Peca(" +
                "                        nome," +
                "                        estoque) " +
                "        VALUES" +
                "        (?,?)";
    try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setString(1,peca.getNome());
        stmt.setDouble(2,peca.getEstoque());
        stmt.executeUpdate();
    }catch (SQLException e){
        e.printStackTrace();
    }

    }
public boolean ValidacaoDuplicacao(String nome)throws SQLException{
        String query = "SELECT COUNT(*) FROM Peca WHERE nome = ? ";
        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1,nome);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }catch (SQLException e){
            e.printStackTrace();
        }
return false;
}
    public static List<Peca> listarPecas(){
        List<Peca> pecas = new ArrayList<>();
        String query = """
                    SELECT    id
                            , nome
                            , estoque
                    FROM Peca
                """;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                double estoque = rs.getDouble("estoque");

                var peca = new Peca(id,nome, estoque);
                pecas.add(peca);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return pecas;
    }
    public double buscarEstoquePorId(int id){
        String query = "SELECT estoque FROM Peca WHERE id = ?";
        double quantidade = 0;
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setInt(1,id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                quantidade = rs.getDouble("estoque");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return quantidade;
    }
    public static void Atualizar(double quantidade, Double id){
        String query = "UPDATE Peca" +
                "SET estoque = estoque - ?" +
                "where id =? ";
        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
        stmt.setDouble(1,quantidade);
        stmt.setInt(2,id);
        stmt.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }


}
