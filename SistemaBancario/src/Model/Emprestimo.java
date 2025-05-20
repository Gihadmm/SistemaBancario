package Model;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Emprestimo {
    private int id;
    private Date dataEmprestimo;
    private Date dataDevolucaoPrevista;
    private Date dataDevolucaoReal;
    private StatusEmprestimo status;
    private Livro livro;
    private Cliente cliente;
    private double valorMulta;
    private boolean multaPaga;

    public Emprestimo(int id, Date dataEmprestimo, Date dataDevolucaoPrevista,
                      Livro livro, Cliente cliente) {
        this.id = id;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.livro = livro;
        this.cliente = cliente;
        this.status = StatusEmprestimo.ATIVO;
        this.valorMulta = 0.0;
        this.multaPaga = false;
    }

    /** RENOVAÇÃO: estende a data prevista em X dias */
    public void renovar(int dias) {
        if (status != StatusEmprestimo.ATIVO) {
            throw new IllegalStateException("Só é possível renovar empréstimos ativos.");
        }
        dataDevolucaoPrevista = new Date(dataDevolucaoPrevista.getTime() + TimeUnit.DAYS.toMillis(dias));
    }

    /** DEVOLUÇÃO: define data real, muda status e aplica multa */
    public void devolver() {
        if (status != StatusEmprestimo.ATIVO) {
            throw new IllegalStateException("Empréstimo já concluído.");
        }
        dataDevolucaoReal = new Date();
        aplicarMulta();
        status = StatusEmprestimo.CONCLUIDO;
        // devolve o exemplar ao estoque
        livro.devolver();
    }

    /** Cálculo de dias de atraso */
    public int calcularDiasAtraso() {
        if (dataDevolucaoReal == null) return 0;
        long diff = dataDevolucaoReal.getTime() - dataDevolucaoPrevista.getTime();
        return (int)Math.max(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS), 0);
    }

    /** Aplica multa de R$ 2,00 por dia de atraso */
    public void aplicarMulta() {
        int dias = calcularDiasAtraso();
        if (dias > 0) {
            this.valorMulta = dias * 2.0;
            this.multaPaga = false;
        }
    }

    public void pagarMulta() {
        if (valorMulta > 0) this.multaPaga = true;
    }

    // Getters / Setters
    public int getId() { return id; }
    public Date getDataEmprestimo() { return dataEmprestimo; }
    public Date getDataDevolucaoPrevista() { return dataDevolucaoPrevista; }
    public void setDataDevolucaoPrevista(Date d) { this.dataDevolucaoPrevista=d; }
    public Date getDataDevolucaoReal() { return dataDevolucaoReal; }
    public void setDataDevolucaoReal(Date d) { this.dataDevolucaoReal=d; }
    public StatusEmprestimo getStatus() { return status; }
    public void setStatus(StatusEmprestimo s) { this.status=s; }
    public Livro getLivro() { return livro; }
    public Cliente getCliente() { return cliente; }
    public double getValorMulta() { return valorMulta; }
    public boolean isMultaPaga() { return multaPaga; }
    public void setId(int id) { this.id=id; }
    public void setValorMulta(double v) { this.valorMulta=v; }
    public void setMultaPaga(boolean b) { this.multaPaga=b; }
}
