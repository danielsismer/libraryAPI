package com.biblioteca.sismer.mapper;

import com.biblioteca.sismer.dto.request.UsuarioResquestDTO;
import com.biblioteca.sismer.dto.response.UsuarioResponseDTO;
import com.biblioteca.sismer.model.Usuario;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    public UsuarioResponseDTO toResponse(Usuario usuario){
        return new UsuarioResponseDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail()
        );
    }

    public Usuario toEntity(UsuarioResquestDTO usuarioResquestDTO){
        return new Usuario(
                usuarioResquestDTO.nome(),
                usuarioResquestDTO.email()
        );
    }

}
