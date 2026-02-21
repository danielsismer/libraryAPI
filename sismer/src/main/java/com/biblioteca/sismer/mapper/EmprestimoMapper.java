package com.biblioteca.sismer.mapper;

import com.biblioteca.sismer.dto.request.EmprestimoRequestDTO;
import com.biblioteca.sismer.dto.response.EmprestimoResponseDTO;
import com.biblioteca.sismer.model.Emprestimo;
import org.springframework.stereotype.Component;

@Component
public class EmprestimoMapper {

    public EmprestimoResponseDTO toResponse(Emprestimo emprestimo){
        return new EmprestimoResponseDTO(
                emprestimo.getId(),
                emprestimo.getLivro(),
                emprestimo.getUsuario(),
                emprestimo.getDataEmprestimo(),
                emprestimo.getDataDevolucao()
        );
    }

    public Emprestimo toEntity(EmprestimoRequestDTO emprestimoRequestDTO){
        return new Emprestimo(
                emprestimoRequestDTO.livro(),
                emprestimoRequestDTO.usuario(),
                emprestimoRequestDTO.data_emprestimo(),
                emprestimoRequestDTO.data_devolucao()
        );
    }
}
