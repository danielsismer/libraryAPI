package com.biblioteca.sismer.service;

import com.biblioteca.sismer.dto.request.UsuarioResquestDTO;
import com.biblioteca.sismer.dto.response.UsuarioResponseDTO;
import com.biblioteca.sismer.infrastructure.repository.UsuarioRepository;
import com.biblioteca.sismer.mapper.UsuarioMapper;

import com.biblioteca.sismer.model.Usuario;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioMapper usuarioMapper;
    private final UsuarioRepository usuarioRepository;

    public  UsuarioService (UsuarioMapper usuarioMapper, UsuarioRepository usuarioRepository){
        this.usuarioMapper = usuarioMapper;
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioResponseDTO cadastrarUsuario(UsuarioResquestDTO usuarioRequest) throws SQLException {

        Usuario usuario = usuarioMapper.toEntity(usuarioRequest);

        return usuarioMapper.toResponse(usuarioRepository.cadastrarUsuario(usuario));
    }

    public List<UsuarioResponseDTO> listarTodos() throws SQLException {

        return usuarioRepository.listarTodos()
                .stream()
                .map(usuarioMapper::toResponse)
                .collect(Collectors.toList());
    }

    public UsuarioResponseDTO buscarPorID(Long id) throws SQLException{
        return usuarioRepository.buscarPorID(id)
                .map(usuarioMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com ID: " + id));
    }

    public UsuarioResponseDTO atualizar(Long id, UsuarioResquestDTO usuarioResquest) throws SQLException {

        if(!usuarioRepository.verificarExistencia(id)){
            throw new RuntimeException();
        }

        Usuario usuario = usuarioMapper.toEntity(usuarioResquest);

        return usuarioRepository.atualizar(id, usuario)
                .map(usuarioMapper::toResponse)
                .orElseThrow(() -> new RuntimeException(""));

    }

    public void deletar(Long id) throws SQLException {

        if(!usuarioRepository.verificarExistencia(id)){
            throw new RuntimeException();
        }

        usuarioRepository.deletar(id);
    }
}
