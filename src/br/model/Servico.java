package br.model;

import javax.persistence.*;

@Entity
@Table(name = "servico")
@TableGenerator(
        name = "gerador_id",
        table = "sqlite_sequence",
        pkColumnName = "name",
        valueColumnName = "seq",
        pkColumnValue = "servico",
        initialValue = 1,
        allocationSize = 1
)
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gerador_id")
    @Column(name = "codServico", nullable = false)
    private int codServico;

    @Column(name = "nome", nullable = false)
    private String nome;

    public Servico() {
    }
    public Servico(String nome) {
        this.nome = nome;
    }
    public Servico(int codServico, String nome) {
        this.codServico = codServico;
        this.nome = nome;
    }

    public int getCodServico() {
        return codServico;
    }
    public void setCodServico(int codServico) {
        this.codServico = codServico;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

}
