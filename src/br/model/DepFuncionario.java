package br.model;

import javax.persistence.*;

@Entity
@Table(name = "depFuncionario")
@TableGenerator(
        name = "gerador_id_depfun",
        table = "sqlite_sequence",
        pkColumnName = "name",
        valueColumnName = "seq",
        pkColumnValue = "depFuncionario",
        initialValue = 1,
        allocationSize = 1
)
public class DepFuncionario {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gerador_id_depfun")
    @Column(name = "codDepFuncionario", nullable = false)
    private int codDepFuncionario;

    @JoinColumn(
            foreignKey = @ForeignKey(name = "codDepartamento", value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "codDepartamento", nullable = false)
    private int codDepartamento;

    @JoinColumn(
            foreignKey = @ForeignKey(name = "codFuncionario", value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "codFuncionario", nullable = false)
    private int codFuncionario;

    public DepFuncionario() {
    }
    public DepFuncionario(int codDepartamento, int codFuncionario) {
        this.codDepartamento = codDepartamento;
        this.codFuncionario = codFuncionario;
    }
    public DepFuncionario(int codDepFuncionario, int codDepartamento, int codFuncionario) {
        this.codDepFuncionario = codDepFuncionario;
        this.codDepartamento = codDepartamento;
        this.codFuncionario = codFuncionario;
    }

    public int getCodDepFuncionario() {
        return codDepFuncionario;
    }
    public void setCodDepFuncionario(int codDepFuncionario) {
        this.codDepFuncionario = codDepFuncionario;
    }

    public int getCodDepartamento() {
        return codDepartamento;
    }
    public void setCodDepartamento(int codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    public int getCodFuncionario() {
        return codFuncionario;
    }
    public void setCodFuncionario(int codFuncionario) {
        this.codFuncionario = codFuncionario;
    }

}
