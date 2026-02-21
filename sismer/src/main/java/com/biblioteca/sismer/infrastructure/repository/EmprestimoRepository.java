package com.biblioteca.sismer.infrastructure.repository;

import com.biblioteca.sismer.dto.DevolucaoDTO;
import com.biblioteca.sismer.infrastructure.database.DatabaseCredentials;
import com.biblioteca.sismer.model.Emprestimo;
import com.biblioteca.sismer.model.Livro;
import com.biblioteca.sismer.model.Usuario;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class EmprestimoRepository {

    private UsuarioRepository usuarioRepository;
    private LivroRepository livroRepository;

    public Optional<Emprestimo> registrarEmprestimo(Emprestimo emprestimo) throws SQLException {
        String query = """
                INSERT INTO emprestimo (livro_id, usuario_id, data_emprestimo)
                VALUES (?, ?, ?)
                """;

        try(Connection conn = DatabaseCredentials.connectar();
            PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            stmt.setLong(1, emprestimo.getLivro().getId());
            stmt.setLong(2, emprestimo.getUsuario().getId());
            stmt.setDate(3, emprestimo.getDataEmprestimo());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()){
                emprestimo.setId(rs.getLong(1));
                return Optional.of(emprestimo);
            }
        }

        return null;
    }

    public List<Emprestimo> listarTodos() throws SQLException {

        List<Emprestimo> emprestimos = new ArrayList<>();

        String query = """
                SELECT id, livro_id, usuario_id, data_emprestimo, data_devolucao
                FROM emprestimo
                """;

        try(Connection conn = DatabaseCredentials.connectar(); PreparedStatement stmt = conn.prepareStatement(query)){

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Livro livro = new Livro(rs.getLong("livro_id"));
                Usuario usuario = new Usuario(rs.getLong("usuario_id"));
                Emprestimo emprestimo = new Emprestimo(rs.getLong("id"), livro, usuario, rs.getDate("data_emprestimo"), rs.getDate("data_devolucao"));
                emprestimos.add(emprestimo);
            }
        }
        return emprestimos;
    }

    public Optional<Emprestimo> buscarPorID(Long id) throws SQLException {
        String query = """
                SELECT id, livro_id, usuario_id, data_emprestimo, data_devolucao
                FROM emprestimo WHERE id = ?
                """;

        try(Connection conn = DatabaseCredentials.connectar(); PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                Livro livro = new Livro(rs.getLong("livro_id"));
                Usuario usuario = new Usuario(rs.getLong("usuario_id"));
                Emprestimo emprestimo = new Emprestimo(rs.getLong("id"), livro, usuario, rs.getDate("data_emprestimo"), rs.getDate("data_devolucao"));
                return Optional.of(emprestimo);
            }
        }
        return null;
    }

    public boolean verificarExistencia(Long id) throws SQLException {
        String query = """
                SELECT COUNT(0) FROM emprestimo WHERE id = ?
                """;

        try(Connection conn = DatabaseCredentials.connectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return rs.getLong(1) > 0;
            }

        }

        return false;
    }

    public Optional<Emprestimo> atualizar(Long id, Emprestimo emprestimo) throws SQLException {

        String query = """
                UPDATE emprestimo
                SET livro_id = ?,
                usuario_id = ?,
                data_emprestimo = ?
                WHERE id = ?
                """;

        try(Connection conn = DatabaseCredentials.connectar();
        PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setLong(1, emprestimo.getLivro().getId());
            stmt.setLong(2, emprestimo.getUsuario().getId());
            stmt.setDate(3, emprestimo.getDataEmprestimo());
            stmt.setLong(4, id);
            stmt.executeUpdate();

            Livro livro = new Livro(emprestimo.getLivro().getId());
            Usuario usuario = new Usuario(emprestimo.getUsuario().getId());

            emprestimo = new Emprestimo(id, livro, usuario, emprestimo.getDataEmprestimo(), null);

            return Optional.of(emprestimo);
        }
    }

    public void deletar(Long id) throws SQLException {

        String query = """
                DELETE FROM emprestimo WHERE id = ?
                """;

        try (Connection conn = DatabaseCredentials.connectar();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setLong(1, id);
            stmt.executeUpdate();
        }
    }

    public Optional<Emprestimo> registrarDevolucao(Long id, DevolucaoDTO dto) throws SQLException {

        String query = """
                UPDATE emprestimo
                SET data_devolucao = ?
                WHERE id = ?
                """;

        try(Connection conn = DatabaseCredentials.connectar();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setDate(1, dto.getDataDevolucao());
            stmt.setLong(2, id);
            stmt.executeUpdate();

            return Optional.of(new Emprestimo(id, dto.getDataDevolucao()));

        }

    }

    public List<Emprestimo> emprestimosUsuario(Long id) throws SQLException {

        List<Emprestimo> emprestimos = new ArrayList<>();

        String query = """
                SELECT e.id, e.livro_id, e.usuario_id, e.data_emprestimo, e.data_devolucao
                FROM emprestimo e
                JOIN usuario u
                ON u.id = e.usuario_id
                WHERE u.id = ?
                """;

        try(Connection conn = DatabaseCredentials.connectar();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setLong(1, id);

            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Livro livro = new Livro(rs.getLong("livro_id"));
                Usuario usuario = new Usuario(rs.getLong("usuario_id"));

                emprestimos.add(new Emprestimo(rs.getLong("id"), livro, usuario, rs.getDate("data_emprestimo"), rs.getDate("data_devolucao")));
            }
        }

        return emprestimos;
    }

    public boolean aceitarEmprestimo(Emprestimo emprestimo) {
        String query = """
                SELECT e.id, u.id FROM emprestimo e
                JOIN usuario u
                ON u.id = e.usuario_id
                WHERE id = ?
                """;

        try(Connection conn = DatabaseCredentials.connectar();
            PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setLong(1, emprestimo.getLivro().getId());

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
