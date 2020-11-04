package br.model;

import javax.persistence.*;

@Entity
@Table(name = "cursoDisciplina")
@TableGenerator(
        name = "gerador_id",
        table = "sqlite_sequence",
        pkColumnName = "name",
        valueColumnName = "seq",
        pkColumnValue = "cursoDisciplina",
        initialValue = 1,
        allocationSize = 1
)
public class CursoDisciplina {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gerador_id")
    @Column(name = "codCursoDisciplina", nullable = false)
    private int codCursoDisciplina;

    @JoinColumn(
            foreignKey = @ForeignKey(name = "codCurso", value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "codCurso", nullable = false)
    private int codCurso;

    @JoinColumn(
            foreignKey = @ForeignKey(name = "codDisciplina", value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "codDisciplina", nullable = false)
    private int codDisciplina;

    public CursoDisciplina() {
    }
    public CursoDisciplina(int codCurso, int codDisciplina) {
        this.codCurso = codCurso;
        this.codDisciplina = codDisciplina;
    }
    public CursoDisciplina(int codCursoDisciplina, int codCurso, int codDisciplina) {
        this.codCursoDisciplina = codCursoDisciplina;
        this.codCurso = codCurso;
        this.codDisciplina = codDisciplina;
    }

    public int getCodCursoDisciplina() {
        return codCursoDisciplina;
    }
    public void setCodCursoDisciplina(int codCursoDisciplina) {
        this.codCursoDisciplina = codCursoDisciplina;
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
