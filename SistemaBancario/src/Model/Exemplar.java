package Model;

/**
 * Representa uma cópia física de um Livro na biblioteca.
 */
public class Exemplar {
    private int id;             // Identificador único no banco
    private int numeroCopia;    // Sequência dentro do livro (1, 2, 3…)
    private int livroId;        // FK para Livro.id
    private boolean disponivel; // Indica se pode ser emprestado

    /**
     * Construtor usado ao mapear do banco (ExemplarDAO.buscarPorId).
     *
     * @param id            ID gerado pelo banco.
     * @param numeroCopia   Número da cópia dentro do livro.
     * @param livroId       ID do livro a que pertence.
     * @param disponivel    Se está disponível para empréstimo.
     */
    public Exemplar(int id, int numeroCopia, int livroId, boolean disponivel) {
        this.id = id;
        this.numeroCopia = numeroCopia;
        this.livroId = livroId;
        this.disponivel = disponivel;
    }

    /**
     * Construtor antes de persistir, assume disponível.
     *
     * @param numeroCopia Número da cópia.
     * @param livroId     ID do livro.
     */
    public Exemplar(int numeroCopia, int livroId) {
        this(0, numeroCopia, livroId, true);
    }

    // ——— Getters ———

    public int getId() {
        return id;
    }

    public int getNumeroCopia() {
        return numeroCopia;
    }

    public int getLivroId() {
        return livroId;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    // ——— Setters ———

    /** Usado pelo DAO para atribuir o ID gerado */
    public void setId(int id) {
        this.id = id;
    }

    public void setNumeroCopia(int numeroCopia) {
        this.numeroCopia = numeroCopia;
    }

    public void setLivroId(int livroId) {
        this.livroId = livroId;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    // ——— Métodos de domínio ———

    /**
     * Marca este exemplar como emprestado (indisponível).
     * @throws IllegalStateException se já estiver emprestado.
     */
    public void emprestar() {
        if (!disponivel) {
            throw new IllegalStateException("Exemplar já está emprestado.");
        }
        disponivel = false;
    }

    /**
     * Marca este exemplar como devolvido (disponível).
     */
    public void devolver() {
        disponivel = true;
    }

    @Override
    public String toString() {
        return String.format("Exemplar{id=%d, copia=%d, livroId=%d, disponivel=%b}",
                id, numeroCopia, livroId, disponivel);
    }
}
