package com.biblioteca.sismer.controller;

import com.biblioteca.sismer.model.Livro;
import com.biblioteca.sismer.service.LivroService;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/livros")
public class LivroController {

    private final LivroService livroService;

    public LivroController (LivroService livroService){
        this.livroService = livroService;
    }

    @PostMapping
    public Livro cadastrarLivro(@RequestBody Livro livro){
        try{
            return livroService.cadastrarLivro(livro);
        } catch (RuntimeException e){
            throw new RuntimeException();
        }
    }

    @GetMapping
    public List<Livro> listarTodos(){
        try{
            return livroService.listarTodos();
        } catch (RuntimeException e){
            throw new RuntimeException();
        }
    }

    @GetMapping("/{id}")
    public Livro buscarPorID(@PathVariable Long id){
        try{
            return livroService.buscarPorID(id);
        } catch (RuntimeException e ){
            throw new RuntimeException();
        }
    }

    @PutMapping("/{id}")
    public Livro atualizar(@PathVariable Long id, @RequestBody Livro livro){
        try{
            return livroService.atualizar(id, livro);
        }catch (SQLException | RuntimeException e){
            throw new RuntimeException();
        }
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id){

        try{
            livroService.deletar(id);
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }

    }
}
