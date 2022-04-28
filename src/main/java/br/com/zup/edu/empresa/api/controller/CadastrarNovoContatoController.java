package br.com.zup.edu.empresa.api.controller;

import br.com.zup.edu.empresa.api.model.Contato;
import br.com.zup.edu.empresa.api.model.Departamento;
import br.com.zup.edu.empresa.api.repository.ContatoRepository;
import br.com.zup.edu.empresa.api.repository.DepartamentoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
public class CadastrarNovoContatoController {

    private final DepartamentoRepository departamentoRepository;
    private final ContatoRepository contatoRepository;

    public CadastrarNovoContatoController(DepartamentoRepository departamentoRepository, ContatoRepository contatoRepository) {
        this.departamentoRepository = departamentoRepository;
        this.contatoRepository = contatoRepository;
    }

    @PostMapping("/departamentos/{departamentoId}/contatos")
    @Transactional
    public ResponseEntity<?> cadastrar(@PathVariable Long departamentoId, @RequestBody @Valid ContatoRequest request,
                                       UriComponentsBuilder uriComponentsBuilder) {

        Departamento departamento = departamentoRepository.findById(departamentoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Departamento não encontrado"));

        if (contatoRepository.existsByTelefoneAndDepartamentoId(request.getTelefone(), departamento.getId())) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY,
                    "Número de telefone já cadastrado para esta empresa");
        }

        Contato contato = request.toModel(departamento);

        departamento.adicionar(contato);

        departamentoRepository.flush();

        URI location = uriComponentsBuilder.path("/departamentos/{departamentoId}/contatos/{contatoId}")
                .buildAndExpand(departamento.getId(), contato.getId())
                .toUri();

        return ResponseEntity.created(location).build();

    }
}
