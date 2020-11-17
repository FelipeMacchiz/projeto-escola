package br.model;

import javax.persistence.*;

@Entity
@Table(name = "depServico")
@TableGenerator(
        name = "gerador_id_depser",
        table = "sqlite_sequence",
        pkColumnName = "name",
        valueColumnName = "seq",
        pkColumnValue = "depServico",
        initialValue = 1,
        allocationSize = 1
)
public class DepServico {

    @JoinColumn(
            foreignKey = @ForeignKey(name = "CodDepartamento", value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "codDepartamento", nullable = false)
    private int codDepartamento;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gerador_id_depser")
    @JoinColumn(
            foreignKey = @ForeignKey(name = "codServico", value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "codServico", nullable = false)
    private int codServico;

    public DepServico() {
    }
    public DepServico(int codDepartamento) {
        this.codDepartamento = codDepartamento;
    }
    public DepServico(int codDepartamento, int codFuncionario) {
        this.codDepartamento = codDepartamento;
        this.codServico = codFuncionario;
    }

    public int getCodDepartamento() {
        return codDepartamento;
    }
    public void setCodDepartamento(int codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    public int getCodServico() {
        return codServico;
    }
    public void setCodServico(int codFuncionario) {
        this.codServico = codFuncionario;
    }

}
