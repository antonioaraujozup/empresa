package br.com.zup.edu.empresa.api.model;

import javax.persistence.*;
import java.util.List;

@Table(uniqueConstraints = {
        @UniqueConstraint(name = "UK_DEPARTAMENTO_SIGLA", columnNames = {"sigla"})
})
@Entity
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 120)
    private String nome;

    @Column(nullable = false, length = 3)
    private String sigla;

    @OneToMany(
            mappedBy = "departamento",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE},
            orphanRemoval = true
    )
    private List<Contato> contatos;

    public Departamento(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }

    /**
     * @deprecated Construtor para uso exclusivo do Hibernate.
     */
    @Deprecated
    public Departamento() {
    }

    public void adicionar(Contato contato) {
        this.contatos.add(contato);
    }

    public Long getId() {
        return id;
    }
}
