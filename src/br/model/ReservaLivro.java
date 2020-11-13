package br.model;

import javax.persistence.*;

@Entity
@Table(name = "reservaLivro")
@TableGenerator(
        name = "gerador_id_reslivro",
        table = "sqlite_sequence",
        pkColumnName = "name",
        valueColumnName = "seq",
        pkColumnValue = "reservaLivro",
        initialValue = 1,
        allocationSize = 1
)
public class ReservaLivro {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gerador_id_reslivro")
    @Column(name = "codReserva", nullable = false)
    private int codReserva;

    @JoinColumn(
            foreignKey = @ForeignKey(name = "CodAluno", value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "codAluno", nullable = false)
    private int codAluno;

    @JoinColumn(
            foreignKey = @ForeignKey(name = "CodLivro", value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "codLivro", nullable = false)
    private int codLivro;

    @Column(name = "dataEmprestimo", nullable = false)
    private String dataEmprestimo;

    @Column(name = "dataDevolucao", nullable = false)
    private String dataDevolucao;

    public ReservaLivro() {
    }
    public ReservaLivro(int codAluno, int codLivro, String dataEmprestimo, String dataDevolucao) {
        this.codAluno = codAluno;
        this.codLivro = codLivro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }
    public ReservaLivro(int codReserva, int codAluno, int codLivro, String dataEmprestimo, String dataDevolucao) {
        this.codReserva = codReserva;
        this.codAluno = codAluno;
        this.codLivro = codLivro;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
    }

    public int getCodReserva() {
        return codReserva;
    }
    public void setCodReserva(int codReserva) {
        this.codReserva = codReserva;
    }

    public int getCodAluno() {
        return codAluno;
    }
    public void setCodAluno(int codAluno) {
        this.codAluno = codAluno;
    }

    public int getCodLivro() {
        return codLivro;
    }
    public void setCodLivro(int codLivro) {
        this.codLivro = codLivro;
    }

    public String getDataEmprestimo() {
        return dataEmprestimo;
    }
    public void setDataEmprestimo(String dataEmprestimo) {
        this.dataEmprestimo = dataEmprestimo;
    }

    public String getDataDevolucao() {
        return dataDevolucao;
    }
    public void setDataDevolucao(String dataDevolucao) {
        this.dataDevolucao = dataDevolucao;
    }

}
