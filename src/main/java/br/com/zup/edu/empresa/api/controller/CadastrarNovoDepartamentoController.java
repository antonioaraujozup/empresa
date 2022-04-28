package br.com.zup.edu.empresa.api.controller;

import br.com.zup.edu.empresa.api.model.Departamento;
import br.com.zup.edu.empresa.api.repository.DepartamentoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class CadastrarNovoDepartamentoController {

    private final DepartamentoRepository repository;

    public CadastrarNovoDepartamentoController(DepartamentoRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/departamentos")
    @Transactional
    public ResponseEntity<?> cadastrar(@RequestBody @Valid DepartamentoRequest request, UriComponentsBuilder uriComponentsBuilder) {

        if(repository.existsBySigla(request.getSigla())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Já existe no sistema um departamento cadastrado com a sigla informada");
        }

        Departamento departamento = request.toModel();

        repository.save(departamento);

        URI location = uriComponentsBuilder.path("/departamentos/{id}")
                .buildAndExpand(departamento.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }
}
