package br.com.zup.edu.empresa.api.controller;

import br.com.zup.edu.empresa.api.model.Contato;
import br.com.zup.edu.empresa.api.model.Departamento;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

public class ContatoRequest {

    @NotNull
    @Pattern(regexp = "^\\+[1-9][0-9]\\d{1,14}")
    private String telefone;

    @NotBlank
    private String nomeResponsavel;

    @NotNull
    @Past
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate cadastradoEm;

    public ContatoRequest(String telefone, String nomeResponsavel, LocalDate cadastradoEm) {
        this.telefone = telefone;
        this.nomeResponsavel = nomeResponsavel;
        this.cadastradoEm = cadastradoEm;
    }

    public ContatoRequest() {
    }

    public Contato toModel(Departamento departamento) {
        return new Contato(telefone, nomeResponsavel, cadastradoEm, departamento);
    }

    public String getTelefone() {
        return telefone;
    }

    public String getNomeResponsavel() {
        return nomeResponsavel;
    }

    public LocalDate getCadastradoEm() {
        return cadastradoEm;
    }
}
