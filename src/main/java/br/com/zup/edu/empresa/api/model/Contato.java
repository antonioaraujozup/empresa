package br.com.zup.edu.empresa.api.model;

import javax.persistence.*;
import java.time.LocalDate;

@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UK_CONTATO_TELEFONE_DEPARTAMENTO", columnNames = {"telefone", "departamento_id"})
})
@Entity
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 14)
    private String telefone;

    @Column(nullable = false)
    private String nomeResponsavel;

    @Column(nullable = false)
    private LocalDate cadastradoEm;

    @ManyToOne(optional = false)
    private Departamento departamento;

    public Contato(String telefone, String nomeResponsavel, LocalDate cadastradoEm, Departamento departamento) {
        this.telefone = telefone;
        this.nomeResponsavel = nomeResponsavel;
        this.cadastradoEm = cadastradoEm;
        this.departamento = departamento;
    }

    /**
     * @deprecated Construtor para uso exclusivo do Hibernate.
     */
    @Deprecated
    public Contato() {
    }

    public Long getId() {
        return id;
    }

    public String getTelefone() {
        return telefone;
    }

    public Departamento getDepartamento() {
        return departamento;
    }
}
