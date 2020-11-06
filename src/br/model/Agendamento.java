package br.model;

import javax.persistence.*;

@Entity
@Table(name = "agendamento")
@TableGenerator(
        name = "gerador_id_agendamento",
        table = "sqlite_sequence",
        pkColumnName = "name",
        valueColumnName = "seq",
        pkColumnValue = "agendamento",
        initialValue = 1,
        allocationSize = 1
)
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gerador_id_agendamento")
    @Column(name = "codAgendamento", nullable = false)
    private int codAgendamento;

    @JoinColumn(
            foreignKey = @ForeignKey(name = "CodServico", value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "codServico", nullable = false)
    private int codServico;

    @JoinColumn(
            foreignKey = @ForeignKey(name = "CodAluno", value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "codAluno", nullable = false)
    private int codAluno;

    @Column(name = "horario", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private String horario;

    public Agendamento() {
    }
    public Agendamento(int codServico, int codAluno, String horario) {
        this.codServico = codServico;
        this.codAluno = codAluno;
        this.horario = horario;
    }
    public Agendamento(int codAgendamento, int codServico, int codAluno, String horario) {
        this.codAgendamento = codAgendamento;
        this.codServico = codServico;
        this.codAluno = codAluno;
        this.horario = horario;
    }

    public int getCodAgendamento() {
        return codAgendamento;
    }
    public void setCodAgendamento(int codAgendamento) {
        this.codAgendamento = codAgendamento;
    }

    public int getCodServico() {
        return codServico;
    }
    public void setCodServico(int codServico) {
        this.codServico = codServico;
    }

    public int getCodAluno() {
        return codAluno;
    }
    public void setCodAluno(int codAluno) {
        this.codAluno = codAluno;
    }

    public String getHorario() {
        return horario;
    }
    public void setHorario(String horario) {
        this.horario = horario;
    }

}
