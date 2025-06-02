package Model;

/**
 * Enumeração que representa o status de um empréstimo de livro na biblioteca.
 * Pode assumir dois estados:
 * - ATIVO: o empréstimo ainda está em andamento, ou seja, o livro ainda não foi devolvido.
 * - CONCLUIDO: o empréstimo foi finalizado com a devolução do livro.
 */
public enum StatusEmprestimo {
    ATIVO,      // Empréstimo em andamento
    CONCLUIDO   // Empréstimo finalizado (livro devolvido)
}
