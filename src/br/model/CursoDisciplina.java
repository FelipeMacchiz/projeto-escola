package br.model;

import javax.persistence.*;

@Entity
@Table(name = "cursoDisciplina")
@TableGenerator(
        name = "gerador_id_cursodisc",
        table = "sqlite_sequence",
        pkColumnName = "name",
        valueColumnName = "seq",
        pkColumnValue = "cursoDisciplina",
        initialValue = 1,
        allocationSize = 1
)
public class CursoDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gerador_id_cursodisc")
    @JoinColumn(
            foreignKey = @ForeignKey(name = "codDisciplina", value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "codDisciplina", nullable = false)
    private int codDisciplina;

    @JoinColumn(
            foreignKey = @ForeignKey(name = "codCurso", value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "codCurso", nullable = false)
    private int codCurso;

    public CursoDisciplina() {
    }
    public CursoDisciplina(int codCurso) {
        this.codCurso = codCurso;
    }
    public CursoDisciplina(int codDisciplina, int codCurso) {
        this.codDisciplina = codDisciplina;
        this.codCurso = codCurso;
    }

    public int getCodCurso() {
        return codCurso;
    }
    public void setCodCurso(int codCurso) {
        this.codCurso = codCurso;
    }

    public int getCodDisciplina() {
        return codDisciplina;
    }
    public void setCodDisciplina(int codDisciplina) {
        this.codDisciplina = codDisciplina;
    }

}
