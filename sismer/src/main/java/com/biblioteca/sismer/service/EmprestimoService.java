package com.biblioteca.sismer.service;

import com.biblioteca.sismer.dto.DevolucaoDTO;
import com.biblioteca.sismer.infrastructure.repository.EmprestimoRepository;
import com.biblioteca.sismer.model.Emprestimo;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final UsuarioService usuarioService;
    private final LivroService livroService;

    public EmprestimoService (EmprestimoRepository emprestimoRepository, UsuarioService usuarioService, LivroService livroService){
        this.emprestimoRepository = emprestimoRepository;
        this.usuarioService = usuarioService;
        this.livroService = livroService;
    }

    public Emprestimo registrarEmprestimo(Emprestimo emprestimo) throws SQLException {

        if (emprestimoRepository.aceitarEmprestimo(emprestimo)){
            throw new RuntimeException("Não foi possível emprestar esse livro. Este livro já está em um emprestimo!!!");
        }

        emprestimo = emprestimoRepository.registrarEmprestimo(emprestimo);

        emprestimo.setUsuario(usuarioService.buscarPorID(emprestimo.getUsuario().getId()));
        emprestimo.setLivro(livroService.buscarPorID(emprestimo.getLivro().getId()));
        return emprestimo;
    }

    public List<Emprestimo> listarTodos() throws SQLException {

        List<Emprestimo> emprestimos = emprestimoRepository.listarTodos();

        for(Emprestimo emprestimo: emprestimos){
            emprestimo.setUsuario(usuarioService.buscarPorID(emprestimo.getUsuario().getId()));
            emprestimo.setLivro(livroService.buscarPorID(emprestimo.getLivro().getId()));
        }

        return emprestimos;
    }

    public Emprestimo buscarPorID(Long id) throws SQLException {

        Emprestimo emprestimo = emprestimoRepository.buscarPorID(id);

        if(emprestimo == null){
            throw new RuntimeException();
        }

        emprestimo.setLivro(livroService.buscarPorID(emprestimo.getLivro().getId()));
        emprestimo.setUsuario(usuarioService.buscarPorID(emprestimo.getUsuario().getId()));

        return emprestimo;
    }

    public Emprestimo atualizar(Long id, Emprestimo emprestimo) throws SQLException {

        Emprestimo emprestimoExistente = emprestimoRepository.buscarPorID(id);

        if (emprestimoExistente == null ){
            throw new RuntimeException();
        }

        emprestimo = emprestimoRepository.atualizar(id, emprestimo);
        emprestimo.setLivro(livroService.buscarPorID(emprestimo.getLivro().getId()));
        emprestimo.setUsuario(usuarioService.buscarPorID(emprestimo.getUsuario().getId()));

        return emprestimo;
    }

    public void deletar(Long id) throws SQLException {
        Emprestimo emprestimoExistente = emprestimoRepository.buscarPorID(id);

        if (emprestimoExistente == null ){
            throw new RuntimeException();
        }

        emprestimoRepository.deletar(id);
    }

    public Emprestimo registrarDevolucao(Long id, DevolucaoDTO dto) throws SQLException {

        Emprestimo emprestimoExistente = emprestimoRepository.buscarPorID(id);

        if (emprestimoExistente == null ){
            throw new RuntimeException();
        }

        emprestimoRepository.registrarDevolucao(id, dto);

        emprestimoExistente.setLivro(livroService.buscarPorID(emprestimoExistente.getLivro().getId()));
        emprestimoExistente.setUsuario(usuarioService.buscarPorID(emprestimoExistente.getUsuario().getId()));
        emprestimoExistente.setDataDevolucao(dto.getDataDevolucao());

        return emprestimoExistente;
    }

    public List<Emprestimo> emprestimosUsuario(Long id) throws SQLException {
        try{
            return emprestimoRepository.emprestimosUsuario(id);
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());

        }
    }
}
