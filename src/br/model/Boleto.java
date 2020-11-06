package br.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "boleto")
@TableGenerator(
        name = "gerador_id_boleto",
        table = "sqlite_sequence",
        pkColumnName = "name",
        valueColumnName = "seq",
        pkColumnValue = "boleto",
        initialValue = 1,
        allocationSize = 1
)
public class Boleto {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "gerador_id_boleto")
    @Column(name = "codBoleto")
    private int codBoleto;

    @JoinColumn(
            foreignKey = @ForeignKey(name = "CodAluno", value = ConstraintMode.NO_CONSTRAINT)
    )
    @Column(name = "codAluno", nullable = false)
    private int codAluno;

    @Column(name = "dataVencimento", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date dataVenc;

    @Column(name = "valor", nullable = false)
    private double valor;

    @Column(name = "desconto")
    private double desconto;

    @Column(name = "valorPago")
    private double valorPago;

    public Boleto() {
    }
    public Boleto(int codAluno, Date dataVenc, double valor, double desconto, double valorPago) {
        this.codAluno = codAluno;
        this.dataVenc = dataVenc;
        this.valor = valor;
        this.desconto = desconto;
        this.valorPago = valorPago;
    }
    public Boleto(int codBoleto, int codAluno, Date dataVenc, double valor, double desconto, double valorPago) {
        this.codBoleto = codBoleto;
        this.codAluno = codAluno;
        this.dataVenc = dataVenc;
        this.valor = valor;
        this.desconto = desconto;
        this.valorPago = valorPago;
    }

    public int getCodBoleto() {
        return codBoleto;
    }
    public void setCodBoleto(int codBoleto) {
        this.codBoleto = codBoleto;
    }

    public int getCodAluno() {
        return codAluno;
    }
    public void setCodAluno(int codAluno) {
        this.codAluno = codAluno;
    }

    public Date getDataVenc() {
        return dataVenc;
    }
    public void setDataVenc(Date dataVenc) {
        this.dataVenc = dataVenc;
    }

    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getDesconto() {
        return desconto;
    }
    public void setDesconto(double desconto) {
        this.desconto = desconto;
    }

    public double getValorPago() {
        return valorPago;
    }
    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

}
