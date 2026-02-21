package com.biblioteca.sismer.service;

import com.biblioteca.sismer.dto.DevolucaoDTO;
import com.biblioteca.sismer.dto.request.EmprestimoRequestDTO;
import com.biblioteca.sismer.dto.response.EmprestimoResponseDTO;
import com.biblioteca.sismer.infrastructure.repository.EmprestimoRepository;
import com.biblioteca.sismer.mapper.EmprestimoMapper;
import com.biblioteca.sismer.model.Emprestimo;
import org.springframework.stereotype.Service;


import java.sql.SQLException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmprestimoService {

    private final EmprestimoRepository emprestimoRepository;
    private final EmprestimoMapper emprestimoMapper;

    public EmprestimoService (EmprestimoRepository emprestimoRepository, UsuarioService usuarioService, LivroService livroService, EmprestimoMapper emprestimoMapper){
        this.emprestimoRepository = emprestimoRepository;
        this.emprestimoMapper = emprestimoMapper;
    }

    public EmprestimoResponseDTO registrarEmprestimo(EmprestimoRequestDTO emprestimoRequest) throws SQLException {

        Emprestimo emprestimo = emprestimoMapper.toEntity(emprestimoRequest);

        return emprestimoRepository.registrarEmprestimo(emprestimo)
                .map(emprestimoMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Erro ao salvar no Banco"));

    }

    public List<EmprestimoResponseDTO> listarTodos() throws SQLException {
        return emprestimoRepository.listarTodos()
                .stream()
                .map(emprestimoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public EmprestimoResponseDTO buscarPorID(Long id) throws SQLException {

        return emprestimoRepository.buscarPorID(id)
                .map(emprestimoMapper::toResponse)
                .orElseThrow(() -> new RuntimeException(""));

    }

    public EmprestimoResponseDTO atualizar(Long id, EmprestimoRequestDTO emprestimoRequest) throws SQLException {

        Emprestimo emprestimo = emprestimoMapper.toEntity(emprestimoRequest);

        return emprestimoRepository.atualizar(id, emprestimo)
                .map(emprestimoMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Erro ao buscar ID " + id));
    }

    public void deletar(Long id) throws SQLException {
       Optional<Emprestimo> emprestimoExistente = emprestimoRepository.buscarPorID(id);

        if (emprestimoExistente.isEmpty()){
            throw new RuntimeException("ID inexistente");
        }

        emprestimoRepository.deletar(id);
    }

    public EmprestimoResponseDTO registrarDevolucao(Long id, DevolucaoDTO dto) throws SQLException {

        Optional<Emprestimo> emprestimoExistente = emprestimoRepository.buscarPorID(id);

        if (emprestimoExistente.isEmpty()){
            throw new RuntimeException();
        }

        return emprestimoRepository.registrarDevolucao(id, dto)
                .map(emprestimoMapper::toResponse)
                .orElseThrow(() -> new RuntimeException(""));
    }

    public List<EmprestimoResponseDTO> emprestimosUsuario(Long id) throws SQLException {
        try{
            return emprestimoRepository.emprestimosUsuario(id)
                    .stream().map(emprestimoMapper::toResponse)
                    .collect(Collectors.toList());
        } catch (SQLException e){
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());

        }
    }
}
