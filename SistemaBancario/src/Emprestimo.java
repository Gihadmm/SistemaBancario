import java.util.Date;

public class Emprestimo {
    private int id;
    private Date dataEmprestimo;
    private Date dataDevolucaoPrevista;
    private Date dataDevolucaoReal;
    private StatusEmprestimo status;

    public Emprestimo(int id, Date dataEmprestimo, Date dataDevolucaoPrevista) {
        this.id = id;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucaoPrevista = dataDevolucaoPrevista;
        this.status = StatusEmprestimo.ATIVO;
    }


    public int calcularDiasAtraso() {
        //Calcular o número de dias de atraso com base nas datas informadas.
        return 0;
    }

    public void aplicarMulta() {
        int diasAtraso = calcularDiasAtraso();
        if (diasAtraso > 0) {
            //aplicação da multa
        }
    }

    public void pagarMulta() {
        //pagamento da multa
    }

    // Getters e setters
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
}
