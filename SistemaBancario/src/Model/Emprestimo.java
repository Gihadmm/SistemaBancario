package Model;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Emprestimo {
    private int id;
    private Date dataEmprestimo;
    private Date dataDevolucaoPrevista;
    private Date dataDevolucaoReal;
    private StatusEmprestimo status;    
    private Exemplar exemplar;
    private Cliente cliente;
    private double valorMulta;
    private boolean multaPaga;

    // Construtor completo
    public Emprestimo(int id, Date dataEmprestimo, Date dataDevolucaoPrevista,
                      Exemplar exemplar, Cliente cliente) {
        this.id = id;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.exemplar = exemplar;
        this.cliente = cliente;
        this.status = StatusEmprestimo.ATIVO;
        this.valorMulta = 0.0;
        this.multaPaga = false;
    }

    // Calcula dias de atraso
    public int calcularDiasAtraso() {
        if (dataDevolucaoReal == null) {
            return 0;
        }
        long diffMillis = dataDevolucaoReal.getTime() - dataDevolucaoPrevista.getTime();
        long dias = TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);
        return (int) Math.max(dias, 0); // não retorna valor negativo
    }

    // Aplica multa com taxa fixa por dia de atraso
    public void aplicarMulta() {
        int diasAtraso = calcularDiasAtraso();
        if (diasAtraso > 0) {
            final double taxaPorDia = 2.0;
            this.valorMulta = diasAtraso * taxaPorDia;
            this.multaPaga = false;
        }
    }

    // Marca multa como paga
    public void pagarMulta() {
        if (this.valorMulta > 0) {
            this.multaPaga = true;
        }
    }

    // Zera a multa
    public void resetarMulta() {
        this.valorMulta = 0.0;
        this.multaPaga = false;
    }

    // —— Getters e Setters completos ——

    public int getId() {
        return id;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public Date getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
    }

    public void setDataDevolucaoPrevista(Date dataDevolucaoPrevista) {
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
    }

    public Date getDataDevolucaoReal() {
        return dataDevolucaoReal;
    }

    public void setDataDevolucaoReal(Date dataDevolucaoReal) {
        this.dataDevolucaoReal = dataDevolucaoReal;
    }

    public StatusEmprestimo getStatus() {
        return status;
    }

    public void setStatus(StatusEmprestimo status) {
        this.status = status;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public double getValorMulta() {
        return valorMulta;
    }

    public void setValorMulta(double valorMulta) {
        this.valorMulta = valorMulta;
    }

    public boolean isMultaPaga() {
        return multaPaga;
    }

    public void setMultaPaga(boolean multaPaga) {
        this.multaPaga = multaPaga;
    }
}
