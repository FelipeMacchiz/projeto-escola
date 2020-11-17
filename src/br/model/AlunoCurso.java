package br.model;

import javax.persistence.*;

@Entity
@Table(name = "alunoCurso")
@TableGenerator(
        name = "gerador_id_alunocurso",
        table = "sqlite_sequence",
        pkColumnName = "name",
        valueColumnName = "seq",
        pkColumnValue = "alunoCurso",
        initialValue = 1,
        allocationSize = 1
)
public class AlunoCurso {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gerador_id_alunocurso")
    @JoinColumn(
            foreignKey = @ForeignKey(name = "codAluno", value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "codAluno", nullable = false)
    private int codAluno;

    @JoinColumn(
            foreignKey = @ForeignKey(name = "codCurso", value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "codCurso", nullable = false)
    private int codCurso;

    public AlunoCurso() {
    }
    public AlunoCurso(int codCurso) {
        this.codCurso = codCurso;
    }
    public AlunoCurso(int codAluno, int codCurso) {
        this.codAluno = codAluno;
        this.codCurso = codCurso;
    }

    public int getCodAluno() {
        return codAluno;
    }
    public void setCodAluno(int codAluno) {
        this.codAluno = codAluno;
    }

    public int getCodCurso() {
        return codCurso;
    }
    public void setCodCurso(int codCurso) {
        this.codCurso = codCurso;
    }

}
