package br.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "funcionario")
@TableGenerator(
        name = "gerador_id_fun",
        table = "sqlite_sequence",
        pkColumnName = "name",
        valueColumnName = "seq",
        pkColumnValue = "funcionario",
        initialValue = 1,
        allocationSize = 1
)
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gerador_id_fun")
    @Column(name = "codFuncionario", nullable = false)
    private int codFuncionario;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "RG", nullable = false)
    private String rg;

    @Column(name = "CPF", nullable = false)
    private String cpf;

    @Column(name = "nasc", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date nasc;

    public Funcionario() {
    }
    public Funcionario(String nome, String rg, String cpf, Date nasc) {
        this.nome = nome;
        this.rg = rg;
        this.cpf = cpf;
        this.nasc = nasc;
    }
    public Funcionario(int codFuncionario, String nome, String rg, String cpf, Date nasc) {
        this.codFuncionario = codFuncionario;
        this.nome = nome;
        this.rg = rg;
        this.cpf = cpf;
        this.nasc = nasc;
    }

    public int getCodFuncionario() {
        return codFuncionario;
    }
    public void setCodFuncionario(int codFuncionario) {
        this.codFuncionario = codFuncionario;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRg() {
        return rg;
    }
    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getCpf() {
        return cpf;
    }
    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getNasc() {
        return nasc;
    }
    public void setNasc(Date nasc) {
        this.nasc = nasc;
    }

}
