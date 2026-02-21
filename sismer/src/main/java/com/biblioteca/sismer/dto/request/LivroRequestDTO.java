package com.biblioteca.sismer.dto.request;

public record LivroRequestDTO(
        String titulo,
        String autor,
        int anoPublicacao
) {
}
