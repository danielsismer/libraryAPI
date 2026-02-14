package com.biblioteca.sismer.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.time.LocalDate;

public class Emprestimo {

    private Long id;
    private Livro livro;
    private Usuario usuario;
    private Date dataEmprestimo;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dataDevolucao;

    public Emprestimo() {}

    public Emprestimo(Long id, Livro livro, Usuario usuario, Date dataEmprestimo, LocalDate dataDevolucao) {
        this.id = id;
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    public Emprestimo(Long id, Livro livro, Usuario usuario, Date dataEmprestimo) {
        this.id = id;
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
    }

    public Emprestimo(Long id, LocalDate dataDevolucao) {
        this.id = id;
        this.dataDevolucao = dataDevolucao;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public Date getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(Date dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }
}