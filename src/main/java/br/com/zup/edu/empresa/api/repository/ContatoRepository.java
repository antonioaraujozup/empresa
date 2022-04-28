package br.com.zup.edu.empresa.api.repository;

import br.com.zup.edu.empresa.api.model.Contato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContatoRepository extends JpaRepository<Contato,Long> {
    boolean existsByTelefoneAndDepartamentoId(String telefone, Long id);
}
