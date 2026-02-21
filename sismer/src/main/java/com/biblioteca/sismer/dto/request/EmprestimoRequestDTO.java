package com.biblioteca.sismer.dto.request;

import com.biblioteca.sismer.dto.response.LivroResponseDTO;
import com.biblioteca.sismer.dto.response.UsuarioResponseDTO;
import com.biblioteca.sismer.model.Livro;
import com.biblioteca.sismer.model.Usuario;

import java.sql.Date;

public record EmprestimoRequestDTO(
        Livro livro,
        Usuario usuario,
        Date data_emprestimo,
        Date data_devolucao
) {
}
