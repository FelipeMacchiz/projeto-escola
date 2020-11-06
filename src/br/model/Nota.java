package br.model;

import javax.persistence.*;

@Entity
@Table(name = "nota")
@TableGenerator(
        name = "gerador_id_nota",
        table = "sqlite_sequence",
        pkColumnName = "name",
        valueColumnName = "seq",
        pkColumnValue = "nota",
        initialValue = 1,
        allocationSize = 1
)
public class Nota {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gerador_id_nota")
    @Column(name = "codNota", nullable = false)
    private int codNota;

    @JoinColumn (
            foreignKey = @ForeignKey(name = "CodAluno", value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "codAluno", nullable = false)
    private int codAluno;

    @Column(name = "codDisciplina", nullable = false)
    private int codDisciplina;

    @Column(name = "nota", nullable = false)
    private double nota;

    public Nota() {
    }
    public Nota(int codAluno, int codDisciplina, double nota) {
        this.codAluno = codAluno;
        this.codDisciplina = codDisciplina;
        this.nota = nota;
    }
    public Nota(int codNotas, int codDisciplina, int codAluno, double nota) {
        this.codNota = codNotas;
        this.codAluno = codAluno;
        this.codDisciplina = codDisciplina;
        this.nota = nota;
    }

    public int getCodNota() {
        return codNota;
    }
    public void setCodNota(int codNota) {
        this.codNota = codNota;
    }

    public int getCodAluno() {
        return codAluno;
    }
    public void setCodAluno(int codAluno) {
        this.codAluno = codAluno;
    }

    public int getCodDisciplina() {
        return codDisciplina;
    }
    private void setCodDisciplina(int codDisciplina) {
        this.codDisciplina = codDisciplina;
    }

    public double getNota() {
        return nota;
    }
    public void setNota(double nota) {
        this.nota = nota;
    }

}
