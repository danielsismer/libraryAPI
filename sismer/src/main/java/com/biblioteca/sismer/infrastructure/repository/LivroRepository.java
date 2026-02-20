package com.biblioteca.sismer.infrastructure.repository;

import com.biblioteca.sismer.infrastructure.database.DatabaseCredentials;
import com.biblioteca.sismer.model.Livro;
import org.springframework.stereotype.Repository;

import javax.xml.crypto.Data;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LivroRepository {

    public Livro cadastrarLivro(Livro livro) {

        String query = """
                INSERT INTO livro(
                titulo,
                autor,
                ano_publicacao)
                VALUES (?,?,?)
                """;

        try(Connection conn = DatabaseCredentials.connectar(); PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getAno());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()){
                livro.setId(rs.getLong(1));
                return livro;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public List<Livro> listarTodos() {
        List<Livro> livros = new ArrayList<>();
        String query = """
                SELECT id, titulo, autor, ano_publicacao
                FROM livro
                """;

        try(Connection conn = DatabaseCredentials.connectar(); PreparedStatement stmt = conn.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Livro livro = new Livro(rs.getLong("id"), rs.getString("titulo"), rs.getString("autor"), rs.getInt("ano_publicacao"));
                livros.add(livro);
            }

            return livros;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Livro buscarPorID(Long id) {

        String query = """
                SELECT id,
                titulo,
                autor,
                ano_publicacao
                FROM livro
                WHERE id = ?
                """;

        try(Connection conn = DatabaseCredentials.connectar();PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return new Livro(rs.getLong("id"), rs.getString("titulo"), rs.getString("autor"), rs.getInt("ano_publicacao"));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return null;
    }

    public boolean vericarExistencia(Long id){
        String query = """
                SELECT COUNT(0)
                FROM livro WHERE id = ?
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

    public Livro atualizar(Long id, Livro livro) throws SQLException {
        String query = """
                UPDATE livro SET titulo = ?, autor = ?, ano_publicacao = ?
                WHERE id = ?
                """;

        try(Connection conn = DatabaseCredentials.connectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getAutor());
            stmt.setInt(3, livro.getAno());
            stmt.setLong(4, id);
            stmt.executeUpdate();

            return new Livro(id, livro.getTitulo(), livro.getAutor(), livro.getAno());
        }
    }

    public void deletar(Long id) {

        String query = """
                DELETE FROM livro
                WHERE id = ?
                """;

        try(Connection conn = DatabaseCredentials.connectar();
        PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verifyDependency(Long id){
        String query = """
                SELECT e.id, u.id FROM emprestimo e
                JOIN usuario u
                ON u.id = e.usuario_id
                WHERE u.id = ?
                """;
        try(Connection conn = DatabaseCredentials.connectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
