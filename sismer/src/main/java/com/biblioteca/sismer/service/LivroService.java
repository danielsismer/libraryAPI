package com.biblioteca.sismer.service;

import com.biblioteca.sismer.infrastructure.repository.LivroRepository;
import com.biblioteca.sismer.model.Livro;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class LivroService {

    private final LivroRepository livroRepository;

    public LivroService(LivroRepository livroRepository){
        this.livroRepository = livroRepository;
    }

    public Livro cadastrarLivro(Livro livro) {
        return livroRepository.cadastrarLivro(livro);
    }

    public List<Livro> listarTodos() {
        return livroRepository.listarTodos();
    }

    public Livro buscarPorID(Long id) {
        return livroRepository.buscarPorID(id);
    }

    public Livro atualizar(Long id, Livro livro) throws SQLException {

        if(!livroRepository.vericarExistencia(id)){
            throw new RuntimeException();
        }

        return livroRepository.atualizar(id, livro);
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
