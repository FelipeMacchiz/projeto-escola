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

    @JoinColumn(
            foreignKey = @ForeignKey(name = "codDepartamento", value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "codDepartamento", nullable = false)
    private int codDepartamento;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gerador_id_depfun")
    @JoinColumn(
            foreignKey = @ForeignKey(name = "codFuncionario", value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "codFuncionario", nullable = false)
    private int codFuncionario;

    public DepFuncionario() {
    }
    public DepFuncionario(int codDepartamento) {
        this.codDepartamento = codDepartamento;
    }
    public DepFuncionario(int codDepartamento, int codFuncionario) {
        this.codDepartamento = codDepartamento;
        this.codFuncionario = codFuncionario;
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
