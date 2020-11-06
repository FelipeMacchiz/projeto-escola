package br.model;

import javax.persistence.*;

@Entity
@Table(name = "disciplina")
@TableGenerator(
        name = "gerador_id_disciplina",
        table = "sqlite_sequence",
        pkColumnName = "name",
        valueColumnName = "seq",
        pkColumnValue = "disciplina",
        initialValue = 1,
        allocationSize = 1
)
public class Disciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gerador_id_disciplina")
    @Column(name = "codDisciplina", nullable = false)
    private int codDisciplina;

    @Column(name = "nome", nullable = false)
    private String nome;

    public Disciplina() {
    }
    public Disciplina(String nome) {
        this.nome = nome;
    }
    public Disciplina(int codDisciplina, String nome) {
        this.codDisciplina = codDisciplina;
        this.nome = nome;
    }

    public int getCodDisciplina() {
        return codDisciplina;
    }
    public void setCodDisciplina(int codDisciplina) {
        this.codDisciplina = codDisciplina;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

}
