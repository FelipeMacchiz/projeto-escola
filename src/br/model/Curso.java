package br.model;

import javax.persistence.*;

@Entity
@Table(name = "curso")
@TableGenerator(
        name = "gerador_id_curso",
        table = "sqlite_sequence",
        pkColumnName = "name",
        valueColumnName = "seq",
        pkColumnValue = "curso",
        initialValue = 1,
        allocationSize = 1
)
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gerador_id_curso")
    @Column(name = "codCurso", nullable = false)
    private int codCurso;

    @Column(name = "nomeCurso", nullable = false)
    private String nomeCurso;

    @Column(name = "duracao")
    private int duracao;

    public Curso() {
    }
    public Curso(String nomeCurso, int duracao) {
        this.nomeCurso = nomeCurso;
        this.duracao = duracao;
    }
    public Curso(int codCurso, String nomeCurso, int duracao) {
        this.codCurso = codCurso;
        this.nomeCurso = nomeCurso;
        this.duracao = duracao;
    }

    public int getCodCurso() {
        return codCurso;
    }
    public void setCodCurso(int codCurso) {
        this.codCurso = codCurso;
    }

    public String getNomeCurso() {
        return nomeCurso;
    }
    public void setNomeCurso(String nomeCurso) {
        this.nomeCurso = nomeCurso;
    }

    public int getDuracao() {
        return duracao;
    }
    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

}
