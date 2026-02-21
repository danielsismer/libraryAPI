package com.biblioteca.sismer.dto.response;

public record LivroResponseDTO(
        Long id,
        String titulo,
        String autor,
        int anoPublicacao
) {
}
