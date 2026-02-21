package com.biblioteca.sismer.controller;

import com.biblioteca.sismer.dto.DevolucaoDTO;
import com.biblioteca.sismer.dto.request.EmprestimoRequestDTO;
import com.biblioteca.sismer.dto.response.EmprestimoResponseDTO;
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
    public EmprestimoResponseDTO registrarEmprestimo(@RequestBody EmprestimoRequestDTO emprestimo){
        try{
            return emprestimoService.registrarEmprestimo(emprestimo);
        } catch (SQLException e ){
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping
    public List<EmprestimoResponseDTO> listarTodos(){
        try{
            return emprestimoService.listarTodos();
        } catch (SQLException e ){
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public EmprestimoResponseDTO buscarPorID(@PathVariable Long id){
        try{
            return emprestimoService.buscarPorID(id);
        } catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public EmprestimoResponseDTO atualizar(@PathVariable Long id, @RequestBody EmprestimoRequestDTO emprestimo){
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
    public EmprestimoResponseDTO registrarDevolucao(@PathVariable Long id, @RequestBody DevolucaoDTO dto){
        try{
            return emprestimoService.registrarDevolucao(id, dto);
        }catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }

    }

    @GetMapping("usuario/{id}")
    public List<EmprestimoResponseDTO> emprestimosUsuario(@PathVariable Long id){
        try{
            return emprestimoService.emprestimosUsuario(id);
        } catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }
    }

}
