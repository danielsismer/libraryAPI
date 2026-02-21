package com.biblioteca.sismer.dto.response;

import com.biblioteca.sismer.dto.request.UsuarioResquestDTO;
import com.biblioteca.sismer.model.Livro;
import com.biblioteca.sismer.model.Usuario;

import java.sql.Date;

public record EmprestimoResponseDTO(
        Long id,
        Livro livro,
        Usuario usuario,
        Date data_emprestimo,
        Date data_devolucao
) {

}
