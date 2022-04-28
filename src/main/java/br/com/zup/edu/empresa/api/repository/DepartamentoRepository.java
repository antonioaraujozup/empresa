package br.com.zup.edu.empresa.api.repository;

import br.com.zup.edu.empresa.api.model.Departamento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartamentoRepository extends JpaRepository<Departamento,Long> {
    boolean existsBySigla(String sigla);
}
