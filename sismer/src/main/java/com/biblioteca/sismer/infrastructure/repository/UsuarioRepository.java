package com.biblioteca.sismer.infrastructure.repository;

import com.biblioteca.sismer.infrastructure.database.DatabaseCredentials;
import com.biblioteca.sismer.model.Livro;
import com.biblioteca.sismer.model.Usuario;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UsuarioRepository {


    public Usuario cadastrarUsuario(Usuario usuario) throws SQLException {

        String query = """
                INSERT INTO usuario (nome, email) VALUES (?, ?)
                """;

        try(Connection conn = DatabaseCredentials.connectar();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){

            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()){
                usuario.setId(rs.getLong(1));
                return usuario;
            }
        }

        return null;
    }

    public List<Usuario> listarTodos() throws SQLException {

        List<Usuario> usuarios = new ArrayList<>();

        String query = """
                SELECT id,
                nome,
                email
                FROM usuario
                """;

        try(Connection conn = DatabaseCredentials.connectar(); PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Usuario usuario = new Usuario(rs.getLong("id"), rs.getString("nome"), rs.getString("email"));
                usuarios.add(usuario);
            }

        }

        return usuarios;
    }

    public Optional<Usuario> buscarPorID(Long id) {
        String query = """
                SELECT id,
                nome,
                email
                FROM usuario
                WHERE id = ?
                """;

        try(Connection conn = DatabaseCredentials.connectar(); PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                Usuario usuario = new Usuario(rs.getLong("id"), rs.getString("nome"), rs.getString("email"));
                return Optional.of(usuario);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;

    }

    public boolean verificarExistencia(Long id){
        String query = """
                SELECT COUNT(0)
                FROM usuario
                WHERE id = ?
                """;

        try(Connection conn = DatabaseCredentials.connectar(); PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return rs.getLong(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Optional<Usuario> atualizar(Long id, Usuario usuario) throws SQLException {
        String query = """
                UPDATE usuario SET nome = ?, email = ? WHERE id = ?
                """;

        try(Connection conn = DatabaseCredentials.connectar();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setLong(3, id);
            stmt.executeUpdate();
            usuario.setId(id);
            return Optional.of(usuario);
        }

    }

    public void deletar(Long id) throws SQLException {
        String query = """
                DELETE FROM usuario
                WHERE id = ?
                """;

        try(Connection conn = DatabaseCredentials.connectar(); PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setLong(1, id);
            stmt.executeUpdate();

        }

    }
}
