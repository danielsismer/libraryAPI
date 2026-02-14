package com.biblioteca.sismer.service;

import com.biblioteca.sismer.infrastructure.repository.UsuarioRepository;
import com.biblioteca.sismer.model.Livro;
import com.biblioteca.sismer.model.Usuario;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public  UsuarioService (UsuarioRepository usuarioRepository){
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario cadastrarUsuario(Usuario usuario) throws SQLException {
        return usuarioRepository.cadastrarUsuario(usuario);
    }

    public List<Usuario> listarTodos() throws SQLException {
        return usuarioRepository.listarTodos();
    }

    public Usuario buscarPorID(Long id) throws SQLException{
        return usuarioRepository.buscarPorID(id);
    }

    public Usuario atualizar(Long id, Usuario usuario) throws SQLException {

        if(!usuarioRepository.verificarExistencia(id)){
            throw new RuntimeException();
        }

        return usuarioRepository.atualizar(id, usuario);
    }

    public void deletar(Long id) throws SQLException {

        if(!usuarioRepository.verificarExistencia(id)){
            throw new RuntimeException();
        }

        usuarioRepository.deletar(id);
    }
}
