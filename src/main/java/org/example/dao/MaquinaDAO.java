package org.example.dao;

import org.example.model.Maquina;
import org.example.util.Conexao;
import org.example.view.Menu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MaquinaDAO {

    public void CadastrarMaquina(Maquina maquina) throws SQLException {
        String query = "INSERT INTO Maquina( " +
                "                           nome," +
                "                           setor,    " +
                "                           status )" +
                "       VALUES " +
                "       (?,?,?   )            ";
        try (Connection conn = Conexao.conectar();
             PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1,maquina.getNome());
            stmt.setString(2,maquina.getSetor());
            stmt.setString(3,maquina.getStatus());
            stmt.executeUpdate();
            Menu.mensagemSucesso();

        }catch (SQLException e){
            Menu.mensagemErro();
            e.printStackTrace();
        }



    }
    public boolean ValidacaoExistencia(String nome, String setor) throws SQLException{
        String query = "SELECT COUNT(*) FROM Maquina WHERE nome = ? AND setor = ? ";
        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1,nome);
            stmt.setString(2,setor);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1)>0;




        }catch (SQLException e){
            e.printStackTrace();
        }

        return false;
    }
    public List<Maquina> ListarMaquinasOperacionais () throws SQLException{
        List <Maquina> maquinas = new ArrayList<>();
        String query = "SELECT id, nome,setor,status FROM Maquina WHERE status = 'OPERACIONAL' ";
        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                maquinas.add(new Maquina(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("setor"),
                        rs.getString("status")


                ));
                Menu.mensagemSucesso();
            }

            }catch (SQLException e){
            e.printStackTrace();
            Menu.mensagemErro();

        }
        return maquinas;
    }

    public void atualizarStatus(Maquina maquina) throws SQLException {
        String query = "UPDATE Maquina SET status = ? WHERE id = ?";
        try(Connection conn = Conexao.conectar();
        PreparedStatement stmt = conn.prepareStatement(query)){
            maquina.setStatus("EM_MANUTENCAO");
            stmt.setString(1, maquina.getStatus());
            stmt.setInt(2,maquina.getId());
            stmt.executeUpdate();

        }catch (SQLException e){
            e.printStackTrace();
        }


    }



}
