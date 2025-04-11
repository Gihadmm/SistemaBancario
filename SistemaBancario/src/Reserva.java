import java.time.LocalDateTime;
import java.util.Date;

public class Reserva {
    private int id;
    private LocalDateTime dataReserva;
    private Date dataParaEmprestimo;
    private StatusReserva status;
    private Livro livroReservado;

    public Reserva(int id, Livro livro) {
        this.id = id;
        this.livroReservado = livro;
        this.dataReserva = LocalDateTime.now();
        this.status = StatusReserva.ATIVA;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public LocalDateTime getDataReserva() {
        return dataReserva;
    }

    public Date getDataParaEmprestimo() {
        return dataParaEmprestimo;
    }

    public void setDataParaEmprestimo(Date dataParaEmprestimo) {
        this.dataParaEmprestimo = dataParaEmprestimo;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }

    public Livro getLivroReservado() {
        return livroReservado;
    }
}
