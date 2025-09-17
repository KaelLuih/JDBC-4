package org.example.dao;

import org.example.model.Peca;
import org.example.util.Conexao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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


}
