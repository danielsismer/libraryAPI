package com.biblioteca.sismer.service;

import com.biblioteca.sismer.infrastructure.repository.EmprestimoRepository;
import com.biblioteca.sismer.model.Emprestimo;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;

    public EmprestimoService (EmprestimoRepository emprestimoRepository){
        this.emprestimoRepository = emprestimoRepository;
    }

    public Emprestimo registrarEmprestimo(Emprestimo emprestimo) throws SQLException {
        return emprestimoRepository.registrarEmprestimo(emprestimo);
    }

    public List<Emprestimo> listarTodos() throws SQLException {
        return emprestimoRepository.listarTodos();
    }

    public Emprestimo buscarPorID(Long id) throws SQLException {
        return emprestimoRepository.buscarPorID(id);
    }

    public Emprestimo atualizar(Long id, Emprestimo emprestimo) throws SQLException {

        Emprestimo emprestimoExistente = emprestimoRepository.buscarPorID(id);

        if (emprestimoExistente == null ){
            throw new RuntimeException();
        }


        return emprestimoRepository.atualizar(id, emprestimo);
    }

    public void deletar(Long id) throws SQLException {
        Emprestimo emprestimoExistente = emprestimoRepository.buscarPorID(id);

        if (emprestimoExistente == null ){
            throw new RuntimeException();
        }

        emprestimoRepository.deletar(id);
    }

    public Emprestimo registrarDevolucao(Long id, Date dataDevolucao) throws SQLException {

        Emprestimo emprestimoExistente = emprestimoRepository.buscarPorID(id);

        if (emprestimoExistente == null ){
            throw new RuntimeException();
        }

        return emprestimoRepository.registrarDevolucao(id, dataDevolucao);
    }
}
