package Model;

import java.util.Date;

/**
 * Representa uma reserva feita por um cliente para um determinado livro da biblioteca.
 * A reserva contém a data em que foi realizada e uma estimativa de quando o livro estará disponível.
 */
public class Reserva {
    private int id;                                // ID da reserva (gerado no banco)
    private Cliente cliente;                       // Cliente que realizou a reserva
    private Livro livro;                           // Livro reservado
    private Date dataReserva;                      // Data em que a reserva foi registrada
    private Date dataDisponibilidadePrevista;      // Estimativa de quando o livro estará disponível

    /**
     * Construtor principal usado para criar uma nova reserva.
     *
     * @param cliente                      quem fez a reserva
     * @param livro                        livro reservado
     * @param dataReserva                  data em que a reserva foi efetuada
     * @param dataDisponibilidadePrevista data em que o livro deve ficar disponível
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
