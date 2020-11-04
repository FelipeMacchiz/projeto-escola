package br.model;

import javax.persistence.*;

@Entity
@Table(name = "aluno")
@TableGenerator(
        name = "gerador_id",
        table = "sqlite_sequence",
        pkColumnName = "name",
        valueColumnName = "seq",
        pkColumnValue = "alunos",
        initialValue = 1,
        allocationSize = 1
)
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gerador_id")
    @Column(name = "codAluno", nullable = false)
    private int codAluno;

    private String nome;
    @Column(name = "RG", nullable = false)
    private String rg;

    @Column(name = "CPF", nullable = false)
    private String cpf;

    @Column(name = "nasc", nullable = false)
    @Temporal(TemporalType.DATE)
    private String nasc;

    public Aluno() {
    }
    public Aluno(int codAluno, String nome, String rg, String cpf, String nasc) {
        this.codAluno = codAluno;
        this.nome = nome;
        this.rg = rg;
        this.cpf = cpf;
        this.nasc = nasc;
    }
    public Aluno(String nome, String rg, String cpf, String nasc) {
        this.nome = nome;
        this.rg = rg;
        this.cpf = cpf;
        this.nasc = nasc;
    }

    public int getCodAluno() {
        return codAluno;
    }
    public void setCodAluno(int codAluno) {
        this.codAluno = codAluno;
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

    public String getNasc() {
        return nasc;
    }
    public void setNasc(String nasc) {
        this.nasc = nasc;
    }

}