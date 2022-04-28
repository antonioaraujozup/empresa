package br.com.zup.edu.empresa.api.controller;

import br.com.zup.edu.empresa.api.model.Departamento;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class DepartamentoRequest {

    @NotBlank
    @Size(max = 120)
    private String nome;

    @NotNull
    @Pattern(regexp = "[0-9A-Z]{1,3}")
    private String sigla;

    public DepartamentoRequest(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }

    public DepartamentoRequest() {
    }

    public Departamento toModel() {
        return new Departamento(nome,sigla);
    }

    public String getNome() {
        return nome;
    }

    public String getSigla() {
        return sigla;
    }
}
