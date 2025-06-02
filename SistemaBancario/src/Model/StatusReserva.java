package Model;

/**
 * Enumeração que representa o status de uma reserva no sistema da biblioteca.
 * Os possíveis estados são:
 *
 * - ATIVA: a reserva está válida e ainda não foi atendida ou cancelada.
 * - CANCELADA: a reserva foi desfeita, seja pelo cliente ou automaticamente
 *              (por devolução do livro ou não retirada no prazo).
 */
public enum StatusReserva {
    ATIVA,      // Reserva válida, ainda não atendida
    CANCELADA   // Reserva foi cancelada ou expirada
}
