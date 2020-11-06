package br.model;

import javax.persistence.*;

@Entity
@Table(name = "livro")
@TableGenerator(
        name = "gerador_id_livro",
        table = "sqlite_sequence",
        pkColumnName = "name",
        valueColumnName = "seq",
        pkColumnValue = "livro",
        initialValue = 1,
        allocationSize = 1
)
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gerador_id_livro")
    @Column(name = "codLivro", nullable = false)
    private int codLivro;

    @Column(name = "nome", nullable = false)
    private String nome;

    public Livro() {
    }
    public Livro(String nome) {
        this.nome = nome;
    }
    public Livro(int codLivro, String nome) {
        this.codLivro = codLivro;
        this.nome = nome;
    }

    public int getCodLivro() {
        return codLivro;
    }
    public void setCodLivro(int codLivro) {
        this.codLivro = codLivro;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }

}
