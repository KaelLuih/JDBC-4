package org.example.dao;

import org.example.model.Tecnico;
import org.example.util.Conexao;
import org.example.view.Menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TecnicoDAO {

    public void CadastrarTecnico(Tecnico tecnico)throws SQLException{
        String query = "INSERT INTO Tecnico(" +
                "                           nome," +
                "                           especialidade) " +
                "       VALUES " +
                "       (?,?)";

        try(Connection conn = Conexao.conectar();
            PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,tecnico.getNome());
            stmt.setString(2,tecnico.getEspecialidade());
            stmt.executeUpdate();
            Menu.mensagemSucesso();
        } catch (SQLException e) {
            Menu.mensagemErro();
            e.printStackTrace();
        }




    }

    public  boolean ValidarDuplicacao(String nome , String especialidade) throws SQLException{
        String query = "SELECT COUNT(*) FROM Tecnico WHERE nome = ? AND especialidade = ? ";
        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1,nome);
            stmt.setString(2,especialidade);
            ResultSet rs = stmt.executeQuery();
           return rs.next() && rs.getInt(1) > 0;
        } catch (SQLException e) {
        e.printStackTrace();
        }

return false;
    }

    public List<Tecnico> ListarTecnicos()throws SQLException{
        List<Tecnico> tecnicos = new ArrayList<>();

        String query = "SELECT id,nome,especialidade FROM Tecnico";
        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            while(rs.next()){
                tecnicos.add(new Tecnico(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("especialidade")
                ));
                Menu.mensagemSucesso();
            }


        }catch (SQLException e ){
            e.printStackTrace();
        }
    return tecnicos;
    }




}
