package com.biblioteca.sismer.controller;

import com.biblioteca.sismer.model.Livro;
import com.biblioteca.sismer.model.Usuario;
import com.biblioteca.sismer.service.UsuarioService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService){
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public Usuario cadastrarUsuario(@RequestBody Usuario usuario){

        try{
            return usuarioService.cadastrarUsuario(usuario);
        } catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping
    public List<Usuario> listarTodos(){
        try{
            return usuarioService.listarTodos();
        } catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Usuario buscarPorID(@PathVariable Long id){

        try{
            return usuarioService.buscarPorID(id);
        } catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Usuario atualizar(@RequestBody Usuario usuario, @PathVariable Long id){

        try{
            return usuarioService.atualizar(id, usuario);
        } catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public void deletar(@PathVariable Long id){
        try{
            usuarioService.deletar(id);
        } catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
