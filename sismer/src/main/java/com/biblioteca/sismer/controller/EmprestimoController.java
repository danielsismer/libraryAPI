package com.biblioteca.sismer.controller;

import com.biblioteca.sismer.model.Emprestimo;
import com.biblioteca.sismer.service.EmprestimoService;
import org.apache.coyote.Request;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("emprestimos")
public class EmprestimoController {

    private final EmprestimoService emprestimoService;

    public EmprestimoController(EmprestimoService emprestimoService){
        this.emprestimoService = emprestimoService;
    }



    @PostMapping
    public Emprestimo registrarEmprestimo(@RequestBody Emprestimo emprestimo){
        try{
            return emprestimoService.registrarEmprestimo(emprestimo);
        } catch (SQLException e ){
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping
    public List<Emprestimo> listarTodos(){
        try{
            return emprestimoService.listarTodos();
        } catch (SQLException e ){
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Emprestimo buscarPorID(@PathVariable Long id){
        try{
            return emprestimoService.buscarPorID(id);
        } catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public Emprestimo atualizar(@PathVariable Long id, @RequestBody Emprestimo emprestimo){
        try{
            return emprestimoService.atualizar(id, emprestimo);
        } catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public void deletar(@PathVariable Long id) {

        try{
            emprestimoService.deletar(id);
        } catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }

    }

    @PutMapping("{id}/devolucao")
    public Emprestimo registrarDevolucao(@PathVariable Long id, @RequestBody Date dataDevolucao){
        try{
            return emprestimoService.registrarDevolucao(id, dataDevolucao);
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }

    }

}
