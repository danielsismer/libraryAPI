package com.biblioteca.sismer.service;

import com.biblioteca.sismer.dto.request.LivroRequestDTO;
import com.biblioteca.sismer.dto.response.LivroResponseDTO;
import com.biblioteca.sismer.infrastructure.repository.LivroRepository;
import com.biblioteca.sismer.mapper.LivroMapper;
import com.biblioteca.sismer.model.Livro;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LivroService {

    private final LivroRepository livroRepository;
    private final LivroMapper livroMapper;

    public LivroService(LivroRepository livroRepository, LivroMapper livroMapper){
        this.livroRepository = livroRepository;
        this.livroMapper = livroMapper;
    }

    public LivroResponseDTO cadastrarLivro(LivroRequestDTO livroRequest) {

        Livro livro = livroMapper.toEntity(livroRequest);

        return livroRepository.cadastrarLivro(livro)
                .map(livroMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Não foi possível salvar no banco"));

    }

    public List<LivroResponseDTO> listarTodos() {
        return livroRepository.listarTodos()
                .stream().map(livroMapper::toResponse)
                .collect(Collectors.toList());
    }

    public LivroResponseDTO buscarPorID(Long id) {
        return livroRepository.buscarPorID(id)
                .map(livroMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Erro ao Buscar o ID " + id));
    }

    public LivroResponseDTO atualizar(Long id, LivroRequestDTO livroRequestDTO) throws SQLException {

        if(!livroRepository.vericarExistencia(id)){
            throw new RuntimeException();
        }

        Livro livro = livroMapper.toEntity(livroRequestDTO);

        return livroRepository.atualizar(id, livro)
                .map(livroMapper::toResponse)
                .orElseThrow(() -> new RuntimeException(""))
                ;
    }

    public void deletar(Long id) {

        if(!livroRepository.vericarExistencia(id)){
            throw new RuntimeException();
        }

        if(livroRepository.verifyDependency(id)){
            throw new RuntimeException("Existe um Usuário com este livro!!!");
        }

        livroRepository.deletar(id);
    }

}
