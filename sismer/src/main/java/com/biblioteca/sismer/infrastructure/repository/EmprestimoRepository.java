package com.biblioteca.sismer.infrastructure.repository;

import com.biblioteca.sismer.infrastructure.database.DatabaseCredentials;
import com.biblioteca.sismer.model.Emprestimo;
import com.biblioteca.sismer.model.Livro;
import com.biblioteca.sismer.model.Usuario;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class EmprestimoRepository {

    public Emprestimo registrarEmprestimo(Emprestimo emprestimo) throws SQLException {
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
                return emprestimo;
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
                Emprestimo emprestimo = new Emprestimo(rs.getLong("id"), livro, usuario, rs.getDate("data_emprestimo"), rs.getObject("data_devolucao", LocalDate.class));
                emprestimos.add(emprestimo);
            }
        }
        return emprestimos;
    }

    public Emprestimo buscarPorID(Long id) throws SQLException {
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
                Emprestimo emprestimo = new Emprestimo(rs.getLong("id"), livro, usuario, rs.getDate("data_emprestimo"), rs.getObject("data_devolucao", LocalDate.class));
                return emprestimo;
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

    public Emprestimo atualizar(Long id, Emprestimo emprestimo) throws SQLException {

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

            return new Emprestimo(id, livro, usuario, emprestimo.getDataEmprestimo());

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

    public Emprestimo registrarDevolucao(Long id, Date dataDevolucao) throws SQLException {

        String query = """
                UPDATE emprestimo
                SET data_devolucao = ?
                WHERE id = ?
                """;

        try(Connection conn = DatabaseCredentials.connectar();
        PreparedStatement stmt = conn.prepareStatement(query)){
            stmt.setDate(1, dataDevolucao);
            stmt.setLong(2, id);
            stmt.executeUpdate();
        }

        return new Emprestimo(id, dataDevolucao.toLocalDate());
    }
}
