package com.biblioteca.sismer.model;

public class Livro {

    private Long id;
    private String titulo;
    private String autor;
    private int ano;

    public Livro() {
    }

    public Livro(Long id, String titulo, String autor, int ano) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
    }

    public Livro(String titulo, String autor, int ano) {
        this.titulo = titulo;
        this.autor = autor;
        this.ano = ano;
    }

    public Livro(long livroId) {
        this.id = livroId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    @Override
    public String toString() {
        return """
        \
    Livro{
        id=$id,
        titulo='$titulo',
        autor='$autor',
     ano=$ano
    }""";
    }
}
