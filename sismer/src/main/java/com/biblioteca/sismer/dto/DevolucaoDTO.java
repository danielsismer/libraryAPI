package com.biblioteca.sismer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class DevolucaoDTO {

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dataDevolucao;

    public DevolucaoDTO(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

    public DevolucaoDTO(){
    }

    public Date getDataDevolucao() {
        return dataDevolucao;
    }

    public void setDataDevolucao(Date dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }
}
