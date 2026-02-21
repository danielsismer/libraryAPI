package com.biblioteca.sismer.mapper;

import com.biblioteca.sismer.dto.request.LivroRequestDTO;
import com.biblioteca.sismer.dto.response.LivroResponseDTO;
import com.biblioteca.sismer.model.Livro;
import org.springframework.stereotype.Component;

@Component
public class LivroMapper {

    public LivroResponseDTO toResponse(Livro livro){
        return new LivroResponseDTO(
                livro.getId(),
                livro.getTitulo(),
                livro.getAutor(),
                livro.getAno()
        );
    }

    public Livro toEntity (LivroRequestDTO livroRequestDTO){
        return new Livro(
                livroRequestDTO.titulo(),
                livroRequestDTO.autor(),
                livroRequestDTO.anoPublicacao()
        );
    }
}
