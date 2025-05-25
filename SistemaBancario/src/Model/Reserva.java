package Model;

import java.util.Date;

public class Reserva {
    private int id;
    private Cliente cliente;
    private Livro livro;
    private Date dataReserva;
    private Date dataDisponibilidadePrevista;

    /**
     * Construtor principal.
     *
     * @param cliente                      quem fez a reserva
     * @param livro                        livro reservado
     * @param dataReserva                  data em que a reserva foi efetuada
     * @param dataDisponibilidadePrevista  data em que o livro deve ficar disponível
     */
    public Reserva(Cliente cliente,
                   Livro livro,
                   Date dataReserva,
                   Date dataDisponibilidadePrevista) {
        this.cliente = cliente;
        this.livro = livro;
        this.dataReserva = dataReserva;
        this.dataDisponibilidadePrevista = dataDisponibilidadePrevista;
    }

    // ——— Getters / Setters ———

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Livro getLivro() {
        return livro;
    }

    public Date getDataReserva() {
        return dataReserva;
    }

    public Date getDataDisponibilidadePrevista() {
        return dataDisponibilidadePrevista;
    }

    public void setDataDisponibilidadePrevista(Date dataDisponibilidadePrevista) {
        this.dataDisponibilidadePrevista = dataDisponibilidadePrevista;
    }
}
