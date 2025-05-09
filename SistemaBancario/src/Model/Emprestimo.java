package Model;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Emprestimo {
    private int id;
    private Date dataEmprestimo;
    private Date dataDevolucaoPrevista;
    private Date dataDevolucaoReal;
    private StatusEmprestimo status;

    // campos de multa
    private double valorMulta;
    private boolean multaPaga;

    public Emprestimo(int id, Date dataEmprestimo, Date dataDevolucaoPrevista) {
        this.id = id;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.status = StatusEmprestimo.ATIVO;
        this.valorMulta = 0.0;
        this.multaPaga = false;
    }

    /**
     * Calcula quantos dias se passaram entre a data prevista e a real.
     * Se dataDevolucaoReal for null, retorna 0.
     */
    public int calcularDiasAtraso() {
        if (dataDevolucaoReal == null) {
            return 0;
        }
        long diffMillis = dataDevolucaoReal.getTime() - dataDevolucaoPrevista.getTime();
        return (int) TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS);
    }

    /**
     * Aplica multa, calculando valorMulta = diasAtraso * taxaPorDia.
     * Exemplo: R$ 2,00 por dia de atraso.
     * Marca multaPaga = false para sinalizar pendência.
     */
    public void aplicarMulta() {
        int diasAtraso = calcularDiasAtraso();
        if (diasAtraso > 0) {
            final double taxaPorDia = 2.0;
            this.valorMulta = diasAtraso * taxaPorDia;
            this.multaPaga = false;
        }
    }

    /** Marca a multa como paga (só faz sentido se valorMulta > 0). */
    public void pagarMulta() {
        if (this.valorMulta > 0) {
            this.multaPaga = true;
        }
    }

    // —— getters e setters ——

    public int getId() {
        return id;
    }

    public Date getDataEmprestimo() {
        return dataEmprestimo;
    }

    public Date getDataDevolucaoPrevista() {
        return dataDevolucaoPrevista;
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

    public double getValorMulta() {
        return valorMulta;
    }

    public boolean isMultaPaga() {
        return multaPaga;
    }

    /** Zera a multa caso seja necessário reaplicar após uma nova devolução */
    public void resetarMulta() {
        this.valorMulta = 0.0;
        this.multaPaga = false;
    }
}
