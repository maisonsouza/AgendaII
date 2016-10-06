package com.maiso.agenda.modelo;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by maiso on 28/09/2016.
 */
public class Aluno implements Serializable {

    private Long id;
    private String nome;
    private String telefone;
    private String endereco;
    private String site;
    private Double nota;
    private String caminho_da_foto;

    public String getCaminho_da_foto() {
        return caminho_da_foto;
    }

    public void setCaminho_da_foto(String caminho_da_foto) {
        this.caminho_da_foto = caminho_da_foto;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    @Override
    public String toString() {
        return getId()+" - "+getNome();
    }
    }
