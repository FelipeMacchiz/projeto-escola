package br.model;

import javax.persistence.*;

@Entity
@Table(name = "departamento")
@TableGenerator(
        name = "gerador_id_departamento",
        table = "sqlite_sequence",
        pkColumnName = "name",
        valueColumnName = "seq",
        pkColumnValue = "departamento",
        initialValue = 1,
        allocationSize = 1
)
public class Departamento {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gerador_id_departamento")
    @Column(name = "codDepartamento", nullable = false)
    private int codDepartamento;

    @Column(name = "descricao", nullable = false)
    private String descricao;

    public Departamento() {
    }
    public Departamento(String descricao) {
        this.descricao = descricao;
    }
    public Departamento(int codDepartamento, String descricao) {
        this.codDepartamento = codDepartamento;
        this.descricao = descricao;
    }

    public int getCodDepartamento() {
        return codDepartamento;
    }
    public void setCodDepartamento(int codDepartamento) {
        this.codDepartamento = codDepartamento;
    }

    public String getDescricao() {
        return descricao;
    }
    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

}
